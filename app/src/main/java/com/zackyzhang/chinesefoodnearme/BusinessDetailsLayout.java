package com.zackyzhang.chinesefoodnearme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lei on 3/31/17.
 */

public class BusinessDetailsLayout extends CoordinatorLayout {

    @BindView(R.id.cardview)
    CardView cardViewContainer;
    @BindView(R.id.headerImage)
    ImageView imageViewPlaceDetails;
    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.priceTitle)
    TextView priceTitle;
    @BindView(R.id.phone)
    TextView phoneNumber;

    private static Activity mActivity;
    private static double mLatitude;
    private static double mLongitude;

    public BusinessDetailsLayout(final Context context) {
        this(context, null);
    }

    public BusinessDetailsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    private void setData(Business place) {
        mLatitude = place.getCoordinates().getLatitude();
        mLongitude = place.getCoordinates().getLongitude();
        Picasso.with(mActivity).load(place.getImageUrl()).fit().into(imageViewPlaceDetails);
        textViewTitle.setText(place.getName());
        rating.setText(ratingTextView(place));
        priceTitle.setText(place.getPrice());
        phoneNumber.setText(place.getDisplayPhone());
    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName, final Business data) {
        mActivity = activity;
        BusinessDetailsLayout businessDetailsLayout = (BusinessDetailsLayout) activity.getLayoutInflater().inflate(R.layout.item_business_detail, container, false);
        businessDetailsLayout.setData(data);

        TransitionSet set = new ShowDetailsTransitionSet(activity, transitionName, sharedView, businessDetailsLayout);
        Scene scene = new Scene(container, (View) businessDetailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }

    public static Scene hideScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        BusinessDetailsLayout businessDetailsLayout = (BusinessDetailsLayout) container.findViewById(R.id.business_details_container);

        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, businessDetailsLayout);
        Scene scene = new Scene(container, (View) businessDetailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }

    private String ratingTextView(Business business) {
        return business.getRating() + " " + business.getReviewCount() + " reviews";
    }

    @OnClick(R.id.takeMe)
    public void onClick() {
        googleNavigation();
    }

    private void googleNavigation() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mLatitude + "," + mLongitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mActivity.startActivity(mapIntent);
    }
}
