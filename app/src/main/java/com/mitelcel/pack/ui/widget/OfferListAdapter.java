package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.R;
import com.mitelcel.pack.bean.ui.OfferItemHolder;
import com.mitelcel.pack.utils.MiLog;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class OfferListAdapter extends ArrayAdapter<OfferItemHolder> implements View.OnClickListener{

    private static final int CORNER_RADIUS = 8; // dips
    private static final int MARGIN = 12; // dips

    private Bitmap bitmap;

    private HolderMainItem item;

    public OfferListAdapter(Context context, int resource) {
        super(context, resource);
        final float density = context.getResources().getDisplayMetrics().density;
        init();
    }

    public void init(){
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.place_holder);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final OfferItemHolder offerItemHolder = getItem(position);

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_offer_listcard_with_btn, null);
            item = new HolderMainItem(convertView);

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                CardView cv = (CardView)convertView.findViewById(R.id.item_card);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,0,0);
                cv.setLayoutParams(lp);
            }
            convertView.setTag(item);
        }
        else{
            item = (HolderMainItem)convertView.getTag();
        }

        item.setData(offerItemHolder, getContext());
        item.setListener(this);

        return convertView;
    }

    @Override
    public void onClick(final View v) {
//        MiUtils.logicButtonInstallPlay(getContext(), v, rewardsRedirectResponse.getRedirectUrl()).start();
    }

    public static class HolderMainItem {

        @InjectView(R.id.item_offer_description)
        public TextView description;
        @InjectView(R.id.item_offer_click_btn)
        public TextView offerBtn;
        @InjectView(R.id.item_border_imageview)
        public BorderImageView borderImageView;
        @InjectView(R.id.item_background_imageview)
        public ImageView backGroundImageView;
        @InjectView(R.id.item_divider)
        public View divider;

        public HolderMainItem(View view) {
            ButterKnife.inject(this, view);
        }

        public void setData(OfferItemHolder offerItemHolder, Context context){

            offerBtn.setText(offerItemHolder.buttonText);
            offerBtn.setTag(R.string.installed_tag, true);
            offerBtn.setTag(R.string.package_tag, offerItemHolder.description);

            divider.setVisibility(View.GONE);

            description.setText(offerItemHolder.description);
            Picasso.with(context).load(offerItemHolder.urlIcon).into(borderImageView);
            Picasso.with(context).load(offerItemHolder.urlCard).placeholder(R.drawable.placeholder_thumb).into(backGroundImageView);

            MiLog.i("OfferListAdapter", "offerItemHolder [ " + offerItemHolder.toString() + " ]");
        }

        public void setListener(View.OnClickListener l){
            offerBtn.setOnClickListener(l);
        }

    }
}
