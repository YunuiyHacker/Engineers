package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.OneCRepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.statuses.StatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getConnectivityManager
import java.util.Date
import javax.inject.Inject
import kotlin.collections.first

@HiltViewModel
class EngineerHomeViewModel @Inject constructor(
    val application: Application,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val oneCRepairRequestsUseCase: OneCRepairRequestsUseCase,
    private val statusesUseCase: StatusesUseCase,
    val gson: Gson
) : ViewModel() {
    val state by mutableStateOf(EngineerHomeState())

    private val connectivityManager =
        getConnectivityManager(application)

    fun onEvent(event: EngineerHomeEvent) {
        when (event) {
            is EngineerHomeEvent.LoadDataEvent -> loadData()

            is EngineerHomeEvent.ShowMessageDialogEvent -> {
                state.showMessageDialog = true
                state.message = event.message
            }

            is EngineerHomeEvent.HideMessageDialogEvent -> state.showMessageDialog = false

            is EngineerHomeEvent.ShowStatusPickerMenuEvent -> state.showStatusPickerMenu = true
            is EngineerHomeEvent.SelectStatusPickerMenuEvent -> {
                state.selectedStatus = event.status
                state.showStatusPickerMenu = false
                if (state.applyStatusFiltering)
                    loadDataWithFilter()
            }

            is EngineerHomeEvent.HideStatusPickerMenuEvent -> state.showStatusPickerMenu = false

            is EngineerHomeEvent.ShowStartDatePickerDialogEvent -> {
                state.selectStartDate = true
                state.showDatePickerDialog = true
            }

            is EngineerHomeEvent.ShowEndDatePickerDialogEvent -> {
                state.selectStartDate = false
                state.showDatePickerDialog = true
            }

            is EngineerHomeEvent.SelectStartDateEvent -> {
                state.startDateInMilliseconds = event.dateInMilliseconds
                state.showDatePickerDialog = false
                if (state.applyPeriodFiltering)
                    loadDataWithFilter()
            }

            is EngineerHomeEvent.SelectEndDateEvent -> {
                state.endDateInMilliseconds = event.dateInMilliseconds
                state.showDatePickerDialog = false
                if (state.applyPeriodFiltering)
                    loadDataWithFilter()
            }

            is EngineerHomeEvent.HideDatePickerDialogEvent -> state.showDatePickerDialog = false

            is EngineerHomeEvent.SearchRepairRequestsEvent -> {
                state.query = event.query
                loadDataWithFilter()
            }

            is EngineerHomeEvent.ToggleStatusApplyingEvent -> {
                state.applyStatusFiltering = !state.applyStatusFiltering
                loadDataWithFilter()
            }

            is EngineerHomeEvent.TogglePeriodApplyingEvent -> {
                state.applyPeriodFiltering = !state.applyPeriodFiltering
                loadDataWithFilter()
            }

            is EngineerHomeEvent.CheckInternetStateEvent -> checkInternetState()
        }
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    private fun loadData() {
        state.contentState.isLoading.value = true

        checkInternetState()

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.login = sharedPrefsHelper.login ?: ""
                    state.full_name = sharedPrefsHelper.fullName ?: ""

                    state.allRepairRequests =
                        oneCRepairRequestsUseCase.getOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                            state.login, state.full_name
                        )?.map { it.toDomain() }?.toMutableList() ?: mutableListOf()
                    state.repairRequests = state.allRepairRequests

                    state.statuses =
                        statusesUseCase.getAllStatusesOperator()
                            ?.map { it.toDomain() }?.toMutableList() ?: mutableListOf()

                    state.selectedStatus = state.statuses.first()

                    val currentDate = Date()
                    currentDate.hours = 0
                    currentDate.minutes = 0
                    currentDate.seconds = 0

                    state.startDateInMilliseconds = currentDate.time
                    state.endDateInMilliseconds = currentDate.time

                    state.contentState.hasConnectionToServers.value = true
                    state.contentState.isLoading.value = false
                } catch (e: retrofit2.HttpException) {
                    if (e.response()?.code() == 404 || e.response()?.code() == 502) {
                        state.contentState.hasConnectionToServers.value = false
                    }

                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
                    checkInternetState()

                    state.message = e.message.toString()
                    if (state.contentState.internetIsNotAvailable.value)
                        state.showMessageDialog = true
                    state.contentState.hasConnectionToServers.value = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadDataWithFilter() {
        state.contentState.isLoading.value = true

        checkInternetState()

        val startDate = Date(state.startDateInMilliseconds)
        val endDate = Date(state.endDateInMilliseconds)
        startDate.hours = 0
        startDate.minutes = 0
        startDate.seconds = 0
        endDate.hours = 23
        endDate.minutes = 59
        endDate.seconds = 59

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.repairRequests.clear()
                    state.allRepairRequests.clear()

                    if (state.applyStatusFiltering && !state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            oneCRepairRequestsUseCase.getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(
                                login = state.login,
                                full_name = state.full_name,
                                status = state.selectedStatus.title
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (!state.applyStatusFiltering && state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            oneCRepairRequestsUseCase.getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(
                                login = state.login,
                                full_name = state.full_name,
                                startDate = startDate,
                                endDate = endDate
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (state.applyStatusFiltering && state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            oneCRepairRequestsUseCase.getOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(
                                login = state.login,
                                full_name = state.full_name,
                                status = state.selectedStatus.title,
                                startDate = startDate,
                                endDate = endDate
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (!state.applyStatusFiltering && !state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            oneCRepairRequestsUseCase.getOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                                login = state.login,
                                full_name = state.full_name
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    state.repairRequests = state.allRepairRequests

                    if (state.query.isNotEmpty()) {
                        state.repairRequests = state.allRepairRequests.filter {
                            it.nomenclature.lowercase()
                                .startsWith(state.query.lowercase()) || it.nomenclature.lowercase()
                                .equals(state.query.lowercase()) || it.nomenclature.lowercase()
                                .endsWith(
                                    state.query.lowercase()
                                )
                        }.toMutableList()
                    } else {
                        state.repairRequests = state.allRepairRequests
                    }

                    state.contentState.hasConnectionToServers.value = true
                    state.contentState.isLoading.value = false
                } catch (e: HttpException) {
                    if (e.response()?.code() == 404 || e.response()?.code() == 502) {
                        state.contentState.hasConnectionToServers.value = false
                    }

                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
                    checkInternetState()

                    state.message = e.message.toString()
                    if (state.contentState.internetIsNotAvailable.value)
                        state.showMessageDialog = true
                    state.contentState.hasConnectionToServers.value = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }

    private fun checkInternetState() {
        state.contentState.internetIsNotAvailable.value =
            connectivityManager.getActiveNetworkInfo()?.isConnected ?: false
    }
}