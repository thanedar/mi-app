package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse.UserActivity;
import com.mitelcel.pack.bean.ui.BeanRechargeParams;
import com.mitelcel.pack.bean.ui.BeanTransferParams;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshu.thanedar on 12/11/2015
 */
public class RecentItemLayout extends RelativeLayout {

    @InjectView(R.id.item_recent_description)
    TextView tvDescription;
    @InjectView(R.id.item_recent_image)
    ImageView icon;
    @InjectView(R.id.item_recent_overlay)
    ImageView overlay;
    @InjectView(R.id.item_recent_time)
    TextView tvTime;

    public RecentItemLayout(Context context) {
        super(context);
    }

    public RecentItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecentItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bind(UserActivity userActivity){
        int activityType = userActivity.getActivityType();

        Gson gson = new Gson();
        float amount = 0;
        String action = "";
        if(activityType == Config.ACTION_TYPE_RECHARGE){
            BeanRechargeParams bean = gson.fromJson(userActivity.getRequest(), BeanRechargeParams.class);
            if(bean != null) {
                MiLog.i("RecentItem", bean.toString());
                amount = bean.getAmount();
            }
            action = getResources().getString(R.string.recharge_recent_activity, MiUtils.MiAppPreferences.getCurrencySymbol(), amount);
        }
        else if(activityType == Config.ACTION_TYPE_TRANSFER){
            String target = "";
            BeanTransferParams bean = gson.fromJson(userActivity.getRequest(), BeanTransferParams.class);
            if(bean != null) {
                amount = bean.getAmount();
                target = bean.getTarget();
            }
            action = getResources().getString(R.string.transfer_recent_activity, MiUtils.MiAppPreferences.getCurrencySymbol(), amount, target);
        }

        tvDescription.setText(action);
//        tvDescription.setText(userActivity.getAppDisplayText());

        tvTime.setText(userActivity.getActivityDatetime());

        int iconRes = MiUtils.getResIdFromAction(activityType);
        icon.setImageResource(iconRes);
    }
}
