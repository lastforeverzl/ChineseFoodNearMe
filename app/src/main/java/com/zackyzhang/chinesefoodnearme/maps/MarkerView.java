package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lei on 3/30/17.
 */

public abstract class MarkerView  extends View {

    protected Point point;
    protected LatLng latLng;

    private MarkerView(Context context) {
        super(context);
    }

    public MarkerView(Context context, LatLng latLng, Point point) {
        this(context);
        this.latLng = latLng;
        this.point = point;
    }

    public double lat() {
        return latLng.latitude;
    }

    public double lng() {
        return latLng.longitude;
    }

    public Point point() {
        return point;
    }

    public LatLng latLng() {
        return latLng;
    }

    public abstract void show();

    public abstract void hide();

    public abstract void refresh(final Point point);

}
