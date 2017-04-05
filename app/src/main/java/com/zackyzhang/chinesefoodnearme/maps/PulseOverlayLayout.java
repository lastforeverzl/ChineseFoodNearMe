package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.zackyzhang.chinesefoodnearme.data.entity.Business;

/**
 * Created by lei on 3/30/17.
 */

public class PulseOverlayLayout extends MapOverlayLayout {
    private static final String TAG = "PulseOverlayLayout";
    private static final int ANIMATION_DELAY_FACTOR = 50;

    private int scaleAnimationDelay = 100;
    private TravelTimeMarkerView finishMarker;

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
        removeStartAndFinishMarkers();
        removeCurrentPolyline();
        showAllMarkers();
        refresh();
    }

    private void removeStartAndFinishMarkers() {
        removeFinishMarker();
    }

    public void drawStartAndFinishMarker(String duration) {
        addFinishMarker((LatLng) polylines.get(polylines.size() - 1), duration);
        setOnCameraIdleListener(null);
    }

    public void addFinishMarker(final LatLng latLng, String duration) {
        finishMarker = createTravelTimeMarkerView(latLng, duration);
        finishMarker.updatePulseViewLayoutParams(googleMap.getProjection().toScreenLocation(latLng));
        addMarker(finishMarker);
        finishMarker.show();
    }

    private PulseMarkerView createPulseMarkerView(final LatLng latLng) {
        return new PulseMarkerView(getContext(), latLng, googleMap.getProjection().toScreenLocation(latLng));
    }

    private TravelTimeMarkerView createTravelTimeMarkerView(final LatLng latLng, String duration) {
        return new TravelTimeMarkerView(getContext(), latLng, googleMap.getProjection().toScreenLocation(latLng), duration);
    }

    public void removeFinishMarker() {
        removeMarker(finishMarker);
    }

}
