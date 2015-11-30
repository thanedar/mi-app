package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.utils.MiLog;

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
 * Activities containing this fragment MUST implement the {@link OnAccountFragmentInteractionListener}
 * interface.
 */
public class FragmentAccount extends Fragment
{
    private OnAccountFragmentInteractionListener mListener;

    @InjectView(R.id.account_money)
    TextView money;
    @InjectView(R.id.account_minutes)
    TextView minutes;
    @InjectView(R.id.account_sms)
    TextView sms;
    @InjectView(R.id.account_data)
    TextView data;

    @Inject
    MiApiClient miApiClient;

    public static FragmentAccount newInstance() {
        FragmentAccount fragment = new FragmentAccount();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentAccount() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mListener.onAccountFragmentInteraction();

        get_account_info();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAccountFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAccountFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAccountFragmentInteractionListener {
        void onAccountFragmentInteraction();
    }

    private void get_account_info() {
        BeanGetAccountInfo beanGetAccountInfo = new BeanGetAccountInfo();
        miApiClient.get_account_info(beanGetAccountInfo, new Callback<BeanGetAccountInfoResponse>() {
            @Override
            public void success(BeanGetAccountInfoResponse beanGetAccountInfoResponse, Response response) {
                if (beanGetAccountInfoResponse.getError().getCode() == Config.SUCCESS && beanGetAccountInfoResponse.getResult() != null) {
                    Resources res = getResources();
                    minutes.setText(res.getString(R.string.home_minutes, beanGetAccountInfoResponse.getResult().getUsedMinutes()));
                    sms.setText(res.getString(R.string.home_sms, beanGetAccountInfoResponse.getResult().getUsedSms()));
                    data.setText(res.getString(R.string.home_data, beanGetAccountInfoResponse.getResult().getUsedData()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i("FragmentAccount", "GetAccountInfo failure " + error.toString());
            }
        });
    }
}
