package com.mitelcel.pack.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.bean.ui.OfferDetailHolder;
import com.mitelcel.pack.ui.widget.BorderImageView;
import com.mitelcel.pack.utils.MiLog;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OfferDetailActivity extends BaseChildActivity implements View.OnClickListener{

    @InjectView(R.id.offer_details_container)
    RelativeLayout mShadowLayout;
    @InjectView(R.id.offer_detail_center_img)
    BorderImageView mCenterImage;
    @InjectView(R.id.offer_detail_bg_img)
    ImageView mImageBackground;
    ColorDrawable mBackground;
    @InjectView(R.id.offer_detail_btn)
    TextView btnOffer;
    @InjectView(R.id.offer_detail_description)
    TextView tvDescription;
    @InjectView(R.id.offer_detail_title)
    TextView offerTitle;
    String offerPackageId;
    String offerDescription;
    String urlBackground;
    String btnText;

    // center image
    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;

    //btnOffer
    int mBtnLeftDelta;
    int mBtnTopDelta;
    float mBtnWidthScale;
    float mBtnHeightScale;

    private int mOriginalOrientation;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();

    private static final int ANIM_DURATION = 500;

    public static String PACKAGE;
    public static String TAG = OfferDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE = getPackageName();

        setContentView(R.layout.activity_offer_detail);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();

        for(String key : bundle.keySet()) {
            MiLog.i(TAG, String.format("Offer Detail key %s with value %s", key, bundle.get(key)));
        }

        final OfferDetailHolder beanImage = OfferDetailHolder.createObject(bundle, PACKAGE);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setTitle(beanImage.getTitle());
        }

        MiLog.i(TAG, "beanImage - " + beanImage.toString());

        offerTitle.setText(beanImage.getTitle());
        offerPackageId = beanImage.getPackageId();
        urlBackground = beanImage.getUrlBackground();
        offerDescription = beanImage.getDescription();
        btnText = beanImage.getBtnText();

        mOriginalOrientation = bundle.getInt(PACKAGE + ".orientation");
        mBackground = new ColorDrawable(Color.WHITE);
        mShadowLayout.setBackground(mBackground);
        mCenterImage.setAlpha(255);

        tvDescription.setText(offerDescription);
        btnOffer.setText(btnText);
        btnOffer.setOnClickListener(this);

        Picasso.with(getApplicationContext()).load(beanImage.getUrlBackground()).into(mImageBackground);
        Picasso.with(getApplicationContext()).load(beanImage.getUrlThumb()).into(mCenterImage);

        configAnimationCenterImage(savedInstanceState, beanImage);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    private void configAnimationInstallPlayBtn(Bundle savedInstanceState, final OfferDetailHolder beanImage) {
        if (savedInstanceState == null) {
            ViewTreeObserver observer = btnOffer.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    btnOffer.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    btnOffer.getLocationOnScreen(screenLocation);
                    mBtnLeftDelta = beanImage.getBtnOfferLeft() - screenLocation[0];
                    mBtnTopDelta = beanImage.getBtnOfferTop() - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mBtnWidthScale = (float) beanImage.getBtnOfferWidth() / btnOffer.getWidth();
                    mBtnHeightScale = (float) beanImage.getBtnOfferHeight() / btnOffer.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }
    }

    private void configAnimationCenterImage(Bundle savedInstanceState, final OfferDetailHolder beanImage) {
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mCenterImage.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mCenterImage.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mCenterImage.getLocationOnScreen(screenLocation);
                    mLeftDelta = beanImage.getThumbnailLeft() - screenLocation[0];
                    mTopDelta = beanImage.getThumbnailTop() - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) beanImage.getThumbnailWidth() / mCenterImage.getWidth();
                    mHeightScale = (float) beanImage.getThumbnailHeight() / mCenterImage.getHeight();
                    return true;
                }
            });
        }

        configAnimationInstallPlayBtn(savedInstanceState, beanImage);
    }

    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of the
     * activity is fading in. When the pictue is in place, the text description
     * drops down.
     */
    public void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up

        mCenterImage.setPivotX(0);
        mCenterImage.setPivotY(0);
        mCenterImage.setScaleX(mWidthScale);
        mCenterImage.setScaleY(mHeightScale);
        mCenterImage.setTranslationX(mLeftDelta);
        mCenterImage.setTranslationY(mTopDelta);

        //install btn
        btnOffer.setPivotX(0);
        btnOffer.setPivotY(0);
        btnOffer.setScaleX(mBtnWidthScale);
        btnOffer.setScaleY(mBtnHeightScale);
        btnOffer.setTranslationX(mBtnLeftDelta);
        btnOffer.setTranslationY(mBtnTopDelta);

        // We'll fade the text in later
        tvDescription.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        mCenterImage.animate().setDuration(duration*2).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(() -> {
                    // Animate the tvDescription in after the image animation
                    // is done. Slide and fade the text in from underneath
                    // the picture.
                    tvDescription.setTranslationY(-tvDescription.getHeight());
                    tvDescription.animate().setDuration(duration / 2).
                            translationY(0).alpha(1).
                            setInterpolator(sDecelerator);
                });

        // Animate scale and translation
        btnOffer.animate().setDuration(duration*2).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnOffer.setPivotX(btnOffer.getWidth()/2);
                btnOffer.setPivotY(btnOffer.getHeight()/2);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // Fade in the black background

        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();

        ObjectAnimator mImageBackgroundAnim = ObjectAnimator.ofInt(mImageBackground, "alpha", 0, 255);
        mImageBackgroundAnim.setDuration(duration);
        mImageBackgroundAnim.start();

        // Fade in the black background
        ObjectAnimator relativeLayourBackgroundAnim = ObjectAnimator.ofInt(mShadowLayout, "alpha", 0, 255);
        relativeLayourBackgroundAnim.setDuration(duration);
        relativeLayourBackgroundAnim.start();


        // Animate a color filter to take the image from grayscale to full color.
        // This happens in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(OfferDetailActivity.this, "saturation", 0, 1);
        colorizer.setDuration(duration);
        colorizer.start();

    }

    /**
     * The exit animation is basically a reverse of the enter animation, except that if
     * the orientation has changed we simply scale the picture back into the center of
     * the screen.
     *
     * @param endAction This action gets run after the animation completes (this is
     * when we actually switch activities)
     */
    public void runExitAnimation(final Runnable endAction) {
        final long duration = (long) (ANIM_DURATION );

        // No need to set initial values for the reverse animation; the image is at the
        // starting size/location that we want to start from. Just animate to the
        // thumbnail size/location that we retrieved earlier

        // Caveat: configuration change invalidates thumbnail positions; just animate
        // the scale around the center. Also, fade it out since it won't match up with
        // whatever's actually in the center
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != mOriginalOrientation) {
            mCenterImage.setPivotX(mCenterImage.getWidth() / 2);
            mCenterImage.setPivotY(mCenterImage.getHeight() / 2);
//            mLeftDelta = 0;
//            mTopDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        // First, slide/fade text out of the way
        tvDescription.animate().translationY(tvDescription.getHeight()).alpha(0).
                setDuration(duration/2).setInterpolator(sAccelerator);

        // Animate image back to thumbnail size/location
        mCenterImage.animate().setDuration(duration).
                scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta).
                withEndAction(endAction);

        btnOffer.animate().setDuration(duration).
                scaleX(mBtnWidthScale).scaleY(mBtnHeightScale).
                translationX(mBtnLeftDelta).translationY(mBtnTopDelta).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                offerTitle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        if (fadeOut) {
            mCenterImage.animate().alpha(0);
            btnOffer.animate().alpha(0);
        }

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Fade out background
        ObjectAnimator mImageBackgroundAnim = ObjectAnimator.ofInt(mImageBackground, "alpha", 0);
        mImageBackgroundAnim.setDuration(duration);
        mImageBackgroundAnim.start();

        // Animate the shadow of the image
        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout, "shadowDepth", 1, 0);
        shadowAnim.setDuration(duration);
        shadowAnim.start();

        // Animate a color filter to take the image back to grayscale,
        // in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(OfferDetailActivity.this, "saturation", 1, 0);
        colorizer.setDuration(duration);
        colorizer.start();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            runExitAnimation(OfferDetailActivity.this::finish);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it is complete.
     */
    @Override
    public void onBackPressed() {
        runExitAnimation(OfferDetailActivity.this::finish);
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
    }
}
