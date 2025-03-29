package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
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
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses.ApplicationStatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.RepairRequestsUseCase
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val application: Application,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val repairRequestsUseCase: RepairRequestsUseCase,
    private val applicationStatusesUseCase: ApplicationStatusesUseCase,
    val gson: Gson
) : ViewModel() {
    val state by mutableStateOf(HomeState())


    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadDataEvent -> loadData()

            is HomeEvent.ShowMessageEvent -> {
                state.showMessageDialog = true
                state.message = event.message
            }

            is HomeEvent.HideMessageEvent -> state.showMessageDialog = false

            is HomeEvent.ShowStatusPickerMenuEvent -> state.showStatusPickerMenu = true
            is HomeEvent.SelectStatusPickerMenuEvent -> {
                state.selectedStatus = event.status
                state.showStatusPickerMenu = false
                if (state.applyStatusFiltering)
                    loadDataWithFilter()
            }

            is HomeEvent.HideStatusPickerMenuEvent -> state.showStatusPickerMenu = false

            is HomeEvent.ShowStartDatePickerDialogEvent -> {
                state.selectStartDate = true
                state.showDatePickerDialog = true
            }

            is HomeEvent.ShowEndDatePickerDialogEvent -> {
                state.selectStartDate = false
                state.showDatePickerDialog = true
            }

            is HomeEvent.SelectStartDateEvent -> {
                state.startDateInMilliseconds = event.dateInMilliseconds
                state.showDatePickerDialog = false
                if (state.applyPeriodFiltering)
                    loadDataWithFilter()
            }

            is HomeEvent.SelectEndDateEvent -> {
                state.endDateInMilliseconds = event.dateInMilliseconds
                state.showDatePickerDialog = false
                if (state.applyPeriodFiltering)
                    loadDataWithFilter()
            }

            is HomeEvent.HideDatePickerDialogEvent -> state.showDatePickerDialog = false

            is HomeEvent.SearchRepairRequestsEvent -> {
                state.query = event.query
                loadDataWithFilter()
            }

            is HomeEvent.ToggleStatusApplyingEvent -> {
                state.applyStatusFiltering = !state.applyStatusFiltering
                loadDataWithFilter()
            }

            is HomeEvent.TogglePeriodApplyingEvent -> {
                state.applyPeriodFiltering = !state.applyPeriodFiltering
                loadDataWithFilter()
            }

            is HomeEvent.CheckInternetStateEvent -> checkInternetState()
        }
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    private fun loadData() {
        state.contentState.isLoading.value = true

        checkInternetState()

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.masterTitle = sharedPrefsHelper.title ?: ""
                    state.masterTitleClarifying = sharedPrefsHelper.titleClarifying ?: ""

                    state.allRepairRequests =
                        repairRequestsUseCase.getRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                            state.masterTitle, state.masterTitleClarifying
                        )?.map { it.toDomain() }?.toMutableList() ?: mutableListOf()
                    state.repairRequests = state.allRepairRequests

                    state.applicationStatuses =
                        applicationStatusesUseCase.getAllApplicationStatusesOperator()
                            ?.map { it.toDomain() }?.toMutableList() ?: mutableListOf()

                    state.selectedStatus = state.applicationStatuses.first()

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
                            repairRequestsUseCase.getRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(
                                masterTitle = state.masterTitle,
                                masterTitleClarifying = state.masterTitleClarifying,
                                status = state.selectedStatus.title
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (!state.applyStatusFiltering && state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            repairRequestsUseCase.getRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(
                                masterTitle = state.masterTitle,
                                masterTitleClarifying = state.masterTitleClarifying,
                                startDate = startDate,
                                endDate = endDate
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (state.applyStatusFiltering && state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            repairRequestsUseCase.getRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(
                                masterTitle = state.masterTitle,
                                masterTitleClarifying = state.masterTitleClarifying,
                                status = state.selectedStatus.title,
                                startDate = startDate,
                                endDate = endDate
                            )?.map {
                                it.toDomain()
                            }?.toMutableList() ?: mutableStateListOf()
                    }
                    if (!state.applyStatusFiltering && !state.applyPeriodFiltering) {
                        state.allRepairRequests =
                            repairRequestsUseCase.getRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(
                                masterTitle = state.masterTitle,
                                masterTitleClarifying = state.masterTitleClarifying
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

    private fun checkInternetState() {
        state.contentState.internetIsNotAvailable.value =
            connectivityManager.getActiveNetworkInfo()?.isConnected ?: false
    }
}