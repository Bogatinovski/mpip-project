package mk.ukim.finki.mpip.helloworld;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import mk.ukim.finki.mpip.helloworld.fragments.CountryDetailsText;
import mk.ukim.finki.mpip.helloworld.loaders.LocationAddressLoader;

public class CountryDetails extends AppCompatActivity
        implements OnMapReadyCallback, OnMarkerClickListener,
        LoaderManager.LoaderCallbacks<LatLng>,
        LocationListener {


    private GoogleMap googleMap;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60, 100, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 100, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        getSupportLoaderManager()
                .initLoader(12, null, this)
                .forceLoad();

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new LocationAddressLoader(this.getApplicationContext(), getCountryName());
    }

    @Override
    public void onLoadFinished(Loader loader, LatLng data) {
        String title = getCountryName();
        if (data == null) {
            data = new LatLng(-33.852, 151.211);
            title = "Sidney";
        }
        this.googleMap
                .addMarker(new MarkerOptions()
                        .position(data)
                        .title(title));
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(data, 6));

        this.googleMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    @Override
    public void onLocationChanged(Location location) {
        this.googleMap
                .addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),
                                location.getLongitude()))
                        .title("Current location from: " + location.getProvider())
                        .snippet("Accuracy: " + location.getAccuracy()));
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().equals(getCountryName())) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.country_details, CountryDetailsText.newInstance(getCountryName()))
                    .addToBackStack(null)
                    .commit();

//            fragmentManager.popBackStack();

//            fragmentManager.popBackStack("state",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return false;
    }


    public String getCountryName() {
        Intent intent = getIntent();
        return intent.getStringExtra("country");
    }

}
