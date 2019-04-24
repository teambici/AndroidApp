package co.edu.eci.ieti.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.eci.ieti.android.storage.Storage;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class LaunchActivity
    extends AppCompatActivity
{


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Storage storage = new Storage( this );
        if ( storage.containsToken() )
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
