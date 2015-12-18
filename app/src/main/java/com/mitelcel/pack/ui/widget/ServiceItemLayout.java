package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitelcel.pack.R;
import com.mitelcel.pack.bean.api.resp.BeanGetServiceListResponse;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshu.thanedar on 12/11/2015
 */
public class ServiceItemLayout extends RelativeLayout {
    @InjectView(R.id.item_recent_description)
    TextView tvDescription;
    @InjectView(R.id.item_recent_image)
    ImageView icon;
    @InjectView(R.id.item_recent_overlay)
    ImageView overlay;
    @InjectView(R.id.item_recent_time)
    TextView tvTime;

    public ServiceItemLayout(Context context) {
        super(context);
    }

    public ServiceItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ServiceItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bind(BeanGetServiceListResponse.Service service) {
        tvDescription.setText(service.getDescription());

        tvTime.setText("ID " + service.getServiceId());

        Picasso.with(getContext().getApplicationContext()).load(service.getUrlIcon()).into(icon);
    }
}
