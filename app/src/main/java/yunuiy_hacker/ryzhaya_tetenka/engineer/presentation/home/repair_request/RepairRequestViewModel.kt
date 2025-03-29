package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.repair_request

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
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses.ApplicationStatusesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests.RepairRequestsUseCase
import javax.inject.Inject

@HiltViewModel
class RepairRequestViewModel @Inject constructor(
    val application: Application,
    private val applicationStatusesUseCase: ApplicationStatusesUseCase,
    private val repairRequestsUseCase: RepairRequestsUseCase
) :
    ViewModel() {
    val state by mutableStateOf(RepairRequestState())

    fun onEvent(event: RepairRequestEvent) {
        when (event) {
            is RepairRequestEvent.LoadDataEvent -> loadData()

            is RepairRequestEvent.ShowStatusPickerMenuEvent -> state.showStatusPickerMenu = true
            is RepairRequestEvent.SelectStatusPickerMenuEvent -> {
                state.selectedApplicationStatus =
                    event.applicationStatus
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
                    state.applicationStatuses =
                        applicationStatusesUseCase.getAllApplicationStatusesOperator()
                            ?.map { it.toDomain() }?.toMutableList() ?: mutableStateListOf()

                    state.selectedApplicationStatus = state.applicationStatuses.find {
                        it.normalizedTitle.lowercase()
                            .equals(state.repairRequest.status.lowercase())
                    } ?: state.applicationStatuses[0]

                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
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
                    repairRequestsUseCase.putRepairRequestsByDocumentNumberWithStatus(
                        number = state.repairRequest.number.toString(),
                        status = state.selectedApplicationStatus.title
                    )

                    state.success = true

                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
                    state.message = e.message.toString()
                    state.showMessageDialog = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }
}