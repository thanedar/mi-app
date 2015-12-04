package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.req.BeanGetOfferList;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetOfferListResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.bean.ui.OfferItemHolder;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.ListOfferActivity;
import com.mitelcel.pack.ui.RecentActivity;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.BorderImageView;
import com.mitelcel.pack.ui.widget.DividerItemDecoration;
import com.mitelcel.pack.ui.widget.EmptyRecyclerView;
import com.mitelcel.pack.ui.widget.RecentRecycleViewAdapter;
import com.mitelcel.pack.utils.MiLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {

    public static final String TAG = FragmentMain.class.getSimpleName();

    OnMainFragmentInteractionListener mListener;
    RecyclerView.LayoutManager mLayoutManager;
    RecentRecycleViewAdapter mRecentRecycleViewAdapter;

    @InjectView(R.id.home_minutes)
    TextView minutes;
    @InjectView(R.id.home_sms)
    TextView sms;
    @InjectView(R.id.home_data)
    TextView data;

    @InjectView(R.id.home_recent_act)
    EmptyRecyclerView mRecyclerView;
    @InjectView(R.id.empty_list)
    TextView tvEmpty;

    @InjectView(R.id.item_offer_description)
    public TextView offerDescription;
    @InjectView(R.id.item_offer_click_btn)
    public TextView offerBtn;
    @InjectView(R.id.item_border_imageview)
    public BorderImageView borderImageView;
    @InjectView(R.id.item_background_imageview)
    public ImageView backGroundImageView;

    @Inject
    MiApiClient miApiClient;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentMain.
     */
    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // create the layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        // set layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // create the adapter
        mRecentRecycleViewAdapter = new RecentRecycleViewAdapter();
        // adding adapter on recycle view
        mRecyclerView.setAdapter(mRecentRecycleViewAdapter);
        // adding separator on recycle view
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // adding empty view
        mRecyclerView.setEmptyView(tvEmpty);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mListener.updateActionBar();
        mRecentRecycleViewAdapter.setOnItemClickListener(new RecentRecycleViewAdapter.ClickListener() {
            @Override
            public void onRecentItemClick(int position, View view) {
                MiLog.i("FragmentMain", "Click detected in fragment");
                showRecent();
            }
        });

        get_account_info();
        get_best_offer();
        get_most_recent_activity();
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
    public void onDetach() {
        super.onDetach();
    }

    private void get_account_info() {
        BeanGetAccountInfo beanGetAccountInfo = new BeanGetAccountInfo();
        MiLog.i(TAG, "beanGetAccountInfo: " + beanGetAccountInfo.toString());
        miApiClient.get_account_info(beanGetAccountInfo, new Callback<BeanGetAccountInfoResponse>() {
            @Override
            public void success(BeanGetAccountInfoResponse beanGetAccountInfoResponse, Response response) {
                MiLog.i(TAG, "beanGetAccountInfoResponse: " + beanGetAccountInfoResponse.toString());
                if (beanGetAccountInfoResponse.getError().getCode() == Config.SUCCESS && beanGetAccountInfoResponse.getResult() != null) {
                    Resources res = getResources();
                    minutes.setText(res.getString(R.string.home_minutes, beanGetAccountInfoResponse.getResult().getUsedMinutes()));
                    sms.setText(res.getString(R.string.home_sms, beanGetAccountInfoResponse.getResult().getUsedSms()));
                    data.setText(res.getString(R.string.home_data, beanGetAccountInfoResponse.getResult().getUsedData()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "GetAccountInfo failure " + error.toString());
            }
        });
    }

    private void get_most_recent_activity() {
        BeanGetRecentActivity beanGetRecentActivity = new BeanGetRecentActivity(1);
        MiLog.i(TAG, "beanGetRecentActivity: " + beanGetRecentActivity.toString());
        miApiClient.get_recent_activity(beanGetRecentActivity, new Callback<BeanGetRecentActivityResponse>() {
            @Override
            public void success(BeanGetRecentActivityResponse beanGetRecentActivityResponse, Response response) {
                MiLog.i(TAG, "beanGetRecentActivityResponse: " + beanGetRecentActivityResponse.toString());
                if (beanGetRecentActivityResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i(TAG, beanGetRecentActivityResponse.toString());
                    if (beanGetRecentActivityResponse.getResult() != null) {
                        List<BeanGetRecentActivityResponse.UserActivity> userActivities = beanGetRecentActivityResponse.getResult();
                        mRecentRecycleViewAdapter.replaceData(userActivities == null ? new ArrayList<>() : userActivities);
                    } else {
                        MiLog.i("FragmentMain", "beanGetRecentActivityResponse.Result [ NULL ]");
                        tvEmpty.setText(R.string.no_data);
                    }
                } else {
                    if (beanGetRecentActivityResponse == null)
                        MiLog.i("FragmentMain", "beanGetRecentActivityResponse [ NULL ]");
                    tvEmpty.setText(R.string.oops);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "GetAccountInfo failure " + error.toString());
                tvEmpty.setText(R.string.oops);
            }
        });
    }

    @OnClick(R.id.home_offer)
    public void onCLick(View view){
        MiLog.i(TAG, "Offer click detected");
        getActivity().startActivity(new Intent(getActivity(), ListOfferActivity.class));
    }

    private void get_best_offer() {
        BeanGetOfferList beanGetOfferList = new BeanGetOfferList(0, 1);
        MiLog.i(TAG, "beanGetOfferList " + beanGetOfferList.toString());
        miApiClient.get_offer_list(beanGetOfferList, new Callback<BeanGetOfferListResponse>() {
            @Override
            public void success(BeanGetOfferListResponse beanGetOfferListResponse, Response response) {
                MiLog.i(TAG, "beanGetOfferListResponse " + beanGetOfferListResponse.toString());
                List<BeanGetOfferListResponse.Offer> offerList = beanGetOfferListResponse.getResult();
                MiLog.i(TAG, "offer list " + offerList.get(0).toString());
                OfferItemHolder offerItemHolder = new OfferItemHolder(offerList.get(0));
                MiLog.i(TAG, "offerItemHolder desc " + offerItemHolder.description + " button " + offerItemHolder.buttonText +
                        " urlCard " + offerItemHolder.urlCard + " urlIcon " + offerItemHolder.urlIcon);
                offerDescription.setText(offerItemHolder.description);
                offerBtn.setText(offerItemHolder.buttonText);
                offerBtn.setVisibility(View.INVISIBLE);

                Picasso.with(getActivity().getApplicationContext()).load(offerItemHolder.urlIcon).into(borderImageView);
                Picasso.with(getActivity().getApplicationContext()).load(offerItemHolder.urlCard).placeholder(R.drawable.placeholder_thumb).into(backGroundImageView);
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "GetOfferList failure " + error.toString());
            }
        });
    }

    public void showRecent(){
        MiLog.i(TAG, "Start recent activity");
        getActivity().startActivity(new Intent(getActivity(), RecentActivity.class));
    }
}
