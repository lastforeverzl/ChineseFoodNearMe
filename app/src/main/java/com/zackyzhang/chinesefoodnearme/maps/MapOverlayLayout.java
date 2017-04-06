package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.zackyzhang.chinesefoodnearme.MapsUtil;
import com.zackyzhang.chinesefoodnearme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 3/30/17.
 */

public class MapOverlayLayout<V extends MarkerView> extends FrameLayout {

    protected List<V> markersList;
    protected GoogleMap googleMap;
    protected ArrayList<LatLng> polylines;
    protected Polyline currentPolyline;

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

    public void moveCamera(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
    }

    public void showAllMarkers() {
        if (markersList.get(markersList.size() - 1) instanceof TravelTimeMarkerView) {
            markersList.remove(markersList.size() - 1);
        }
        for (int i = 0; i < markersList.size(); i++) {
            markersList.get(i).show();
        }
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

    public void addPolyline(ArrayList<LatLng> polylines) {
        this.polylines = polylines;
        PolylineOptions options = new PolylineOptions();
        for (int i = 1; i < polylines.size(); i++) {
            options.add(polylines.get(i - 1), polylines.get(i)).width(10).color(ContextCompat.getColor(getContext(), R.color.polyline_color)).geodesic(true);
        }
        currentPolyline = googleMap.addPolyline(options);
    }

    public void animateCamera(LatLngBounds bounds) {
        int width = getWidth();
        int height = getHeight();
        int padding = MapsUtil.DEFAULT_MAP_PADDING;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
    }

    public void hideAllMarkers() {
        for (int i = 0; i < markersList.size(); i++) {
            markersList.get(i).hide();
        }
    }

    protected void removeMarker(final V view) {
        markersList.remove(view);
        removeView(view);
    }

    public void removeCurrentPolyline() {
        if (currentPolyline != null) currentPolyline.remove();
    }
}