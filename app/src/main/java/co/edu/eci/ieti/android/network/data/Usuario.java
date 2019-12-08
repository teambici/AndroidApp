package co.edu.eci.ieti.android.network.data;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class Usuario
{

    final String correo;

    final String contrasena;

    public Usuario(String email, String password )
    {
        this.correo = email;
        this.contrasena = password;
    }
}
