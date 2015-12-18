package com.mitelcel.pack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.bean.api.req.BeanGetServiceList;
import com.mitelcel.pack.bean.api.resp.BeanGetServiceListResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.widget.DividerItemDecoration;
import com.mitelcel.pack.ui.widget.EmptyRecyclerView;
import com.mitelcel.pack.ui.widget.ServiceRecycleViewAdapter;
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
public class FragmentService extends Fragment
{
    public static final String TAG = FragmentService.class.getName();

    RecyclerView.LayoutManager mLayoutManager;
    ServiceRecycleViewAdapter mServiceRecycleViewAdapter;

    @InjectView(R.id.recent_recycle_view)
    EmptyRecyclerView mRecyclerView;
    @InjectView(R.id.empty_list)
    TextView tvEmpty;
    @InjectView(R.id.label_recent)
    TextView tvLabel;

    @Inject
    MiApiClient miApiClient;

    MaterialDialog dialog;

    public static FragmentService newInstance() {
        FragmentService fragment = new FragmentService();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentService() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);

        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();
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
        mServiceRecycleViewAdapter = new ServiceRecycleViewAdapter();
        // adding adapter on recycle view
        mRecyclerView.setAdapter(mServiceRecycleViewAdapter);
        // adding separator on recycle view
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // adding empty view
        mRecyclerView.setEmptyView(tvEmpty);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dialog.show();
        tvLabel.setText(getString(R.string.your_service));
        tvEmpty.setText(R.string.loading);

        mServiceRecycleViewAdapter.setOnItemClickListener(new ServiceRecycleViewAdapter.ClickListener() {
            @Override
            public void onRecentItemClick(int position, View view) {
                MiLog.i("FragmentRecent", "Click detected in fragment");
            }
        });

        BeanGetServiceList beanGetServiceList = new BeanGetServiceList();
        MiLog.i(TAG, "beanGetServiceList [" + beanGetServiceList.toString() + " ]");

        miApiClient.get_service_list(beanGetServiceList, new Callback<BeanGetServiceListResponse>() {
            @Override
            public void success(BeanGetServiceListResponse beanGetServiceListResponse, Response response) {
                dialog.dismiss();
                if (beanGetServiceListResponse == null){
                    tvEmpty.setText(R.string.oops);
                    MiLog.i(TAG, "GetServiceListResponse [ NULL ]");
                }
                else {
                    MiLog.i(TAG, "beanGetServiceListResponse [" + beanGetServiceListResponse.toString() + " ]");

                    if (beanGetServiceListResponse.getError().getCode() == Config.SUCCESS && beanGetServiceListResponse.getResult() != null) {
                        MiLog.i(TAG, beanGetServiceListResponse.toString());
                        List<BeanGetServiceListResponse.Service> services = beanGetServiceListResponse.getResult();
                        mServiceRecycleViewAdapter.replaceData(services == null ? new ArrayList<>() : services);
                    } else {
                        tvEmpty.setText(R.string.no_data);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "GetServiceListResponse failure " + error.toString());
                dialog.dismiss();
                tvEmpty.setText(R.string.oops);
            }
        });
    }
}
