package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.R;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse.UserActivity;
import com.mitelcel.pack.utils.MiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshu.thanedar on 12/11/2015
 */
public class RecentItemLayout extends CardView {

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

        tvDescription.setText(userActivity.getActivityType());
        tvTime.setText("For now ID: " + activityId);

        int iconRes = MiUtils.getResIdFromAction(userActivity.getActivityType());
        icon.setImageResource(iconRes);
    }
}
