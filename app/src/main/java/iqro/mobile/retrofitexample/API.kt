package iqro.mobile.retrofitexample

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("/todos")
    suspend fun getTodoList():Response<List<Todo>>

    @GET("/api/users?")
    suspend fun getAllUserByPage(@Query("page")  page:Int,@Query("per_page")perPage:Int):Response<UserListResponse>

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") userId:Int):Response<UserResponse>

    @POST("/api/users")
    suspend fun cresteUser(@Body userCreate: UserCreate):Response<UserCreate>

    @DELETE("/api/users/{id}")
    suspend fun delateUser(@Path("id") userId: Int):Response<Unit>
@DELETE
    suspend fun  delateAllUsers()



}