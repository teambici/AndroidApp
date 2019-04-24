package co.edu.eci.ieti.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.eci.ieti.R;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class LaunchActivity
    extends AppCompatActivity
{

    public static final String TOKEN_KEY = "TOKEN_KEY";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        SharedPreferences sharedPref =
            getSharedPreferences( getString( R.string.preference_file_key ), Context.MODE_PRIVATE );

        if ( sharedPref.contains( TOKEN_KEY ) )
        {
            startActivity( new Intent( this, MainActivity.class ) );
        }
        else
        {
            startActivity( new Intent( this, LoginActivity.class ) );
        }
        finish();
    }
}
