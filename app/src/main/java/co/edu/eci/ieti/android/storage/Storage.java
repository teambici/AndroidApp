package co.edu.eci.ieti.android.storage;

import android.content.Context;
import android.content.SharedPreferences;
import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.network.data.Token;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class Storage
{

    private final String TOKEN_KEY = "TOKEN_KEY";

    private final SharedPreferences sharedPreferences;

    public Storage( Context context )
    {
        this.sharedPreferences =
            context.getSharedPreferences( context.getString( R.string.preference_file_key ), Context.MODE_PRIVATE );
    }

    public String getToken()
    {
        return sharedPreferences.getString( TOKEN_KEY, null );
    }

    public void saveToken( Token token )
    {
        sharedPreferences.edit().putString( TOKEN_KEY, token.getAccessToken() ).apply();
    }

    public boolean containsToken()
    {
        return sharedPreferences.contains( TOKEN_KEY );
    }

    public void clear()
    {
        sharedPreferences.edit().remove( TOKEN_KEY ).apply();
    }

}
