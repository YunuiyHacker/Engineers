package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForOneCString

interface AuthApi {

    @POST("auth")
    suspend fun auth(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("user") user: String,
        @Query("password") password: String
    ): User
}