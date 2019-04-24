package co.edu.eci.ieti.android.network;

import co.edu.eci.ieti.android.network.service.AuthService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class RetrofitNetwork
{

    private AuthService authService;


    public RetrofitNetwork()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( "http:/10.0.2.2:8080/" ) //localhost for emulator
            .addConverterFactory( GsonConverterFactory.create() ).build();

        authService = retrofit.create( AuthService.class );
    }

    public AuthService getAuthService()
    {
        return authService;
    }
}
