package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.UpdatedRepairRequestResponse
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForOneCString

interface RepairRequestsApi {

    @GET("repair_requests")
    suspend fun getRepairRequestsByEngineerNameAndEngineerNameClarifying(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("engineer_name") engineerName: String,
        @Query("engineer_name_clarifying") engineerNameClarifying: String
    ): List<RepairRequest>?

    @GET("repair_requests")
    suspend fun getRepairRequestsByEngineerNameEngineerNameClarifyingAndDate(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("engineer_name") engineerName: String,
        @Query("engineer_name_clarifying") engineerNameClarifying: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): List<RepairRequest>?

    @GET("repair_requests")
    suspend fun getRepairRequestsByEngineerNameEngineerNameClarifyingAndStatus(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("engineer_name") engineerName: String,
        @Query("engineer_name_clarifying") engineerNameClarifying: String,
        @Query("status") status: String
    ): List<RepairRequest>?

    @GET("repair_requests")
    suspend fun getRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatus(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("engineer_name") engineerName: String,
        @Query("engineer_name_clarifying") engineerNameClarifying: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
        @Query("status") status: String
    ): List<RepairRequest>?

    @PUT("repair_requests")
    suspend fun putRepairRequestsByDocumentNumberWIthStatus(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString(),
        @Query("number") number: String,
        @Query("status") status: String
    ): UpdatedRepairRequestResponse
}