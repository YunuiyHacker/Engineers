package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForKotlinString

interface MastersApi {

    @GET("/masters")
    suspend fun getAllMasters(@Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString()): List<Master>?

    @GET("/masters/{masterId}")
    suspend fun getMasterById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("masterId") masterId: Int
    ): Master?

    @POST("/masters")
    suspend fun postMaster(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body master: Master
    ): Master?

    @PUT("/masters")
    suspend fun putMaster(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body master: Master
    ): Master?

    @DELETE("/masters/{masterId}")
    suspend fun deleteMasterById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("masterId") masterId: Int
    )
}