package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForKotlinString

interface UsersApi {

    @GET("/users")
    suspend fun getAllUsers(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString()
    ): List<User>?

    @GET("/users/{id}")
    suspend fun getUserById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("id") userId: Int
    ): User?

    @GET("/users/{login}/{password}")
    suspend fun getUserByLoginAndPassword(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("login") login: String,
        @Path("password") password: String
    ): User?

    @POST("/users")
    suspend fun postUser(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body user: User
    ): User?

    @PUT("/users")
    suspend fun putUser(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body user: User
    ): User?

    @DELETE("/users/{userId}")
    suspend fun deleteUserById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("userId") userId: Int
    )
}