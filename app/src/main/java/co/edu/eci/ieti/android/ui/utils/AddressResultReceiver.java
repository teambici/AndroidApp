package co.edu.eci.ieti.android.ui.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * @author Santiago Carrillo
 */
@SuppressLint( "ParcelCreator" )
public class AddressResultReceiver
        extends ResultReceiver
{

    private AddressResultListener addressResultListener;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler required by default constructor
     */
    public AddressResultReceiver( Handler handler )
    {
        super( handler );
    }

    public void setAddressResultListener( AddressResultListener addressResultListener )
    {
        this.addressResultListener = addressResultListener;
    }

    @Override
    protected void onReceiveResult( int resultCode, Bundle resultData )
    {
        // Display the address string
        // or an error message sent from the intent service.
        String addressOutput = resultData.getString( FetchAddressIntentService.RESULT_DATA_KEY );

        if ( resultCode == FetchAddressIntentService.SUCCESS_RESULT && addressResultListener != null )
        {
            addressResultListener.onAddressFound( addressOutput );
        }

    }

}