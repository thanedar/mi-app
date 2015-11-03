package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.MiRestClient;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

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

    @InjectView(R.id.main_imei)
    TextView imei;

    @InjectView(R.id.main_imsi)
    TextView imsi;

    @InjectView(R.id.main_android_id)
    TextView androidId;

    @Inject
    MiApiClient miApiClient;

    OnMainFragmentInteractionListener mListener;

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

        if (getArguments() != null) {
        }

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_blank, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        BeanSubmitAppInfo beanSubmitAppInfo = new BeanSubmitAppInfo(getActivity().getApplicationContext());
        MiRestClient.init().submit_app_info(beanSubmitAppInfo, new Callback<BeanSubmitAppInfoResponse>() {
            @Override
            public void success(BeanSubmitAppInfoResponse beanSubmitAppInfoResponse, Response response) {
                MiUtils.MiAppPreferences.setToken(getActivity(), beanSubmitAppInfoResponse.getResult().getAppToken());
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.e(TAG, "BeanSubmitAppInfoResponse Error " + error.toString());
            }
        });

        imei.setText("IMEI: " + MiUtils.Info.getDeviceId(this.getContext()));
        imsi.setText("IMSI: " + MiUtils.Info.getSubscriberId(this.getContext()));
        androidId.setText("ANDROID ID: " + MiUtils.Info.getAndroidId(this.getContext()));
        mListener.updateActionBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (context instanceof Activity) ? (Activity) context : getActivity();
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
}
