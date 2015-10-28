package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.dagger.module.SharedPrefModule;
//import com.mitelcel.pack.ui.adapter.AdapListMain;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.TextViewFolks;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FragMainListGrid extends Fragment implements AdapterView.OnItemClickListener{

    public static final String TAG = FragMainListGrid.class.getSimpleName();
    public int page = 0;
    int limit = 5;
    public static final String PREV_PAGE = "prev_page";

//    private GridView mGridView;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    AdapListMain adapter;
//    TextViewFolks textViewFolks;

    public static String PACKAGE;
//    Bitmap bitmap;

    @Inject
    MiApiClient miApiClient;
    @Inject
    SharedPreferences sharedPreferences;

    OnMainFragmentInteractionListener mListener;

    /*public EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(final int page, int totalItemsCount) {
//                mSwipeRefreshLayout.setEnabled(true);
//                mSwipeRefreshLayout.setRefreshing(true);
            MiLog.i(FragMainListGrid.class.getName(), "setOnScrollListener page[" + page + "]");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    mSwipeRefreshLayout.setEnabled(true);
//                    mSwipeRefreshLayout.setRefreshing(true);
//                    loadDataFromPage();
                }
            }, 100);
        }
    };*/

    public static FragMainListGrid newInstance(Objects obj){
        FragMainListGrid instance = new FragMainListGrid();
        Bundle bundle = new Bundle();
        bundle.putString("","");
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//        adapter = new AdapListMain(getActivity().getApplicationContext(), 0);
        PACKAGE = getActivity().getPackageName();
        setHasOptionsMenu(true);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(savedInstanceState != null){
//            if(savedInstanceState.containsKey(PREV_PAGE)){
//                page = savedInstanceState.getInt(PREV_PAGE);
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = View.inflate(getActivity(), R.layout.fragment_blank, null);

/*
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endlessScrollListener.resetListener();
                        mSwipeRefreshLayout.setEnabled(true);
                        mSwipeRefreshLayout.setRefreshing(true);
//                        startUpdateProcess(true);
                    }
                }, 100);
            }
        });

        // Prepare the GridView
        mGridView = (GridView) root.findViewById(R.id.grid_view);
        mGridView.setAdapter(adapter);
//        mGridView.setEmptyView(root.findViewById(R.id.empty));
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(endlessScrollListener);

        textViewFolks = (TextViewFolks)root.findViewById(R.id.empty_list);
        mGridView.setEmptyView(textViewFolks);
*/

//        if(async != null && async.getStatus() == AsyncTask.Status.PENDING){
//            mSwipeRefreshLayout.setEnabled(true);
//            mSwipeRefreshLayout.setRefreshing(true);
//        }

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if(MiUtils.Info.isNetworkConnected(getActivity()))
//            startUpdateProcess(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.updateActionBar();
//        if(MiUtils.MiAppPreferences.isInvalidToken(getActivity())){
//            startUpdateProcess(true);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt(PREV_PAGE, page);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*@Override
    public void updateAdapter(List<ItemMainAdapter> data, boolean refresh) {

        mSwipeRefreshLayout.setRefreshing(false);
        if (refresh) adapter.clear();

//        MiLog.i(FragMainListGrid.class.getName(),"---------------------------------------------------pagination");
//        MiLog.i(FragMainListGrid.class.getName(),"pagination refresh[" + refresh + "]");
//        MiLog.i(FragMainListGrid.class.getName(),"pagination arrived[" + data.size() + "]");
//        MiLog.i(FragMainListGrid.class.getName(),"pagination in adapter before [" + adapter.getCount() + "]");

        adapter.addAll(data);
//        MiLog.i(FragMainListGrid.class.getName(), "pagination in adapter after [" + adapter.getCount() + "]");
        if (adapter.getCount() == 0) {
            textViewFolks.setText(getString(R.string.no_data));
        }

//        MiLog.i(FragMainListGrid.class.getName(), "---------------------------------------------------pagination");
    }*/

    /*@Override
    public void error(int errorCode) {
        mSwipeRefreshLayout.setRefreshing(false);
        if(errorCode == Config.ERROR_CODE_TOKEN_EXPIRED){
            BeanCreateTokenIfExpired beanCreateTokenIfExpired = new BeanCreateTokenIfExpired(getActivity());
            miApiClient.createTokenIfExpired(beanCreateTokenIfExpired, new Callback<BeanCreateTokenIfExpiredResp>() {
                @Override
                public void success(BeanCreateTokenIfExpiredResp resp, Response response) {
                    if(resp.getResult().getErrorCode() == 1){
                        MiUtils.MiAppPreferences.setToken(getActivity(), resp.getIdToken());
                        *//**//**
                         * da cancellare
                         *//**//*
                        sharedPreferences.edit().putBoolean(SharedPrefModule.HAS_CALL_ERROR, false).commit();
                        adapter.clear();
                        if(MiUtils.Info.isNetworkConnected(getActivity()))
                            startUpdateProcess(true);
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

        if(errorCode == Config.ERROR_NETWORK){
            mListener.noInternetConnection();
        }
    }*/


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (context instanceof Activity) ? (Activity) context : getActivity();
        try {
            mListener = (OnMainFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        AdapListMain.HolderMainItem holder = (AdapListMain.HolderMainItem)view.getTag();
//        ItemMainAdapter itemMainAdapter = (ItemMainAdapter)parent.getAdapter().getItem(position);
        // Interesting data to pass across are the thumbnail size/location, the
        // resourceId of the source bitmap, the picture description, and the
        // orientation (to avoid returning back to an obsolete configuration if
        // the device rotates again in the meantime)
//        int[] screenLocation = new int[2];
//        holder.borderImageView.getLocationOnScreen(screenLocation);
        //screenlocation btn
//        int[] screenLocationBtnInstallPlay = new int[2];
//        holder.playInstall.getLocationOnScreen(screenLocationBtnInstallPlay);
        /*Intent gameDetails = new Intent(getActivity(), GameDetail.class);
        ImageParamAdapter.configureIntent(
                gameDetails,
                PACKAGE,
                screenLocation[1], //top
                screenLocation[0], //left
                holder.borderImageView.getWidth(),
                holder.borderImageView.getHeight(),
//                bitmap,
                itemMainAdapter.urlThumb,
                itemMainAdapter.urlGame,
                itemMainAdapter.urlCard,
                itemMainAdapter.description,
                itemMainAdapter.title,
                itemMainAdapter.packageId,
                screenLocationBtnInstallPlay[1], //top
                screenLocationBtnInstallPlay[0], //left
                holder.playInstall.getWidth(),
                holder.playInstall.getHeight(),
                itemMainAdapter.getGiftCoins());

        //start activity
        startActivity(gameDetails);*/

        // Override transitions: we don't want the normal window animation in addition
        // to our custom one
//        getActivity().overridePendingTransition(0, 0);
    }
//
//    public interface OnMainFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void updateActionBar();
//        public void noInternetConnection();
//        public void goBackPreviousFragment();
//    }

}
