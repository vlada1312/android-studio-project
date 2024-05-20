package ru.mirea.sosnovskayave.mireaproject;





import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


import androidx.preference.PreferenceManager;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.config.Configuration;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstablishmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablishmentsFragment extends Fragment  {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private boolean isLocationPermissionGranted = false;
    private org.osmdroid.views.MapView mapView = null;
    private MyLocationNewOverlay locationNewOverlay;

    private double latitude;
    private double longitude;
    private String title;
    private  String address;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstablishmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstablishmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstablishmentsFragment newInstance(String param1, String param2) {
        EstablishmentsFragment fragment = new EstablishmentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_establishments, container, false);
        Button openMapEst1Button = view.findViewById(R.id.openMapEst1Button);
        openMapEst1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = 55.868299;
                longitude = 37.618956;
                title = "Хвостатый.ру";
                address = "Северный б-р, 12, Москва, 127490";
                chechPermissions();
            }
        });

        Button openMapEst2Button = view.findViewById(R.id.openMapEst2Button);
        openMapEst2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = 55.766207;
                longitude = 37.732467;
                title = "Динозаврик";
                address = "пр. Буденного, 33, Москва, 105275";
                chechPermissions();
            }
        });

        Button openMapEst3Button = view.findViewById(R.id.openMapEst3Button);
        openMapEst3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = 55.730765;
                longitude = 37.771032;
                title = "Зверятам.ру";
                address = "ул. Зарайская, 31, Москва, 109428";
                chechPermissions();
            }
        });


        return view;
    }

    public void chechPermissions() {
        // Проверка и запрос разрешений
        int fineLocationPermissionStatus = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionStatus = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (fineLocationPermissionStatus == PackageManager.PERMISSION_GRANTED && coarseLocationPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionGranted = true;
            openMapActivity();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_CODE_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = true;
                openMapActivity();
            } else {
                Toast.makeText(getContext(), "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openMapActivity() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("title", title);
        intent.putExtra("address", address);
        startActivity(intent);
    }
}