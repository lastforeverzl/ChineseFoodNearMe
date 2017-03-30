package com.zackyzhang.chinesefoodnearme;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.zackyzhang.chinesefoodnearme.network.AccessToken;
import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;
import com.zackyzhang.chinesefoodnearme.network.ServiceGenerator;
import com.zackyzhang.chinesefoodnearme.network.YelpFusionApiService;
import com.zackyzhang.chinesefoodnearme.network.YelpFusionAuthService;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity_bk extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "MainActivity_bk";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private YelpFusionApiService mYelpFusionApiService;
    private YelpFusionAuthService mYelpFusionAuthService;
    private SearchResponse mSearchResponse;

    @BindView(R.id.tv)
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        getAccessToken("HRvqjoSkF7dZCKfoxT9vJA", "EQ8Crg9FaoTLUMV9B9fYRTwTgcWy4YrhjkKwgKzSY9o7EmYUtXZTPLNvI51BXP2R");
    }

    private void getAccessToken(String clientId, String clientSecret) {
        mYelpFusionAuthService = ServiceGenerator.getAccessToken();
        mYelpFusionAuthService.getAccessToken("client_credentials", clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccessToken>() {
                    @Override
                    public void accept(AccessToken accessToken) throws Exception {
                        Log.d(TAG, "AccessToken onResponse: " + accessToken.getAccessToken());
                        createService(accessToken);
                    }
                });
    }

    private void createService(AccessToken accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("term", "indian food");
        params.put("latitude", "40.581140");
        params.put("longitude", "-111.914184");
        mYelpFusionApiService = ServiceGenerator.createService(YelpFusionApiService.class, accessToken);
        mYelpFusionApiService.getBusinessSearch(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchResponse>() {
                    @Override
                    public void accept(SearchResponse searchResponse) throws Exception {
                        Log.d(TAG, "searchResponse total: " + searchResponse.getTotal());
                        mSearchResponse = searchResponse;
                        mTextView.setText(String.valueOf(searchResponse.getBusinesses().get(0).getName()));
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        LatLng testLatLng = new LatLng(40.5737858335711, -111.9188196525825);
//        mMap.addMarker(new MarkerOptions().position(dublin).title("My Favorite City"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testLatLng, 13));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }
}
