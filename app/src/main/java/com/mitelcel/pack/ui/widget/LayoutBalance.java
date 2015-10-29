package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class LayoutBalance extends FrameLayout
//        implements SharedPreferences.OnSharedPreferenceChangeListener, Observer<BeanUserWalletResponse>
{

    @InjectView(R.id.act_bar_balance)
    TextView textView;
    @InjectView(R.id.progress_bar_balance)
    ProgressBar progressBar;

    @Inject
    MiApiClient miApiClient;

    public LayoutBalance(Context context) {
        super(context);
    }

    public LayoutBalance(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutBalance(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        setCurrentBalance();
    }

//    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(MiUtils.MiAppPreferences.MONEY_BALANCE) || key.equals(MiUtils.MiAppPreferences.CURRENCY_SYMBOL))
            setCurrentBalance();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        ((MiApp)getContext()).getAppComponent().inject(this);
//        MiUtils.MiAppPreferences.registerListener(this, getContext());
//        callUserWallet();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        MiUtils.MiAppPreferences.unRegisterListener(this, getContext());
        miApiClient = null;
    }

    private void setCurrentBalance(){
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cash, 0, 0, 0);
        String symbol = MiUtils.MiAppPreferences.getCurrencySymbol(getContext());
        String value = MiUtils.MiAppPreferences.getMoneyCount(getContext());
        textView.setText(symbol + value);
        /*textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserWallet();
            }
        });*/
    }

    public void hideProgressBarCoins(){
        if(progressBar != null) progressBar.setVisibility(View.GONE);
        if(textView !=null) textView.setVisibility(View.VISIBLE);
    }

    public void showProgressBarCoins(){
        if(progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if(textView !=null) textView.setVisibility(View.GONE);
    }

    /*public void callUserWallet(){
        BeanUserWallet beanUserWallet = new BeanUserWallet(getContext());
        MiLog.i(LayoutCashCoins.class.getSimpleName(), "beanUserWallet [ " + beanUserWallet.toString() + " ]");
        showProgressBarCoins();
        miApiClient.userWalletRxJava(beanUserWallet)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }*/

//    @Override
    public void onCompleted() {
        hideProgressBarCoins();
    }

//    @Override
    public void onError(Throwable e) {
        hideProgressBarCoins();
    }

//    @Override
    public void onNext(GenericBean beanUserWalletResponse) {
        MiLog.i(LayoutBalance.class.getSimpleName(), "beanUserWallet response [ " + beanUserWalletResponse.toString() + " ]");
        /*if (beanUserWalletResponse != null && beanUserWalletResponse.getResult().getErrorCode() == 1) {
            String cash = beanUserWalletResponse.getMoneyBalance();
            MiUtils.MiAppPreferences.setMoneyCount(getContext(), cash);
        }*/
    }

    /*public void updateUserWallet(){
        callUserWallet();
    }*/
}
