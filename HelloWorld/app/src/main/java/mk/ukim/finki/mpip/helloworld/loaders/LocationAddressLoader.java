package mk.ukim.finki.mpip.helloworld.loaders;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.Country;

/**
 * Created by ristes on 29.11.17.
 */

public class LocationAddressLoader extends AsyncTaskLoader<LatLng> {

    public static final String TAG = "LocationAddressLoader";
    private String countryName;
    private Handler handler;

    public LocationAddressLoader(Context context, String countryName) {
        super(context);
        this.countryName = countryName;
        handler = new Handler();
    }

    @Override
    public LatLng loadInBackground() {
        LatLng location = null;
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(getContext());
            try {
                List<Address> addresses = geocoder.getFromLocationName(countryName, 1);
                Address address = addresses.get(0);
                location = new LatLng(address.getLatitude(), address.getLongitude());
                handler.post(() -> {
                    String msg = "It's ok for " + countryName;
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> {
                    String msg = "Can't find location for " + countryName;
                    Log.d(TAG, msg);
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                });

            }

        }
        return location;
    }
}
