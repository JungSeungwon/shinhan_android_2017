package com.shinhan.googlemapexam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity {
    SupportMapFragment mapFragment;
    GoogleMap map;
    ///////////////////////////////////////////////////////
    class MyMarker{
        String name; LatLng latLng;

        MyMarker(String name,LatLng latLng){
            this.name = name; this.latLng = latLng;
        }
    }
    MyMarker[] markers = {
            new MyMarker("신한은행 일산금융센터", new LatLng(37.662448, 126.7696257)),
            new MyMarker("서강대학교", new LatLng(37.5513365,126.9407529)),
            new MyMarker("분당 SweetHome", new LatLng(37.3706312,127.1385393)),
            new MyMarker("한강 세이프빌", new LatLng(37.6637969,126.7679595)),
            new MyMarker("뉴욕 브르쿨린 브리지", new LatLng(40.7127837,-74.0059413)),
            new MyMarker("캐나다 나이아가라폭포", new LatLng(43.0828162, -79.0741629)),
            new MyMarker("노스캐롤라이나 주립대", new LatLng(35.7846633, -78.6820946)),
            new MyMarker("파리 에펠탑", new LatLng(48.8583701, 2.2944813)),
            new MyMarker("이비자 섬", new LatLng(39.0200099 ,1.4821482)),
            new MyMarker("일본 오사카성", new LatLng(34.6873153, 135.5262013)),

    };
    int currentPos = 0;
    public void onTourButtonClicked(View view){
        if (currentPos >= markers.length-1) {
            currentPos = 0;
        }
        else
            currentPos++;
        if(map!=null){
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(markers[currentPos].latLng,15));

        }
    }
    /////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap; // 비동기방식으로 구글지도 객체 얻기
                PolylineOptions rectOptions = new PolylineOptions();
                rectOptions.color(Color.RED);
                //
                for(int i=0; i<markers.length; i++){
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(markers[i].latLng);
                    marker.title(markers[i].name);
                    map.addMarker(marker);
                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                        public boolean onMarkerClick(Marker marker){
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15));
                            map.animateCamera(CameraUpdateFactory.zoomTo(18));
                            return false;
                        }

                    });
                    rectOptions.add(markers[i].latLng);
                }
                Polyline polyline = map.addPolyline(rectOptions);
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "GPS 연동 권한 필요합니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }
    public void onWorldMapButtonClicked(View view){
        if(map!=null){
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.moveCamera(CameraUpdateFactory.zoomTo(1));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"GPS 권한 승인!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"GPS 권한 거부!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) { //위치가 변경되면 호출되는것
            double latitude = location.getLatitude(); //위도
            double longitude = location.getLongitude(); // 경도
            TextView textView = (TextView)findViewById(R.id.location);
            textView.setText("내 위치 : "+latitude+","+longitude);
            Toast.makeText(MainActivity.this, "위도:"+latitude+",경도:"+longitude,Toast.LENGTH_SHORT).show();

            LatLng curPoint = new LatLng(location.getLatitude(),location.getLongitude());
            if(map!=null)
            {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public void startLocationService(View view)
    {
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null) {
                TextView textView = (TextView) findViewById(R.id.location);
                textView.setText("내 위치 : " + location.getLatitude() + "," + location.getLongitude());
                Toast.makeText(this, "Last know location 위도 : " + location.getLatitude() + ",경도" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
            GPSListener gpsListener = new GPSListener();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0 ,gpsListener);
        }
    }
}
