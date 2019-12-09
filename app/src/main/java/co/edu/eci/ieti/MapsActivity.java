package co.edu.eci.ieti;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import co.edu.eci.ieti.android.storage.Storage;

import java.text.BreakIterator;

import co.edu.eci.ieti.android.ui.activity.LoginActivity;
import co.edu.eci.ieti.android.ui.utils.AddressResultListener;
import co.edu.eci.ieti.android.ui.utils.AddressResultReceiver;
import co.edu.eci.ieti.android.ui.utils.FetchAddressIntentService;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private final int ACCESS_LOCATION_PERMISSION_CODE = 44;
    static final int DATA_MAPS_REQUEST = 13;
    private Storage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showMyLocation();
    }
    public void showMyLocation()
    {
        if ( mMap != null )
        {
            String[] permissions = { android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION };
            if ( hasPermissions( this, permissions ) )
            {
                mMap.setMyLocationEnabled( true );

                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    addMarkerAndZoom( location, "My Location", 15 );
                                }
                            }
                        });
            }
            else
            {
                ActivityCompat.requestPermissions( this, permissions, ACCESS_LOCATION_PERMISSION_CODE );
            }
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions )
    {
        for ( String permission : permissions )
        {
            if ( ContextCompat.checkSelfPermission( context, permission ) == PackageManager.PERMISSION_DENIED )
            {
                return false;
            }
        }
        return true;
    }
    public void addMarkerAndZoom( Location location, String title, int zoom  )
    {
        LatLng myLocation = new LatLng( location.getLatitude(), location.getLongitude() );
        mMap.addMarker( new MarkerOptions().position( myLocation ).title( title ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( myLocation, zoom ) );
    }
    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions,
                                            @NonNull int[] grantResults )
    {
        for ( int grantResult : grantResults )
        {
            if ( grantResult == -1 )
            {
                return;
            }
        }
        switch ( requestCode )
        {
            case ACCESS_LOCATION_PERMISSION_CODE:
                showMyLocation();
                break;
            default:
                super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        }
    }


    public void onFindAddressClicked( View view )
    {
        startFetchAddressIntentService();
    }
    public void logOut(View view){
        storage = new Storage(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void goToSettings(View view){
        startActivity(new Intent(this,SettingsActivity.class));
        finish();
    }
    public void startFetchAddressIntentService()
    {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if ( location != null )
                        {
                            AddressResultReceiver addressResultReceiver = new AddressResultReceiver( new Handler() );
                            addressResultReceiver.setAddressResultListener( new AddressResultListener()
                            {
                                @Override
                                public void onAddressFound( final String address )
                                {
                                    runOnUiThread( new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {


                                            TextView tv=findViewById(R.id.addressResult);
                                            tv.setText(address);
                                            tv.setVisibility(View.INVISIBLE);

                                        }
                                    } );


                                }
                            } );
                            Intent intent = new Intent( MapsActivity.this, FetchAddressIntentService.class );
                            intent.putExtra( FetchAddressIntentService.RECEIVER, addressResultReceiver );
                            intent.putExtra( FetchAddressIntentService.LOCATION_DATA_EXTRA, location );
                            startService( intent );
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == DATA_MAPS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }
        }
    }


}