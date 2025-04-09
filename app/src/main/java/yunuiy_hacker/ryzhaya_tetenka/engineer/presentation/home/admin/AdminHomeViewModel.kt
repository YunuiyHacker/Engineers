package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin

import android.app.Application
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
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.MastersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.UsersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getConnectivityManager
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    val application: Application,
    private val usersUseCase: UsersUseCase,
    private val mastersUseCase: MastersUseCase,
    val gson: Gson
) : ViewModel() {
    val state by mutableStateOf(AdminHomeState())

    private val connectivityManager =
        getConnectivityManager(application)

    fun onEvent(event: AdminHomeEvent) {
        when (event) {
            is AdminHomeEvent.LoadDataEvent -> loadData()

            is AdminHomeEvent.ShowMessageDialogEvent -> {
                state.showMessageDialog = true
                state.message = event.message
            }

            is AdminHomeEvent.HideMessageDialogEvent -> state.showMessageDialog = false

            is AdminHomeEvent.ShowQuestionDialogEvent -> {
                state.questionTitle = event.title
                state.questionText = event.text
                state.selectedMaster = event.user
                state.showQuestionDialog = true
            }

            is AdminHomeEvent.HideQuestionDialogEvent -> {
                state.showQuestionDialog = false
            }

            is AdminHomeEvent.DeleteMasterEvent -> deleteMaster()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        state.contentState.isLoading.value = true

        checkInternetState()

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.users = usersUseCase.getAllUsersOperator()?.map { it -> it.toDomain() }
                        ?.filter { it -> it.masterId != 0 }?.toMutableList() ?: mutableStateListOf()

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
    private fun deleteMaster() {
        state.contentState.isLoading.value = true

        checkInternetState()

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.showQuestionDialog = false

                    usersUseCase.deleteUserOperatorByIdOperator(state.selectedMaster.id)
                    mastersUseCase.deleteMasterByIdOperator(state.selectedMaster.masterId)

                    state.users.remove(state.selectedMaster)

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