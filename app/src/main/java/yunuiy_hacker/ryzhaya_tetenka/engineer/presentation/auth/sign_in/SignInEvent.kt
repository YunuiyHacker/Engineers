package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.auth.sign_in

sealed class SignInEvent {
    data class ChangeLoginEvent(val login: String) : SignInEvent()
    data class ChangePasswordEvent(val password: String) : SignInEvent()
    data object TogglePasswordVisibilityEvent : SignInEvent()

    data class ShowMessageDialogEvent(val text: String) : SignInEvent()
    data object HideMessageDialogEvent : SignInEvent()

    data object ShowForgotPasswordBottomSheetEvent : SignInEvent()
    data object HideForgotPasswordBottomSheetEvent : SignInEvent()

    data object SignInOnClickEvent : SignInEvent()
}