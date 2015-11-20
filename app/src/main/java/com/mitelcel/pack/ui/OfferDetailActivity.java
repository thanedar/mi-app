package com.mitelcel.pack.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
    @InjectView(R.id.game_details_center_img)
    BorderImageView mCenterImage;
    BitmapDrawable mBitmapDrawable;
    @InjectView(R.id.game_details_bg_img)
    ImageView mImageBackground;
    ColorDrawable mBackground;
    TextView btnInstall;
    @InjectView(R.id.game_detail_description_tv)
    TextView description;
    @InjectView(R.id.game_title)
    TextView gameTitle;
    String gamePackageId;
    String gameDescription;
    String urlBackground;

    // center image
    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;

    //btnInstall
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
    String valueGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE = getPackageName();

        setContentView(R.layout.activity_offer_detail);
        ButterKnife.inject(this);

//        rating = (RatingBar)findViewById(R.id.ratingBar);
        // Retrieve the data we need for the picture/description to display and
        // the thumbnail to animate it from
        Bundle bundle = getIntent().getExtras();

        for(String key : bundle.keySet()) {
            MiLog.i(TAG, String.format("Game Detail key %s with value %s", key, bundle.get(key)));
        }

        final OfferDetailHolder beanImage = OfferDetailHolder.createObject(bundle, PACKAGE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(beanImage.getTitle());

        MiLog.i(TAG, "beanImage - " + beanImage.toString());

        gameTitle.setText(beanImage.getTitle());
        gamePackageId = beanImage.getPackageId();
        urlBackground = beanImage.getUrlBackground();
        gameDescription = beanImage.getDescription();
        description.setText(gameDescription);

        mOriginalOrientation = bundle.getInt(PACKAGE + ".orientation");
        mBackground = new ColorDrawable(Color.WHITE);
        mShadowLayout.setBackground(mBackground);
        mCenterImage.setAlpha(255);

        btnInstall = (TextView)findViewById(R.id.game_detail_install_play);
        btnInstall.setOnClickListener(this);

        Picasso.with(getApplicationContext()).load(beanImage.getUrlBackground()).into(mImageBackground);
        Picasso.with(getApplicationContext()).load(beanImage.getUrlThumb()).into(mCenterImage);


        configAnimationCenterImage(savedInstanceState, beanImage);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        valueGift = beanImage.getRewardInstallPlay();

        ((MiApp)getApplication()).getAppComponent().inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnInstall.setText(getString(R.string.accept));
        description.setText(gameDescription);
    }

    private void configAnimationInstallPlayBtn(Bundle savedInstanceState, final OfferDetailHolder beanImage) {
        if (savedInstanceState == null) {
            ViewTreeObserver observer = btnInstall.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    btnInstall.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    btnInstall.getLocationOnScreen(screenLocation);
                    mBtnLeftDelta = beanImage.getBtnInstallPlayLeft() - screenLocation[0];
                    mBtnTopDelta = beanImage.getBtnInstallPlayTop() - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mBtnWidthScale = (float) beanImage.getBtnInstallPlayWidth() / btnInstall.getWidth();
                    mBtnHeightScale = (float) beanImage.getBtnInstallPlayHeight() / btnInstall.getHeight();

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
        btnInstall.setPivotX(0);
        btnInstall.setPivotY(0);
        btnInstall.setScaleX(mBtnWidthScale);
        btnInstall.setScaleY(mBtnHeightScale);
        btnInstall.setTranslationX(mBtnLeftDelta);
        btnInstall.setTranslationY(mBtnTopDelta);

        // We'll fade the text in later
        description.setAlpha(0);


        // Animate scale and translation to go from thumbnail to full size
        mCenterImage.animate().setDuration(duration*2).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate the description in after the image animation
                        // is done. Slide and fade the text in from underneath
                        // the picture.
                        description.setTranslationY(-description.getHeight());
                        description.animate().setDuration(duration / 2).
                                translationY(0).alpha(1).
                                setInterpolator(sDecelerator);
                    }
                });

        // Animate scale and translation
        btnInstall.animate().setDuration(duration*2).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnInstall.setPivotX(btnInstall.getWidth()/2);
                btnInstall.setPivotY(btnInstall.getHeight()/2);
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
        description.animate().translationY(description.getHeight()).alpha(0).
                setDuration(duration/2).setInterpolator(sAccelerator);

        // Animate image back to thumbnail size/location
        mCenterImage.animate().setDuration(duration).
                scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta).
                withEndAction(endAction);

        btnInstall.animate().setDuration(duration).
                scaleX(mBtnWidthScale).scaleY(mBtnHeightScale).
                translationX(mBtnLeftDelta).translationY(mBtnTopDelta).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                gameTitle.setVisibility(View.INVISIBLE);
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
            btnInstall.animate().alpha(0);
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
            runExitAnimation(new Runnable() {
                public void run() {
                    // *Now* go ahead and exit the activity
                    finish();
                }
            });
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
        runExitAnimation(new Runnable() {
            public void run() {
                // *Now* go ahead and exit the activity
                finish();
            }
        });
    }


    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
//        v.setTag(R.string.installed_tag, MiUtils.Info.isPackageInstalled(getApplicationContext(), gamePackageId));
//        v.setTag(R.string.package_tag, gamePackageId);
//        v.setTag(R.string.url_redirect, urlGame);
//        SkillUtils.logicButtonInstallPlay(getApplicationContext(), v, "").start();
//        MiUtils.logicButtonInstallPlay(GameDetail.this, v, rewardsRedirectResponse.getRedirectUrl()).start();
    }
}
