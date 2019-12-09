package co.edu.eci.ieti.android.ui.utils;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import co.edu.eci.ieti.R;

/**
 * @author Santiago Carrillo
 */
public class FetchAddressIntentService
        extends IntentService
{
    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    private static final String TAG = FetchAddressIntentService.class.getSimpleName();

    protected ResultReceiver resultReceiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FetchAddressIntentService()
    {
        super( "Fetch_address_intent_service" );
    }


    @Override
    protected void onHandleIntent( Intent intent )
    {
        String errorMessage = "";

        resultReceiver = intent.getParcelableExtra( RECEIVER );

        // Check if receiver was properly registered.
        if ( resultReceiver == null )
        {
            Log.wtf( TAG, "No receiver received. There is nowhere to send the results." );
            return;
        }

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra( LOCATION_DATA_EXTRA );

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if ( location == null )
        {
            errorMessage = getString( R.string.no_location_data_provided );
            Log.wtf( TAG, errorMessage );
            deliverResultToReceiver( FAILURE_RESULT, errorMessage );
            return;
        }

        try
        {
            Geocoder geocoder = new Geocoder( this, Locale.getDefault() );
            List<Address> addresses = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(), 1 );

            if ( addresses == null || addresses.size() == 0 )
            {
                if ( errorMessage.isEmpty() )
                {
                    errorMessage = getString( R.string.no_address_found );
                    Log.e( TAG, errorMessage );
                }
                deliverResultToReceiver( FAILURE_RESULT, errorMessage );
            }
            else
            {
                String address = addresses.get( 0 ).getAddressLine( 0 );
                Log.i( TAG, getString( R.string.address_found ) + address );
                deliverResultToReceiver( SUCCESS_RESULT, address );
            }
        }
        catch ( IOException ioException )
        {
            errorMessage = getString( R.string.service_not_available );
            Log.e( TAG, errorMessage, ioException );
        }
        catch ( IllegalArgumentException illegalArgumentException )
        {
            errorMessage = getString( R.string.invalid_lat_long_used );
            Log.e( TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException );
        }
    }

    private void deliverResultToReceiver( int resultCode, String message )
    {
        Bundle bundle = new Bundle();
        bundle.putString( RESULT_DATA_KEY, message );
        resultReceiver.send( resultCode, bundle );
    }

}