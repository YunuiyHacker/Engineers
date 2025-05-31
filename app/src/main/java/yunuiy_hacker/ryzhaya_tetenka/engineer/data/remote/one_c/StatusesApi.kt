package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c

import retrofit2.http.GET
import retrofit2.http.Header
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.Status
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForOneCString

interface StatusesApi {

    @GET("statuses")
    suspend fun getAllStatuses(@Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString()): List<Status>
}