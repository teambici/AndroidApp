package co.edu.eci.ieti.android.network.service;

import java.util.List;

import co.edu.eci.ieti.android.network.data.Task;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface TaskService {
    @GET( "/api/task" )
    Call<List<Task>> tasks(@Body Task loginWrapper );
}
