package co.edu.eci.ieti;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        startActivity( new Intent( this, MainActivity.class ) );
    }
}
