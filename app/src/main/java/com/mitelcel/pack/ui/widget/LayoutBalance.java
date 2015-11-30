package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetCurrentBalance;
import com.mitelcel.pack.api.bean.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class LayoutBalance extends FrameLayout
        implements SharedPreferences.OnSharedPreferenceChangeListener, Observer<BeanGetCurrentBalanceResponse>
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(MiUtils.MiAppPreferences.MONEY_BALANCE) || key.equals(MiUtils.MiAppPreferences.CURRENCY_SYMBOL))
            setCurrentBalance();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((MiApp)getContext()).getAppComponent().inject(this);
        MiUtils.MiAppPreferences.registerListener(this);

        //getCurrentBalance();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MiUtils.MiAppPreferences.unRegisterListener(this);
        miApiClient = null;
    }

    private void setCurrentBalance(){
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cash, 0, 0, 0);
        String symbol = MiUtils.MiAppPreferences.getCurrencySymbol();
        String value = MiUtils.MiAppPreferences.getCurrentBalanceString();
        textView.setText(symbol + value);
    }

    public void hideProgressBar(){
        if(progressBar != null) progressBar.setVisibility(View.GONE);
        if(textView !=null) textView.setVisibility(View.VISIBLE);
    }

    public void showProgressBar(){
        if(progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if(textView != null) textView.setVisibility(View.GONE);
    }

    public void getCurrentBalance(){
        BeanGetCurrentBalance beanGetCurrentBalance = new BeanGetCurrentBalance();
        MiLog.i(LayoutBalance.class.getSimpleName(), "beanGetCurrentBalance [ " + beanGetCurrentBalance.toString() + " ]");
        showProgressBar();
        miApiClient.get_current_balance(beanGetCurrentBalance)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        hideProgressBar();
    }

    @Override
    public void onError(Throwable e) {
        hideProgressBar();
    }

    @Override
    public void onNext(BeanGetCurrentBalanceResponse beanResponse) {
        MiLog.i(LayoutBalance.class.getSimpleName(), "BeanGetCurrentBalanceResponse response [ " + beanResponse.toString() + " ]");
        if (beanResponse != null && beanResponse.getError().getCode() == Config.SUCCESS) {
            String cash = beanResponse.getResult().getCurrentBalance();
            MiUtils.MiAppPreferences.setCurrentBalance(Float.parseFloat(cash));
        }
    }
}
