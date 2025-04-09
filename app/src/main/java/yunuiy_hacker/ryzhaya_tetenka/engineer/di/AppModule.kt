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
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.KotlinRepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.MastersApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.EmployeesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.OneCRepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.application_statuses.ApplicationStatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.application_statuses.GetAllApplicationStatusesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.application_statuses.InsertApplicationStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.epmloyees.EmployeesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.epmloyees.GetAllEmployeesOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.DeleteMasterByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.GetAllMastersOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.GetMasterById
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.InsertMasterOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.MastersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.UpdateMasterOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.DeleteKotlinRepairRequestByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.DeleteKotlinRepairRequestByNumberOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.GetAllKotlinRepairRequestsOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.GetKotlinRepairRequestByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.GetKotlinRepairRequestByNumberOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.InsertKotlinRepairRequestOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.KotlinRepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests.UpdateKotlinRepairRequestOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.PutOneCRepairRequestsByDocumentNumberWithStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.OneCRepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.DeleteUserOperatorByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.GetAllUsersOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.GetUserByIdOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.GetUserByLoginAndPasswordOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.InsertUserOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.UpdateUserOperator
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.UsersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.CONNECTION_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.KOTLIN_API_URL
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.ONE_C_API_URL
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.READ_TIMEOUT
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.WRITE_TIMEOUT
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
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
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
    fun provideRepairRequestsApi(okHttpClient: OkHttpClient): OneCRepairRequestsApi {
        return Retrofit.Builder().baseUrl(ONE_C_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(OneCRepairRequestsApi::class.java)
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
    fun provideOnCRepairRequestsUseCase(oneCRepairRequestsApi: OneCRepairRequestsApi): OneCRepairRequestsUseCase {
        return OneCRepairRequestsUseCase(
            getOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator = GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                oneCRepairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(
                oneCRepairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(
                oneCRepairRequestsApi
            ),
            getOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator = GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(
                oneCRepairRequestsApi
            ),
            putOneCRepairRequestsByDocumentNumberWithStatus = PutOneCRepairRequestsByDocumentNumberWithStatus(
                oneCRepairRequestsApi
            )
        )
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideKotlinRepairRequestsApi(okHttpClient: OkHttpClient): KotlinRepairRequestsApi {
        return Retrofit.Builder().baseUrl(KOTLIN_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(KotlinRepairRequestsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKotlinRepairRequestsUseCase(kotlinRepairRequestsApi: KotlinRepairRequestsApi): KotlinRepairRequestsUseCase {
        return KotlinRepairRequestsUseCase(
            getAllKotlinRepairRequestsOperator = GetAllKotlinRepairRequestsOperator(
                kotlinRepairRequestsApi
            ),
            getKotlinRepairRequestByIdOperator = GetKotlinRepairRequestByIdOperator(
                kotlinRepairRequestsApi
            ),
            getKotlinRepairRequestByNumberOperator = GetKotlinRepairRequestByNumberOperator(
                kotlinRepairRequestsApi
            ),
            insertKotlinRepairRequestOperator = InsertKotlinRepairRequestOperator(
                kotlinRepairRequestsApi
            ),
            updateKotlinRepairRequestOperator = UpdateKotlinRepairRequestOperator(
                kotlinRepairRequestsApi
            ),
            deleteKotlinRepairRequestByIdOperator = DeleteKotlinRepairRequestByIdOperator(
                kotlinRepairRequestsApi
            ),
            deleteKotlinRepairRequestByNumberOperator = DeleteKotlinRepairRequestByNumberOperator(
                kotlinRepairRequestsApi
            )
        )
    }
}