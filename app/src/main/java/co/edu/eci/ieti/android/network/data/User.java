package co.edu.eci.ieti.android.network.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    private String password;

    private String firstName;

    private String lastName;





    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }



    @Override
    public String toString()
    {
        return "User{email='" + email + '\'' + ", password='" + password + '\'' + ", firstName='" + firstName + '\''
                + '}';
    }
}
