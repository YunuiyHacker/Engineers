package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.auth.sign_in

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.users.UsersUseCase
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters.MastersUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val mastersUseCase: MastersUseCase,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val application: Application
) : ViewModel() {
    val state by mutableStateOf(SignInState())

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.ChangeLoginEvent -> {
                state.login = event.login
                validate()
            }

            is SignInEvent.ChangePasswordEvent -> {
                state.password = event.password
                validate()
            }

            is SignInEvent.TogglePasswordVisibilityEvent -> state.passwordVisibility =
                !state.passwordVisibility

            is SignInEvent.ShowMessageDialogEvent -> {
                state.messageText = event.text
                state.showMessageDialog = true
            }

            is SignInEvent.HideMessageDialogEvent -> state.showMessageDialog = false

            is SignInEvent.ShowForgotPasswordBottomSheetEvent -> state.showForgotPasswordBottomSheet =
                true

            is SignInEvent.HideForgotPasswordBottomSheetEvent -> state.showForgotPasswordBottomSheet =
                false

            is SignInEvent.SignInOnClickEvent -> signIn()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun signIn() {
        state.contentState.isLoading.value = true

        GlobalScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    val user =
                        usersUseCase.getUserByLoginAndPasswordOperator(state.login, state.password)
                    if (user?.id != 0) {
                        state.user = user?.toDomain()!!

                        if (state.user.masterId != 0)
                            state.user.master =
                                mastersUseCase.getMasterById(state.user.masterId)?.toDomain()

                        saveUserData(state.user)

                        state.success = true
                    } else {
                        state.messageText = application.getString(R.string.user_does_not_exist)
                        state.showMessageDialog = true
                    }
                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
                    state.messageText = e.message.toString()
                    state.showMessageDialog = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }

    private fun validate() {
        if (state.login.length >= 5) if (state.password.length >= 5) state.validFields = true
        else state.validFields = false
        else state.validFields = false
    }

    private fun saveUserData(user: User) {
        sharedPrefsHelper.userId = user.id
        sharedPrefsHelper.surname = user.surname
        sharedPrefsHelper.name = user.name
        sharedPrefsHelper.lastname = user.lastname
        sharedPrefsHelper.login = user.login

        sharedPrefsHelper.masterId = user.masterId
        if (user.master != null) {
            sharedPrefsHelper.title = user.master?.title
            sharedPrefsHelper.titleClarifying = user.master?.titleClarifying
        }
    }
}