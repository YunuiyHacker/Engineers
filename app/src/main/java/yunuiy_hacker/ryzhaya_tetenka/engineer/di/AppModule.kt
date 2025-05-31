package yunuiy_hacker.ryzhaya_tetenka.engineer.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.AuthApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.EmployeesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.StatusesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.epmloyees.EmployeesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.epmloyees.GetAllEmployeesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.PutOneCRepairRequestsByDocumentNumberWithStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.OneCRepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.auth.AuthOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.auth.AuthUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.statuses.GetAllStatusesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.statuses.StatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.CONNECTION_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.ONE_C_API_URL
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.READ_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.WRITE_TIMEOUT
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefsHelper(@ApplicationContext context: Context) = SharedPrefsHelper(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS).build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepairRequestsApi(okHttpClient: OkHttpClient): RepairRequestsApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RepairRequestsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideEmployeesApi(okHttpClient: OkHttpClient): EmployeesApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(EmployeesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStatusesApi(okHttpClient: OkHttpClient): StatusesApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(StatusesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authApi: AuthApi): AuthUseCase {
        return AuthUseCase(authOperator = AuthOperator(authApi))
    }

    @Provides
    @Singleton
    fun provideEmployeesUseCase(employeesApi: EmployeesApi): EmployeesUseCase {
        return EmployeesUseCase(getAllEmployeesOperator = GetAllEmployeesOperator(employeesApi))
    }

    @Provides
    @Singleton
    fun provideOnCRepairRequestsUseCase(repairRequestsApi: RepairRequestsApi): OneCRepairRequestsUseCase {
        return OneCRepairRequestsUseCase(
            getOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator = GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                repairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(
                repairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(
                repairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(
                repairRequestsApi
            ),
            putOneCRepairRequestsByDocumentNumberWithStatus = PutOneCRepairRequestsByDocumentNumberWithStatus(
                repairRequestsApi
            )
        )
    }

    @Provides
    @Singleton
    fun provideStatusesUseCase(statusesApi: StatusesApi): StatusesUseCase {
        return StatusesUseCase(getAllStatusesOperator = GetAllStatusesOperator(statusesApi))
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}