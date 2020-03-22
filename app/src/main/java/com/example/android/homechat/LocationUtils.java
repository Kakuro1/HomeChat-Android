package com.example.android.homechat;

import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

public abstract class LocationUtils implements
        ActivityCompat.OnRequestPermissionsResultCallback{

    private static final String TAG = "LocationUtils";

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private FusedLocationProviderClient fusedLocationClient;


    public static Location getCurrentLocation() {

        return null;
    }
}
