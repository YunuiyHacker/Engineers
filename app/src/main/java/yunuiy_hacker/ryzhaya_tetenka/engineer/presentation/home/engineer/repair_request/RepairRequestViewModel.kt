package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.repair_request

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests.OneCRepairRequestsUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.statuses.StatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getConnectivityManager
import javax.inject.Inject

@HiltViewModel
class RepairRequestViewModel @Inject constructor(
    val application: Application,
    private val statusesUseCase: StatusesUseCase,
    private val oneCRepairRequestsUseCase: OneCRepairRequestsUseCase
) :
    ViewModel() {

    val connectivityManager = getConnectivityManager(application)

    val state by mutableStateOf(RepairRequestState())

    fun onEvent(event: RepairRequestEvent) {
        when (event) {
            is RepairRequestEvent.LoadDataEvent -> loadData()

            is RepairRequestEvent.ShowStatusPickerMenuEvent -> state.showStatusPickerMenu = true
            is RepairRequestEvent.SelectStatusPickerMenuEvent -> {
                state.selectedStatus =
                    event.status
                state.showStatusPickerMenu = false
            }

            is RepairRequestEvent.HideStatusPickerMenuEvent -> state.showStatusPickerMenu = false

            is RepairRequestEvent.SaveChangesEvent -> saveChanges()

            is RepairRequestEvent.ShowMessageDialogEvent -> {
                state.message = event.message
                state.showMessageDialog = true
            }

            is RepairRequestEvent.HideMessageDialogEvent -> state.showMessageDialog = false
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        state.contentState.isLoading.value = true

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.statuses =
                        statusesUseCase.getAllStatusesOperator()
                            ?.map { it.toDomain() }?.toMutableList() ?: mutableStateListOf()

                    state.selectedStatus = state.statuses.find {
                        it.title.lowercase()
                            .equals(state.repairRequest.status.lowercase())
                    } ?: state.statuses[0]

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

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveChanges() {
        state.contentState.isLoading.value = true

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    oneCRepairRequestsUseCase.putOneCRepairRequestsByDocumentNumberWithStatus(
                        number = state.repairRequest.number.toString(),
                        status = state.selectedStatus.title
                    )

                    state.success = true
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