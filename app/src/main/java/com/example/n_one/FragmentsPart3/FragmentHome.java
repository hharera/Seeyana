package com.example.n_one.FragmentsPart3;


import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.utils.FetchLocationHelper;
import com.example.n_one.utils.LocationListener;
import com.example.n_one.utils.QueryPreferences;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "FragmentHome";

    private MapView mapView;
    private GoogleMap map;
    private Marker marker1, marker2, marker3;

    private ImageView current_loc, mImageViewSpinner;
    private View view;
    private Spinner mSpinnerExpertises;
    private TextView mTextViewChosenJob;



    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        init(savedInstanceState);
        return view;
    }

    private void init(Bundle savedInstanceState) {
        current_loc=view.findViewById(R.id.current_loc);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mTextViewChosenJob = view.findViewById(R.id.home_chosen_job_text_view);
        mImageViewSpinner = view.findViewById(R.id.arrow_choose_job);
        mSpinnerExpertises = view.findViewById(R.id.expertises_spinner);
        List<String> expertises = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.expertises)));
        expertises.set(0, "All");
        ArrayAdapter<String> spinnerAdapter;

        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerExpertises.setAdapter(spinnerAdapter);


        mImageViewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mSpinnerExpertises.setVisibility(View.VISIBLE);
                mSpinnerExpertises.performClick();
            }
        });

        mSpinnerExpertises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                view.setVisibility();
                Log.d(TAG, "Selected Expertise: " + spinnerAdapter.getItem(position));

//                ((TextView)view).setTextColor(getResources().getColor(R.color.transparent));
                mSpinnerExpertises.setVisibility(View.INVISIBLE);
                mTextViewChosenJob.setText(spinnerAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAdapter.addAll(expertises);
        spinnerAdapter.notifyDataSetChanged();

        current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(map.getMyLocation() != null) { // Check to ensure coordinates aren't null, probably a better way of doing this...
//                    mapView.setCenterCoordinate(new LatLngZoom(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude(), 20), true);
//                }
fetchLocation();

            }
        });
    }

    private void fetchLocation() {

        if(QueryPreferences.isLocationPermisstionGranted(getActivity())) {
            new FetchLocationHelper(getActivity(), new LocationListener() {
                @Override
                public void onLocationListener(Location location) {
                    if (location != null) {
                        mapView.getMapAsync(FragmentHome.this::onMapReady);
                    }
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {// Uses a custom icon.
            marker1 = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(30.034465, 31.268952))
                    .title("Cairo")
                    .snippet("")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_1)));
            marker2 = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(30.0444, 31.2357))
                    .title("Cairo")
                    .snippet("Population: 12,230,350")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_2)));
            marker3 = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(30.0131, 31.2089))
                    .title("Giza")
                    .snippet("Population: 4,028,062")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_3)));


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (marker1.equals(marker)) {
                        Log.d(TAG, "Marker1 Cairo Clicked");
                    } else if (marker2.equals(marker)) {
                        Log.d(TAG, "Marker2 Cairo Clicked");
                    } else if (marker3.equals(marker)) {
                        Log.d(TAG, "Marker Giza Clicked");
                    }

                    return false;
                }
            });

            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.setMyLocationEnabled(true);
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.0444, 31.2357),12));

            if (SeeyanaApp.getInstance().getProvider() != null) {
                String providerAddress = SeeyanaApp.getInstance().getProvider().getCity() + ", " +
                        SeeyanaApp.getInstance().getProvider().getCountry();

                Location myLocation = SeeyanaApp.getInstance().getCurrentLocation();
                if (myLocation != null) {

                }
//            Geocoder geocoder;
//            List<Address> addresses;
//            geocoder = new Geocoder(getActivity(), Locale.getDefault());
//            try {
//                addresses = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
//                Log.d(TAG, "Test address from location: " + addresses);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

                LatLng latLng;
                if (myLocation == null) {
                    latLng = QueryPreferences.getStoredLastLocation(getActivity());
                    if (latLng == null) {
                        latLng = getLocationFromAddress(providerAddress);
                    }
                } else {
                    QueryPreferences.setStoredLastLocation(getActivity(), new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
                    latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                }

//            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//            LatLng latLng = getLocationFromAddress(providerAddress);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;


        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            return latLng;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
