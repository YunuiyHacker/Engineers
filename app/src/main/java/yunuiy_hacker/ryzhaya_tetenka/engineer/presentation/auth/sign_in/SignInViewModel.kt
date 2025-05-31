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
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.AuthData
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.auth.AuthUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
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

            is SignInEvent.ShowPrivacyPolicyBottomSheetEvent -> state.showPrivacyPolicyBottomSheet =
                true

            is SignInEvent.HidePrivacyPolicyBottomSheetEvent -> state.showPrivacyPolicyBottomSheet =
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
                        authUseCase.authOperator(AuthData(state.login, state.password))
                    if (user.auth_is_success == true) {
                        state.user = user.toDomain()
                        saveUserData(state.user)

                        state.success = true
                    } else {
                        state.messageText = application.getString(R.string.user_does_not_exist)
                        if (state.messageText.isNotEmpty())
                            state.showMessageDialog = true
                    }
                    state.contentState.isLoading.value = false
                } catch (e: Exception) {
                    state.messageText = e.message.toString()
                    if (state.messageText.isNotEmpty())
                        state.showMessageDialog = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }

    private fun validate() {
        if (state.login.length >= 1) if (state.password.length >= 1) state.validFields = true
        else state.validFields = false
        else state.validFields = false
    }

    private fun saveUserData(user: User) {
        sharedPrefsHelper.login = user.login
        sharedPrefsHelper.fullName = user.full_name
    }
}