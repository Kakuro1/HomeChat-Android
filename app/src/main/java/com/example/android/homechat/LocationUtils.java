package com.example.android.homechat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

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
    private static boolean permissionGranted = false;

    private static FusedLocationProviderClient fusedLocationClient;

    private static LocationManager locationManager;
    private static LocationListener locationListener;


    /**
     * Requests the fine location permission if it hasn't been granted yet.
     */
    private static void requestPermission(AppCompatActivity activity) {
        if (!isPermissionGranted(activity)) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(activity, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    // TODO not a static function, but Override complains if it is
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionGranted = true;
        } else {
            // Display the missing permission error dialog when the fragments resume.
            permissionGranted = false;
        }
    }

    private static boolean isPermissionGranted(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is granted.
            permissionGranted = true;
            return true;
        }
        permissionGranted = false;
        return false;
    }

    private static LocationManager getLocationManager(Context context) {
        if (locationManager == null) {
            // Acquire a reference to the system Location Manager
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        return locationManager;
    }

    private static FusedLocationProviderClient getFusedLocationClient(AppCompatActivity activity) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        }
        return fusedLocationClient;
    }

    public static boolean attachLocationListener(AppCompatActivity activity, LocationListener locationListener) {
        // Register the listener with the Location Manager to receive location updates
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermission(activity);
            return false;
        } else {
            LocationUtils.locationListener = locationListener;
            getLocationManager(activity).requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
            return true;
        }
    }

    public static void detachLocationListener(Context context) {
        getLocationManager(context).removeUpdates(locationListener);
    }

    public static Task<Location> getLastLocation(AppCompatActivity activity) {
        return getFusedLocationClient(activity).getLastLocation();
    }
}
