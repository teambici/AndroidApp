package co.edu.eci.ieti.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.network.data.LoginWrapper;
import co.edu.eci.ieti.android.network.data.Token;
import co.edu.eci.ieti.android.storage.Storage;
import co.edu.eci.ieti.android.ui.utils.StringUtils;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity
    extends AppCompatActivity
{

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    private final RetrofitNetwork retrofitNetwork = new RetrofitNetwork();

    private Storage storage;

    private EditText email;

    private EditText password;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        storage = new Storage( this );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
    }


    public void onLoginClicked( final View view )
    {
        final LoginWrapper loginWrapper = validInputFields();
        if ( loginWrapper != null )
        {
            view.setEnabled( false );
            executorService.execute( new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Call<Token> call = retrofitNetwork.getAuthService().login( loginWrapper );
                        Response<Token> response = call.execute();
                        if ( response.isSuccessful() )
                        {
                            Token token = response.body();
                            storage.saveToken( token );
                            startActivity( new Intent( LoginActivity.this, MainActivity.class ) );
                            finish();
                        }
                        else
                        {
                            showErrorMessage( view );
                        }

                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                        showErrorMessage( view );
                    }
                }
            } );
        }
    }

    private void showErrorMessage( final View view )
    {
        runOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                view.setEnabled( true );
                Snackbar.make( view, getString( R.string.server_error_message ), Snackbar.LENGTH_LONG );
            }
        } );

    }

    private LoginWrapper validInputFields()
    {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        if ( !StringUtils.isValidEmail( email ) )
        {
            this.email.setError( getString( R.string.invalid_email ) );
            return null;
        }
        else
        {
            this.email.setError( null );
            if ( password.isEmpty() )
            {
                this.password.setError( getString( R.string.please_enter_a_password ) );
                return null;
            }
            else
            {
                this.password.setError( null );
            }
        }

        return new LoginWrapper( email, password );
    }
}
