package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForKotlinString

interface KotlinRepairRequestsApi {

    @GET("/repair_requests")
    suspend fun getAllKotlinRepairRequests(@Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString()): List<RepairRequest>

    @GET("/repair_requests/{repairRequestId")
    suspend fun getKotlinRepairRequestById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("repairRequestId") repairRequestId: Int
    ): RepairRequest?

    @GET("/repair_requests/{repairRequestNumber")
    suspend fun getKotlinRepairRequestByNumber(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("repairRequestNumber") repairRequestNumber: String
    ): RepairRequest?

    @POST("/repair_requests")
    suspend fun postRepairRequest(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body repairRequest: RepairRequest
    ): RepairRequest?

    @PUT("/repair_requests")
    suspend fun putRepairRequest(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Body repairRequest: RepairRequest
    ): RepairRequest?

    @DELETE("/repair_requests/{repairRequestId}")
    suspend fun deleteRepairRequestById(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("repairRequestId") repairRequestId: Int
    )

    @DELETE("/repair_requests/{repairRequestNumber}")
    suspend fun deleteRepairRequestByNumber(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForKotlinString(),
        @Path("repairRequestNumber") repairRequestNumber: String
    )
}