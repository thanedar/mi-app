package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.DividerItemDecoration;
import com.mitelcel.pack.ui.widget.EmptyRecyclerView;
import com.mitelcel.pack.ui.widget.RecentRecycleViewAdapter;
import com.mitelcel.pack.utils.MiLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragMain extends Fragment {

    public static final String TAG = FragMain.class.getSimpleName();

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

    @Inject
    MiApiClient miApiClient;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragMain.
     */
    public static FragMain newInstance() {
        FragMain fragment = new FragMain();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragMain() {
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
        // adding adapter on recycleview
        mRecyclerView.setAdapter(mRecentRecycleViewAdapter);
        // adding separator on recycleview
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // adding empty view
        mRecyclerView.setEmptyView(tvEmpty);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mListener.updateActionBar();

        get_account_info();
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
        miApiClient.get_account_info(beanGetAccountInfo, new Callback<BeanGetAccountInfoResponse>() {
            @Override
            public void success(BeanGetAccountInfoResponse beanGetAccountInfoResponse, Response response) {
                if (beanGetAccountInfoResponse.getError().getCode() == Config.SUCCESS && beanGetAccountInfoResponse.getResult() != null) {
                    Resources res = getResources();
                    String value = String.format(res.getString(R.string.home_minutes), beanGetAccountInfoResponse.getResult().getUsedMinutes());
                    minutes.setText(value);
                    value = String.format(res.getString(R.string.home_sms), beanGetAccountInfoResponse.getResult().getUsedSms());
                    sms.setText(value);
                    value = String.format(res.getString(R.string.home_data), beanGetAccountInfoResponse.getResult().getUsedData());
                    data.setText(value);
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
        miApiClient.get_recent_activity(beanGetRecentActivity, new Callback<BeanGetRecentActivityResponse>() {
            @Override
            public void success(BeanGetRecentActivityResponse beanGetRecentActivityResponse, Response response) {
                if (beanGetRecentActivityResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i(TAG, beanGetRecentActivityResponse.toString());
                    if (beanGetRecentActivityResponse.getResult() != null) {
                        List<BeanGetRecentActivityResponse.UserActivity> userActivities = beanGetRecentActivityResponse.getResult();
                        mRecentRecycleViewAdapter.replaceData(userActivities == null ? new ArrayList<>() : userActivities);
                    } else{
                        MiLog.i("FragMain", "beanGetRecentActivityResponse.Result [ NULL ]");
                        tvEmpty.setText(R.string.no_data);
                    }
                } else{
                    if(beanGetRecentActivityResponse == null)
                        MiLog.i("FragMain", "beanGetRecentActivityResponse [ NULL ]");
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
}
