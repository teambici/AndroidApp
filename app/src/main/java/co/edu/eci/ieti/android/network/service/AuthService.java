package co.edu.eci.ieti.android.network.service;

import co.edu.eci.ieti.android.network.data.LoginWrapper;
import co.edu.eci.ieti.android.network.data.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public interface AuthService
{

    @POST( "auth" )
    Call<Token> login( @Body LoginWrapper loginWrapper );

}
