package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer

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
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toData
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.epmloyees.EmployeesUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters.MastersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.UsersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.getConnectivityManager
import javax.inject.Inject

@HiltViewModel
class EngineerViewModel @Inject constructor(
    val application: Application,
    private val employeesUseCase: EmployeesUseCase,
    private val mastersUseCase: MastersUseCase,
    private val usersUseCase: UsersUseCase
) : ViewModel() {

    val connectivityManager = getConnectivityManager(application)

    val state by mutableStateOf(EngineerState())

    fun onEvent(event: EngineerEvent) {
        when (event) {
            is EngineerEvent.LoadDataEvent -> loadData()

            is EngineerEvent.ChangeSurnameEvent -> state.surname = event.surname
            is EngineerEvent.ChangeNameEvent -> state.name = event.name
            is EngineerEvent.ChangeLastnameEvent -> state.lastname = event.lastname
            is EngineerEvent.ChangeLoginEvent -> state.login = event.login
            is EngineerEvent.ChangePasswordEvent -> state.password = event.password
            is EngineerEvent.ChangePasswordConfirmEvent -> state.passwordConfirm =
                event.passwordConfirm

            is EngineerEvent.ShowEmployeePickerMenuEvent -> state.showEmployeePickerMenu = true
            is EngineerEvent.SelectEmployeePickerMenuEvent -> {
                state.selectedEmployee = event.employee
                state.showEmployeePickerMenu = false
            }

            is EngineerEvent.HideEmployeePickerMenuEvent -> state.showEmployeePickerMenu = false

            is EngineerEvent.ShowMessageDialog -> {
                state.message = event.message
                state.showMessageDialog = true
            }

            is EngineerEvent.HideMessageDialog -> state.showMessageDialog = false

            is EngineerEvent.OnClickButtonEvent -> addOrUpdateMaster()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        state.contentState.isLoading.value = true

        checkInternetState()

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    state.employees =
                        employeesUseCase.getAllEmployeesOperator()?.map { it.toDomain() }
                            ?.toMutableList() ?: mutableStateListOf()

                    state.master = mastersUseCase.getMasterById(state.user.masterId)?.toDomain()
                        ?: yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.Master()

                    state.surname = state.user.surname
                    state.name = state.user.name
                    state.lastname = state.user.lastname
                    state.login = state.user.login
                    state.password = state.user.password
                    state.passwordConfirm = state.user.password
                    state.selectedEmployee =
                        state.employees.find { it.title == state.master.title && it.title_clarifying == state.master.titleClarifying && it.inn == state.master.inn }
                            ?: Employee()

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
                    if (state.contentState.internetIsNotAvailable.value && !state.message.equals("null")) state.showMessageDialog =
                        true
                    state.contentState.hasConnectionToServers.value = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addOrUpdateMaster() {
        state.contentState.isLoading.value = true

        checkInternetState()
        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    val users = usersUseCase.getAllUsersOperator()

                    if (state.surname.isEmpty()) {
                        state.message = application.getString(R.string.please_fill_surname)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.name.isEmpty()) {
                        state.message = application.getString(R.string.please_fill_name)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.login.isEmpty()) {
                        state.message = application.getString(R.string.please_fill_login)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.password.isEmpty()) {
                        state.message = application.getString(R.string.please_fill_password)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.passwordConfirm.isEmpty()) {
                        state.message = application.getString(R.string.please_fill_password_confirm)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.password != state.passwordConfirm) {
                        state.message =
                            application.getString(R.string.password_and_him_confirm_not_equals)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (state.selectedEmployee.title.isEmpty()) {
                        state.message = application.getString(R.string.please_select_employee)
                        state.showMessageDialog = true
                        state.contentState.isLoading.value = false
                    } else if (if (state.user.id == 0) users?.find { it.login == state.login } != null else false) {
                        state.message =
                            application.getString(R.string.user_with_this_login_already_exist)
                    } else {
                        state.message = ""

                        val masters = mastersUseCase.getAllMastersOperator()?.map { it.toDomain() }
                            ?.toMutableList() ?: mutableStateListOf()

                        var master: yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.Master? =
                            masters.find { it.title == state.selectedEmployee.title && it.titleClarifying == state.selectedEmployee.title_clarifying && it.inn == state.selectedEmployee.inn }
                        if (master == null) {
                            master = mastersUseCase.insertMasterOperator(
                                Master(
                                    title = state.selectedEmployee.title,
                                    titleClarifying = state.selectedEmployee.title_clarifying,
                                    inn = state.selectedEmployee.inn
                                )
                            )?.toDomain()
                        }

                        if (state.user.id != 0) {
                            val user = usersUseCase.updateUserOperator(
                                state.user.copy(
                                    surname = state.surname,
                                    name = state.name,
                                    lastname = state.lastname,
                                    login = state.login,
                                    password = state.password,
                                    masterId = master?.id ?: 0
                                ).toData()
                            )?.toDomain()
                        } else {
                            val user = usersUseCase.insertUserOperator(
                                User(
                                    surname = state.surname,
                                    name = state.name,
                                    lastname = state.lastname,
                                    login = state.login,
                                    password = state.password,
                                    masterId = master?.id
                                )
                            )?.toDomain()
                        }

                        state.success = true
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
                    if (state.contentState.internetIsNotAvailable.value && !state.message.equals("null")) state.showMessageDialog =
                        true
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
