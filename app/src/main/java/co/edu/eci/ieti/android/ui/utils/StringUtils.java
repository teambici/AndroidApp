package co.edu.eci.ieti.android.ui.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class StringUtils
{

    public static boolean isValidEmail( CharSequence target )
    {
        return ( !TextUtils.isEmpty( target ) && Patterns.EMAIL_ADDRESS.matcher( target ).matches() );
    }
}
