package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toData
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users.UsersUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val application: Application,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val usersUseCase: UsersUseCase
) :
    ViewModel() {
    val state by mutableStateOf(ProfileState())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadDataEvent -> loadData()

            is ProfileEvent.ShowQuestionDialog -> {
                state.showQuestionDialog = true
                state.title = event.title
                state.text = event.text
            }

            is ProfileEvent.HideQuestionDialog -> state.showQuestionDialog = false

            is ProfileEvent.ShowMessageDialog -> {
                state.message = event.message
                state.showMessageDialog = true
            }

            is ProfileEvent.HideMessageDialog -> state.showMessageDialog = false

            is ProfileEvent.ExitToAccountEvent -> exitToAccount()
        }
    }

    private fun loadData() {
        state.surname = sharedPrefsHelper.surname ?: ""
        state.name = sharedPrefsHelper.name ?: ""
        state.lastname = sharedPrefsHelper.lastname ?: ""
        state.masterId = sharedPrefsHelper.masterId
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun exitToAccount() {
        state.contentState.isLoading.value = true

        GlobalScope.launch {
            runBlocking {
                try {
                    var user = usersUseCase.getUserByIdOperator(sharedPrefsHelper.userId)
                    var deviceToken: String = ""

                    if (user != null) {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener {
                            deviceToken = it.result
                        }

                        user = usersUseCase.updateUserOperator(
                            user.copy(deviceToken = deviceToken)
                        )

                        state.message = user.toString()

                        state.showQuestionDialog = false

                        state.contentState.isLoading.value = false
                        if (user == null) {
                            state.message =
                                application.getString(R.string.could_not_log_out_of_account)
                            state.showMessageDialog = true
                        } else {
                            sharedPrefsHelper.userId = 0
                            sharedPrefsHelper.surname = ""
                            sharedPrefsHelper.name = ""
                            sharedPrefsHelper.lastname = ""
                            sharedPrefsHelper.login = ""

                            state.success = true
                        }
                    }
                } catch (e: Exception) {
//                    state.message = e.message.toString()
                    state.showMessageDialog = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }
}