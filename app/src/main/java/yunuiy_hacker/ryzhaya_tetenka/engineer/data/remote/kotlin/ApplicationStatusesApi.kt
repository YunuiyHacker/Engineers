package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.ApplicationStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForKotlinString

interface ApplicationStatusesApi {

    @GET("/application_statuses")
    suspend fun getAllApplicationStatuses(@Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString()): List<ApplicationStatus>

    @POST("/application_statuses")
    suspend fun postApplicationStatus(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body applicationStatus: ApplicationStatus
    ): ApplicationStatus
}