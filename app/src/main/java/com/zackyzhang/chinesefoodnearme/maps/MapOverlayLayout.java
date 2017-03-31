package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 3/30/17.
 */

public class MapOverlayLayout<V extends MarkerView> extends FrameLayout {

    protected List<V> markersList;
    protected GoogleMap googleMap;

    public MapOverlayLayout(Context context) {
        this(context, null);
    }

    public MapOverlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        markersList = new ArrayList<>();
    }

    public void setupMap(final GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void moveCamera(LatLngBounds latLngBounds, int width, int height) {
        Log.d("MapDetailPresenter", latLngBounds.toString());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, 150));
    }

    public void setOnCameraIdleListener(GoogleMap.OnCameraIdleListener listener) {
        googleMap.setOnCameraIdleListener(listener);
    }

    public void setOnCameraMoveListener(GoogleMap.OnCameraMoveListener listener) {
        googleMap.setOnCameraMoveListener(listener);
    }

    protected void addMarker(final V view) {
        markersList.add(view);
        addView(view);
    }

    public void refresh() {
        Projection projection = googleMap.getProjection();
        for (int i = 0; i < markersList.size(); i++) {
            refresh(i, projection.toScreenLocation(markersList.get(i).latLng()));
        }
    }

    private void refresh(int position, Point point) {
        markersList.get(position).refresh(point);
    }
}