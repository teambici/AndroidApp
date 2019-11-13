# Android Local Storage & List Views
* Consume secure REST API wih Retrofit
* Configure and integrate Android local storage using ROOM Persistence library.
* Create a List View using RecyclerView and CardLayout.


#### Part 1: Consume secure REST API using Bearer Token and Retrofit

1) Add an additional constructor to RetrofitNetwork with an interceptor so we can send our token on every request:

    ```java
     public RetrofitNetwork( final String token )
        {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor( new Interceptor()
            {
                @Override
                public okhttp3.Response intercept( Chain chain )
                    throws IOException
                {
                    Request original = chain.request();
    
                    Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                                                                                                           "Bearer "
                                                                                                               + token ).method(
                        original.method(), original.body() ).build();
                    return chain.proceed( request );
                }
            } );
            Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(
                    httpClient.build() ).build();
        }

    ```

2) Create *TaskService* interface with a GET method to retrieve the tasks list.

3) Instantiate the *TaskService* on the *RetrofitNetwork* class.

4) Call the method to retrieve the Tasks list from the API and verify it works.

#### Part 2: Configure Local Storage using Room Library

1) Do the android-persistence codelab until Step 2 - Define Entity Relationships:
https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..index#0

2) Create the structure on your project to have a local database to save the *Task* and *User* models.

3) Connect your *RetrofitNetwork* object with the Database (every time you retrieve the Tasks list you should store the data on the Room Database for offline support).  


#### Part 3: Create a RecyclerView and Adapter to display the Tasks list

1) Modify the *content_main.xml* layout so it has a RecyclerView component:

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                                                       xmlns:app="http://schemas.android.com/apk/res-auto"
                                                       xmlns:tools="http://schemas.android.com/tools"
                                                       android:layout_width="match_parent"
                                                       android:layout_height="match_parent"
                                                       app:layout_behavior="@string/appbar_scrolling_view_behavior"
                                                       tools:context=".android.ui.activity.MainActivity"
                                                       tools:showIn="@layout/app_bar_main">
    
      <androidx.recyclerview.widget.RecyclerView
          android:id="@+id/recyclerView"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>
      
    </androidx.constraintlayout.widget.ConstraintLayout>    
    ```
   
2)  Create a RecyclerView variable on the *MainActivity* and bind it with the xml view using *findViewById()* function.

3) Create a class called *TasksAdapter*

    ```java
    public class TasksAdapter
        extends RecyclerView.Adapter<TasksAdapter.ViewHolder>
    {
    
        List<Task> taskList = null;
    
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
        {
            return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.task_row, parent, false ) );
        }
    
        @Override
        public void onBindViewHolder( @NonNull ViewHolder holder, int position )
        {
            Task task = taskList.get( position );
            //TODO implement update view holder using the task values
        }
    
        @Override
        public int getItemCount()
        {
            return taskList != null ? taskList.size() : 0;
        }
     
         public void updateTasks(List<Task> tasks){
             this.taskList = tasks;
             notifyDataSetChanged();
         }
    
        class ViewHolder
            extends RecyclerView.ViewHolder
        {
            ViewHolder( @NonNull View itemView )
            {
                super( itemView );
            }
        }
    
    }
    
    ```  
    
4) Create the *task_row* layout (this is the layout that represents a Task).

5) Update your ViewHolder so it holds a reference to the Views used to represent a Task in the *task_row* layout.

5) Implement the *onBindViewHolder* function.

6) Create an instance of the *TasksAdapter* on the *MainActivity*       

7) Add the following function to the *MainActivity* and call it right after the RecyclerView binding is done:

    ```java
      private void configureRecyclerView()
        {
            recyclerView.setHasFixedSize( true );
            LinearLayoutManager layoutManager = new LinearLayoutManager( this );
            recyclerView.setAdapter( tasksAdapter );
        }
    
    ```
8) Make a request to retrieve the Tasks list. Once the data is retrieved then call *updateTasks* method of the of the *TasksAdapter* instance.     
    **Note you should call the *updateTasks* method from the UI thread using the method *runOnUiThread* from the *MainActivity*.
    
9) Create a new class that extents the ViewModel class: TasksViewModel. Finally modify your MainActivity so all the business logic to make the network request happens in the TaskViewModel class.
https://developer.android.com/topic/libraries/architecture/viewmodel

10) Run the application and verify that the Tasks list is loaded correctly.    

**Bonus**
11) Use a LiveData to connect your UI with the ViewModel:
https://developer.android.com/topic/libraries/architecture/livedata


