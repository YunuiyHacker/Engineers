package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c

import retrofit2.http.GET
import retrofit2.http.Header
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getRetrofitBasicAuthenticationForOneCString

interface EmployeesApi {

    @GET("employees")
    suspend fun getEmployees(
        @Header("Authorization") auth: String = getRetrofitBasicAuthenticationForOneCString()
    ): List<Employee>?
}