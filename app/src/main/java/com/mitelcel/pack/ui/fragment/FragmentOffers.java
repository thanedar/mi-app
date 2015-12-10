package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.bean.ui.OfferDetailHolder;
import com.mitelcel.pack.bean.ui.OfferItemHolder;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.dagger.module.SharedPrefModule;
import com.mitelcel.pack.ui.OfferDetailActivity;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.OfferListAdapter;
import com.mitelcel.pack.ui.widget.LoadOffersAsyncTask;
import com.mitelcel.pack.ui.widget.TextViewFolks;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 */
public class FragmentOffers extends Fragment implements IFragCommunication, AdapterView.OnItemClickListener {

    public static final String TAG = FragmentOffers.class.getSimpleName();
    public int start = 0;
    int limit = 5;
    public static final String PREV_PAGE = "prev_page";

    private GridView mGridView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    OfferListAdapter adapter;
    LoadOffersAsyncTask async;
    TextViewFolks textViewFolks;
    public static String PACKAGE;
//    Bitmap bitmap;

    @Inject
    MiApiClient miApiClient;
    @Inject
    SharedPreferences sharedPreferences;

    OnMainFragmentInteractionListener mListener;

    public EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(final int page, int totalItemsCount) {

            MiLog.i(FragmentOffers.class.getName(), "setOnScrollListener start[" + page + "]");
            new Handler().postDelayed(FragmentOffers.this::loadDataFromPage, 100);
        }
    };

    public static FragmentOffers newInstance() {
        FragmentOffers instance = new FragmentOffers();
        Bundle bundle = new Bundle();
        bundle.putString("", "");
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new OfferListAdapter(getActivity().getApplicationContext(), 0);
        PACKAGE = getActivity().getPackageName();

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = View.inflate(getActivity(), R.layout.fragment_offers, null);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.offer_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.yellow, R.color.green, R.color.material_blue_500);
        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            endlessScrollListener.resetListener();
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(true);
            startUpdateProcess(true);
        }, 100));

        // Prepare the GridView
        mGridView = (GridView) root.findViewById(R.id.grid_view);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(endlessScrollListener);

        textViewFolks = (TextViewFolks) root.findViewById(R.id.empty_list);
        mGridView.setEmptyView(textViewFolks);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MiUtils.Info.isNetworkConnected(getActivity()))
            startUpdateProcess(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.updateActionBar();
        if (MiUtils.MiAppPreferences.isInvalidSession()) {
            startUpdateProcess(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt(PREV_PAGE, start);
        super.onSaveInstanceState(outState);
    }

    public void startUpdateProcess(boolean refresh) {
        if (async == null || async.getStatus() == AsyncTask.Status.FINISHED) {
            this.start = refresh ? 0 : this.start;
            async = new LoadOffersAsyncTask(FragmentOffers.this, this.start, limit, refresh);
            this.start = this.start + limit;
            async.execute(miApiClient);
        }
    }

    void loadDataFromPage() {

        if (async == null || async.getStatus() == AsyncTask.Status.FINISHED) {
            async = new LoadOffersAsyncTask(FragmentOffers.this, this.start, limit);
            this.start = this.start + limit;
            async.execute(miApiClient);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (async != null && !async.isCancelled())
            MiLog.i(TAG, "OnDestroy: " + async.cancel(true));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateAdapter(List<OfferItemHolder> data, boolean refresh) {

        mSwipeRefreshLayout.setRefreshing(false);
        if (refresh) adapter.clear();

        MiLog.i(FragmentOffers.class.getName(),"---------------------------------------------------pagination");
        MiLog.i(FragmentOffers.class.getName(),"pagination refresh[" + refresh + "]");
        MiLog.i(FragmentOffers.class.getName(),"pagination arrived[" + data.size() + "]");
        MiLog.i(FragmentOffers.class.getName(),"pagination in adapter before [" + adapter.getCount() + "]");

        adapter.addAll(data);
        MiLog.i(FragmentOffers.class.getName(), "pagination in adapter after [" + adapter.getCount() + "]");
        if (adapter.getCount() == 0) {
            textViewFolks.setText(getString(R.string.no_data));
            loadDataFromPage();
        }

        MiLog.i(FragmentOffers.class.getName(), "---------------------------------------------------pagination");
    }

    @Override
    public void error(int errorCode) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (errorCode == Config.INVALID_SESSION_ID) {
            BeanLogin beanLogin = new BeanLogin(getActivity());
            miApiClient.login(beanLogin, new Callback<BeanLoginResponse>() {
                @Override
                public void success(BeanLoginResponse resp, Response response) {
                    if (resp.getError().getCode() == Config.SUCCESS) {
                        MiUtils.MiAppPreferences.setSessionId(resp.getResult().getSessionId());
                        sharedPreferences.edit().putBoolean(SharedPrefModule.HAS_CALL_ERROR, false).apply();
                        adapter.clear();
                        startUpdateProcess(true);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

        if (errorCode == Config.NO_CONNECTION) {
            mListener.noInternetConnection();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMainFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OfferListAdapter.HolderMainItem holder = (OfferListAdapter.HolderMainItem) view.getTag();
        OfferItemHolder offerItemHolder = (OfferItemHolder) parent.getAdapter().getItem(position);
        // Interesting data to pass across are the thumbnail size/location, the
        // resourceId of the source bitmap, the picture description, and the
        // orientation (to avoid returning back to an obsolete configuration if
        // the device rotates again in the meantime)
        int[] screenLocation = new int[2];
        holder.borderImageView.getLocationOnScreen(screenLocation);
        int[] screenLocationBtnInstallPlay = new int[2];
        holder.offerBtn.getLocationOnScreen(screenLocationBtnInstallPlay);

        Intent offerDetail = new Intent(getActivity(), OfferDetailActivity.class);
        OfferDetailHolder.configureIntent(
                offerDetail,
                PACKAGE,
                screenLocation[1], //top
                screenLocation[0], //left
                holder.borderImageView.getWidth(),
                holder.borderImageView.getHeight(),
                offerItemHolder.urlIcon,
                offerItemHolder.urlCard,
                offerItemHolder.buttonText,
                offerItemHolder.description,
                screenLocationBtnInstallPlay[1], //top
                screenLocationBtnInstallPlay[0], //left
                holder.offerBtn.getWidth(),
                holder.offerBtn.getHeight());

        //start activity
        startActivity(offerDetail);

        // Override transitions: we don't want the normal window animation in addition
        // to our custom one
//        getActivity().overridePendingTransition(0, 0);
    }
}
