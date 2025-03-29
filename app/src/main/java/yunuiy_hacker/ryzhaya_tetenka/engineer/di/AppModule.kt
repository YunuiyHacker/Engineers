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
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.ApplicationStatusesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.MastersApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.EmployeesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses.ApplicationStatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses.GetAllApplicationStatusesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses.InsertApplicationStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.epmloyees.EmployeesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.epmloyees.GetAllEmployeesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.DeleteMasterByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.GetAllMastersOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.GetMasterById
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.InsertMasterOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.MastersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.UpdateMasterOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.GetRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.GetRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.GetRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.GetRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.PutRepairRequestsByDocumentNumberWithStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.RepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.DeleteUserOperatorByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.GetAllUsersOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.GetUserByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.GetUserByLoginAndPasswordOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.InsertUserOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.UpdateUserOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.UsersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.CONNECTION_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.KOTLIN_API_URL
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.ONE_C_API_URL
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.READ_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.WRITE_TIMOUT
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
            .writeTimeout(WRITE_TIMOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicationStatusesApi(okHttpClient: OkHttpClient): ApplicationStatusesApi {
        return Retrofit.Builder().baseUrl(KOTLIN_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApplicationStatusesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersApi(okHttpClient: OkHttpClient): UsersApi {
        return Retrofit.Builder().baseUrl(KOTLIN_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMastersApi(okHttpClient: OkHttpClient): MastersApi {
        return Retrofit.Builder().baseUrl(KOTLIN_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MastersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepairRequestsApi(okHttpClient: OkHttpClient): RepairRequestsApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RepairRequestsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideEmployeesApi(okHttpClient: OkHttpClient): EmployeesApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(EmployeesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersUseCase(usersApi: UsersApi): UsersUseCase {
        return UsersUseCase(
            getAllUsersOperator = GetAllUsersOperator(usersApi),
            getUserByIdOperator = GetUserByIdOperator(usersApi),
            getUserByLoginAndPasswordOperator = GetUserByLoginAndPasswordOperator(usersApi),
            insertUserOperator = InsertUserOperator(usersApi),
            updateUserOperator = UpdateUserOperator(usersApi),
            deleteUserOperatorByIdOperator = DeleteUserOperatorByIdOperator(usersApi)
        )
    }

    @Provides
    @Singleton
    fun provideApplicationStatusesUseCase(applicationStatusesApi: ApplicationStatusesApi): ApplicationStatusesUseCase {
        return ApplicationStatusesUseCase(
            getAllApplicationStatusesOperator = GetAllApplicationStatusesOperator(
                applicationStatusesApi
            ),
            insertApplicationStatusOperator = InsertApplicationStatusOperator(applicationStatusesApi)
        )
    }

    @Provides
    @Singleton
    fun provideMastersUseCase(mastersApi: MastersApi): MastersUseCase {
        return MastersUseCase(
            getAllMastersOperator = GetAllMastersOperator(mastersApi),
            getMasterById = GetMasterById(mastersApi),
            insertMasterOperator = InsertMasterOperator(mastersApi),
            updateMasterOperator = UpdateMasterOperator(mastersApi),
            deleteMasterByIdOperator = DeleteMasterByIdOperator(mastersApi)
        )
    }

    @Provides
    @Singleton
    fun provideEmployeesUseCase(employeesApi: EmployeesApi): EmployeesUseCase {
        return EmployeesUseCase(getAllEmployeesOperator = GetAllEmployeesOperator(employeesApi))
    }

    @Provides
    @Singleton
    fun provideRepairRequestsUseCase(repairRequestsApi: RepairRequestsApi): RepairRequestsUseCase {
        return RepairRequestsUseCase(
            getRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator = GetRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                repairRequestsApi
            ),
            getRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator = GetRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(
                repairRequestsApi
            ),
            getRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator = GetRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(
                repairRequestsApi
            ),
            getRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator = GetRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(
                repairRequestsApi
            ),
            putRepairRequestsByDocumentNumberWithStatus = PutRepairRequestsByDocumentNumberWithStatus(
                repairRequestsApi
            )
        )
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}