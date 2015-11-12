package com.mitelcel.pack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.MiRestClient;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
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
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 */
public class FragRecent extends Fragment
{
    public static final String TAG = FragRecent.class.getName();

    RecyclerView.LayoutManager mLayoutManager;
    RecentRecycleViewAdapter mRecentRecycleViewAdapter;

    @InjectView(R.id.recent_recycle_view)
    EmptyRecyclerView mRecyclerView;
    @InjectView(R.id.empty_list)
    TextView tvEmpty;

    @Inject
    MiApiClient miApiClient;

    public static FragRecent newInstance() {
        FragRecent fragment = new FragRecent();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragRecent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
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

        BeanGetRecentActivity beanGetRecentActivity = new BeanGetRecentActivity(1);
        MiLog.i(TAG, "beanGetRecentActivity [" + beanGetRecentActivity.toString() + " ]");
        if(miApiClient == null){
            MiLog.i(TAG, "miApiClient [ NULL ]");
            miApiClient = MiRestClient.init();
        }
        miApiClient.get_recent_activity(beanGetRecentActivity, new Callback<BeanGetRecentActivityResponse>() {
            @Override
            public void success(BeanGetRecentActivityResponse beanGetRecentActivityResponse, Response response) {
                MiLog.i(TAG, "beanGetRecentActivity [" + beanGetRecentActivityResponse.toString() + " ]");
                if (beanGetRecentActivityResponse.getError().getCode() == Config.SUCCESS && beanGetRecentActivityResponse.getResult() != null) {
                    MiLog.i(TAG, beanGetRecentActivityResponse.toString());
                    List<BeanGetRecentActivityResponse.UserActivity> userActivities = beanGetRecentActivityResponse.getResult();
                    mRecentRecycleViewAdapter.replaceData(userActivities == null ? new ArrayList<>() : userActivities);
                } else{
                    if(beanGetRecentActivityResponse == null)
                        MiLog.i(TAG, "beanGetRecentActivityResponse [ NULL ]");
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
