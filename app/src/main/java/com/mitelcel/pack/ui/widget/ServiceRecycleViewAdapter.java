package com.mitelcel.pack.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.bean.api.resp.BeanGetServiceListResponse.Service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sudhanshu.thanedar on 12/11/2015
 */
public class ServiceRecycleViewAdapter extends EmptyRecyclerView.Adapter<ServiceRecycleViewAdapter.ServiceViewHolder> {

    List<Service> services;

    @Inject
    MiApiClient miApiClient;

    public ServiceRecycleViewAdapter(List<Service> services) {
        this.services= services;
    }

    public ServiceRecycleViewAdapter() {
        this.services = new ArrayList<>();
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServiceItemLayout serviceItemLayout = (ServiceItemLayout) View.inflate(parent.getContext(), R.layout.item_service, null);
        return new ServiceViewHolder(serviceItemLayout);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        holder.bind(services.get(position));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static final class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ServiceItemLayout itemView;
        private static ClickListener clickListener;

        public ServiceViewHolder(ServiceItemLayout itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemView = itemView;
        }

        public void bind(Service service) {
            itemView.bind(service);
        }

        @Override
        public void onClick(View view) {
//            MiLog.i("ServiceViewHolder", "onClick detected in ViewHolder");
            clickListener.onRecentItemClick(getAdapterPosition(), view);
        }
    }

    public void addData(List<Service> newListService) {
        int start = services.size();
        services.addAll(newListService);
        notifyItemRangeInserted(start, services.size());
    }

    public void replaceData(List<Service> services) {
        this.services.clear();
        this.services.addAll(services);
        notifyDataSetChanged();
    }

    public void clearData() {
        services.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ServiceViewHolder.clickListener = clickListener;
    }

    public interface ClickListener {
        void onRecentItemClick(int position, View view);
    }

    /*public class ServiceItemLayout extends RelativeLayout {

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

        public void bind(Service service) {
            tvDescription.setText(service.getDescription());

            tvTime.setText("ID " + service.getServiceId());

            Picasso.with(getContext().getApplicationContext()).load(service.getUrlIcon()).into(icon);
        }
    }*/
}
