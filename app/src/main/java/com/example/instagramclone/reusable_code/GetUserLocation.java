package com.example.instagramclone.reusable_code;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

public class GetUserLocation {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Location mLocation;
    public final int MY_PERMISSIONS_REQUEST_LOCATION=4000;

    Context context;

    public GetUserLocation(Context context,Activity activity) {
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, request for permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }

        // Request location updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult.getLastLocation() != null) {
                    mLocation = locationResult.getLastLocation();
                    ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                        @Override
                        public void done(ParseModel parseModel, ParseException e) {
                            if (e == null) {
                                ParseGeoPoint location = new ParseGeoPoint(mLocation.getLatitude(),mLocation.getLongitude());
                                parseModel.setLocation(location);
                                parseModel.pinInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(context, "Location saved", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Error: Location not saved", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    });

                    // Remove the location updates after getting the location once
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                } else {
                    Toast.makeText(context, "Location not Detected", Toast.LENGTH_SHORT).show();
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(LocationRequest.create().setNumUpdates(1).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), locationCallback, null);
    }
}
