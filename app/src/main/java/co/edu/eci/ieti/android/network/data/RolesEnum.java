package co.edu.eci.ieti.android.network.data;

public enum RolesEnum
{
    ADMIN( "admin" ),
    USER( "user" );

    final String role;

    RolesEnum( String role )
    {
        this.role = role;
    }
}
