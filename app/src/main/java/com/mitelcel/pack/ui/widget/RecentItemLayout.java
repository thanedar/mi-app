package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse.UserActivity;
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
        long activityId = (userActivity.getActivityId());
        int activityType = userActivity.getActivityType();

//        tvDescription.setText(String.format(R.string.recent_recharge, userActivity.getActivityId());

        if(activityType == Config.ACTION_TYPE_RECHARGE)
            tvDescription.setText(R.string.recharge);
        else if(activityType == Config.ACTION_TYPE_TRANSFER)
            tvDescription.setText(R.string.transfer_balance);

        tvTime.setText(userActivity.getActivityDatetime());

        int iconRes = MiUtils.getResIdFromAction(activityType);
        icon.setImageResource(iconRes);
    }
}
