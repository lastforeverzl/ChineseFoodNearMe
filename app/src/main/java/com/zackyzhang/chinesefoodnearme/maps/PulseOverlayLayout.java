package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;

/**
 * Created by lei on 3/30/17.
 */

public class PulseOverlayLayout extends MapOverlayLayout {
    private static final String TAG = "PulseOverlayLayout";
    private static final int ANIMATION_DELAY_FACTOR = 100;

    private int scaleAnimationDelay = 100;

    public PulseOverlayLayout(Context context) {
        super(context);
    }

    public PulseOverlayLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public void createAndShowMarker(final int position, Business.CoordinatesBean coordinates) {
        LatLng latLng = new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
        Log.d(TAG, latLng.toString());
        PulseMarkerView marker = createPulseMarkerView(position, googleMap.getProjection().toScreenLocation(latLng), latLng);
        marker.showWithDelay(scaleAnimationDelay);
        scaleAnimationDelay += ANIMATION_DELAY_FACTOR;
    }

    private PulseMarkerView createPulseMarkerView(final int position, final Point point, final LatLng latLng) {
        PulseMarkerView pulseMarkerView = new PulseMarkerView(getContext(), latLng, point, position);
        addMarker(pulseMarkerView);
        return pulseMarkerView;
    }

    public void showMarker(final int position) {
        ((PulseMarkerView)markersList.get(position)).pulse();
    }

    public void onBackPressed(final Location location) {
        moveCamera(location);
//        removeStartAndFinishMarkers();
//        removeCurrentPolyline();
        showAllMarkers();
        refresh();
    }
}
