package co.edu.eci.ieti.android.network.service;

import co.edu.eci.ieti.android.network.data.Usuario;
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

    @POST( "/User/login" )
    Call<Token> login( @Body Usuario usuario);

}
