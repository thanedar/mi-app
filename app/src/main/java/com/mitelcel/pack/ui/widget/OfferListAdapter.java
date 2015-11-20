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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

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

    public static class HolderMainItem{

        @InjectView(R.id.item_head_rewards_title)
        public TextView title;
        @InjectView(R.id.item_head_rewards_coins)
        public TextView description;
        @InjectView(R.id.item_install_play_btn)
        public TextView playInstall;
        @InjectView(R.id.item_message_gift)
        public TextView messageGift;
        @InjectView(R.id.item_gift_coins)
        public TextView giftCoins;
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

            int mResIcon = R.drawable.ic_cash;
            giftCoins.setCompoundDrawablesWithIntrinsicBounds(mResIcon, 0, 0, 0);

            playInstall.setText(context.getString(R.string.accept));
            playInstall.setTag(R.string.installed_tag, true);
            playInstall.setTag(R.string.package_tag, offerItemHolder.description);

            giftCoins.setText("$s");

            divider.setVisibility(View.GONE);

            description.setText(offerItemHolder.description);
            Picasso.with(context).load(offerItemHolder.urlIcon).into(borderImageView);
            Picasso.with(context).load(offerItemHolder.urlCard).placeholder(R.drawable.placeholder_thumb).into(backGroundImageView);

            MiLog.i("OfferListAdapter", "offerItemHolder [ " + offerItemHolder.toString() + " ]");
        }

        public void setListener(View.OnClickListener l){
            playInstall.setOnClickListener(l);
        }

    }

    private Observable<Bitmap> loadBitmap(String url, final ImageView imageView) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.placeholder_thumb).build();
        return Observable
                .create(subscriber -> {
                    ImageLoader.getInstance()
                            .displayImage(url, imageView, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String imageUri, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                    subscriber.onError(failReason.getCause());
                                }

                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    subscriber.onNext(loadedImage);
                                    subscriber.onCompleted();
                                }

                                @Override
                                public void onLoadingCancelled(String imageUri, View view) {
                                    subscriber.onError(new Throwable("Image  loading cancelled"));
                                }
                            });
                })
                ;
    }

}
