package ru.mirea.sosnovskayave.yandexdriver;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Color;

import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;



import ru.mirea.sosnovskayave.yandexdriver.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener, UserLocationObjectListener {
    private final MapObjectTapListener mapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            Log.d("User Location", "Кнопка нажата");
            Toast.makeText(MainActivity.this, "McDonald's, «Макдо́налдс» — американская корпорация, работающая в сфере общественного питания, до 2010 года крупнейшая в мире сеть ресторанов быстрого питания, работающая по системе франчайзинга", Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    private ActivityMainBinding binding;
    boolean isWork;
    private MapView mapView;
    private static final int REQUEST_CODE_BASIC_LOCATION_PERMISSIONS = 101;
    private static final int REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION = 102;
    private final String MAPKIT_API_KEY = "47a2580c-d5f0-4d42-b424-a2a925f4fca5";
    private MapObjectCollection mapObjects;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};
    UserLocationLayer userLocationLayer;
    PlacemarkMapObject marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapView = binding.mapview;
        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f,
                        0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjects = mapView.getMap().getMapObjects().addCollection();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkAndRequestLocationPermissions();
        }

        submitRequest();
    }

    private void checkAndRequestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_CODE_BASIC_LOCATION_PERMISSIONS);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION);
        } else {
            isWork = true;
            loadUserLocationLayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_BASIC_LOCATION_PERMISSIONS && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // После получения основных разрешений проверяем фоновое
                checkAndRequestLocationPermissions();
            } else {
                Toast.makeText(this, "Basic location permissions are necessary.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
                loadUserLocationLayer();
                Toast.makeText(this, "Все разрешения были получены!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Background location permission is necessary.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    private void loadUserLocationLayer(){
        MapKit mapKit = MapKitFactory.getInstance();
        mapKit.resetLocationManagerToDefault();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView,
                                @NonNull ObjectEvent objectEvent) {
        Point userLocation = userLocationView.getPin().getGeometry();
        Log.d("User Location", "Latitude: " + userLocation.getLatitude() + ", Longitude: " + userLocation.getLongitude());
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.83)));
        // При определении направления движения устанавливается следующая иконка
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, android.R.drawable.arrow_up_float));

        // При получении координат местоположения устанавливается следующая иконка
        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.ic_launcher_foreground),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
                        .setScale(0.5f)
        );

    }

    private void submitRequest() {
        DrivingOptions drivingOptions = new DrivingOptions();
        VehicleOptions vehicleOptions = new VehicleOptions();
        // Кол-во альтернативных путей
        drivingOptions.setRoutesCount(4);
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        // Устанавка точек маршрута
        final Point ROUTE_START_LOCATION = new Point(37.453499,-122.181793);
        final Point ROUTE_END_LOCATION = new Point(37.419401, -122.093650);

        requestPoints.add(new RequestPoint(ROUTE_START_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        // Отправка запроса на сервер
        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions,
                vehicleOptions, this);

    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        int color;
        for (int i = 0; i < list.size(); i++) {
            // настроиваем цвета для каждого маршрута
            color = colors[i];
            // добавляем маршрут на карту
            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
        }

        PlacemarkMapObject marker = mapObjects.addPlacemark(new Point(37.419401, -122.093650),
                ImageProvider.fromResource(this, android.R.drawable.btn_star_big_on));
//                ImageProvider.fromResource(this, R.drawable.star));

        marker.addTapListener(mapObjectTapListener);

        marker.setVisible(true);
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        Log.e(TAG, "Ошибка: " + error.toString());
    }
}



//public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener, UserLocationObjectListener {
//    private ActivityMainBinding binding;
//    boolean isWork;
//    private MapView mapView;
//    private static final int REQUEST_CODE_BASIC_LOCATION_PERMISSIONS = 101;
//    private static final int REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION = 102;
//    private final String MAPKIT_API_KEY = "47a2580c-d5f0-4d42-b424-a2a925f4fca5";
//    private MapObjectCollection mapObjects;
//    private DrivingRouter drivingRouter;
//    private DrivingSession drivingSession;
//    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};
//    UserLocationLayer userLocationLayer;
//    PlacemarkMapObject marker;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//
//        MapKitFactory.setApiKey(MAPKIT_API_KEY);
//        MapKitFactory.initialize(this);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        mapView = binding.mapview;
//        mapView.getMap().move(
//                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f,
//                        0.0f),
//                new Animation(Animation.Type.SMOOTH, 0),
//                null);
//
//        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
//        mapObjects = mapView.getMap().getMapObjects().addCollection();
//
//        PlacemarkMapObject marker = mapView.getMap().getMapObjects().addPlacemark(new
//                Point(37.419401, -122.093650), ImageProvider.fromResource(this, android.R.drawable.btn_star_big_on));
//        marker.addTapListener(new MapObjectTapListener() {
//            @Override
//            public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point
//                    point) {
//                Log.d("User Location", "Кнопка нажата");
//                Toast.makeText(MainActivity.this, "Marker click", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        marker.setVisible(true);
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            checkAndRequestLocationPermissions();
//        }
//
//        submitRequest();
//    }
//
//
//    private void checkAndRequestLocationPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_CODE_BASIC_LOCATION_PERMISSIONS);
//        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION);
//        } else {
//            isWork = true;
//            loadUserLocationLayer();
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_BASIC_LOCATION_PERMISSIONS && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // После получения основных разрешений проверяем фоновое
//                checkAndRequestLocationPermissions();
//            } else {
//                Toast.makeText(this, "Basic location permissions are necessary.", Toast.LENGTH_LONG).show();
//            }
//        } else if (requestCode == REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                isWork = true;
//                loadUserLocationLayer();
//                Toast.makeText(this, "Все разрешения были получены!", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "Background location permission is necessary.", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // Вызов onStop нужно передавать инстансам MapView и MapKit.
//        mapView.onStop();
//        MapKitFactory.getInstance().onStop();
//    }
//    @Override
//    protected void onStart() {
//        // Вызов onStart нужно передавать инстансам MapView и MapKit.
//        super.onStart();
//        mapView.onStart();
//        MapKitFactory.getInstance().onStart();
//    }
//
//    private void loadUserLocationLayer(){
//        MapKit mapKit = MapKitFactory.getInstance();
//        mapKit.resetLocationManagerToDefault();
//        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
//        userLocationLayer.setVisible(true);
//        userLocationLayer.setHeadingEnabled(true);
//        userLocationLayer.setObjectListener(this);
//    }
//    @Override
//    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
//    }
//    @Override
//    public void onObjectUpdated(@NonNull UserLocationView userLocationView,
//                                @NonNull ObjectEvent objectEvent) {
//        Point userLocation = userLocationView.getPin().getGeometry();
//        Log.d("User Location", "Latitude: " + userLocation.getLatitude() + ", Longitude: " + userLocation.getLongitude());
//    }
//    @Override
//    public void onObjectAdded(UserLocationView userLocationView) {
//        userLocationLayer.setAnchor(
//                new PointF((float) (mapView.getWidth() * 0.5),
//                        (float) (mapView.getHeight() * 0.5)),
//                new PointF((float) (mapView.getWidth() * 0.5),
//                        (float) (mapView.getHeight() * 0.83)));
//        // При определении направления движения устанавливается следующая иконка
//        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
//                this, android.R.drawable.arrow_up_float));
//
//        // При получении координат местоположения устанавливается следующая иконка
//        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
//        pinIcon.setIcon(
//                "pin",
//                ImageProvider.fromResource(this, R.drawable.ic_launcher_foreground),
//                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
//                        .setRotationType(RotationType.ROTATE)
//                        .setZIndex(1f)
//                        .setScale(0.5f)
//        );
//
//    }
//    private void submitRequest() {
//        DrivingOptions drivingOptions = new DrivingOptions();
//        VehicleOptions vehicleOptions = new VehicleOptions();
//        // Кол-во альтернативных путей
//        drivingOptions.setRoutesCount(4);
//        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
//        // Устанавка точек маршрута
//        final Point ROUTE_START_LOCATION = new Point(37.453499,-122.181793);
//        final Point ROUTE_END_LOCATION = new Point(37.419401, -122.093650);
//
//        requestPoints.add(new RequestPoint(ROUTE_START_LOCATION,
//                RequestPointType.WAYPOINT,
//                null));
//        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION,
//                RequestPointType.WAYPOINT,
//                null));
//        // Отправка запроса на сервер
//        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions,
//                vehicleOptions, this);
//
//    }
//
//    @Override
//    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
//        int color;
//        for (int i = 0; i < list.size(); i++) {
//            // настроиваем цвета для каждого маршрута
//            color = colors[i];
//            // добавляем маршрут на карту
//            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
//        }
//    }
//
//    @Override
//    public void onDrivingRoutesError(@NonNull Error error) {
//        Log.e(TAG, "Ошибка: " + error.toString());
//    }
//}
