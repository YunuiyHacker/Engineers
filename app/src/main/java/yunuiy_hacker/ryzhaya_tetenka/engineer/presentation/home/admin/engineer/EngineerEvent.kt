package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.Employee

sealed class EngineerEvent {
    data object LoadDataEvent : EngineerEvent()

    data class ChangeSurnameEvent(val surname: String) : EngineerEvent()
    data class ChangeNameEvent(val name: String) : EngineerEvent()
    data class ChangeLastnameEvent(val lastname: String) : EngineerEvent()
    data class ChangeLoginEvent(val login: String) : EngineerEvent()
    data class ChangePasswordEvent(val password: String) : EngineerEvent()
    data class ChangePasswordConfirmEvent(val passwordConfirm: String) : EngineerEvent()

    data object ShowEmployeePickerMenuEvent : EngineerEvent()
    data class SelectEmployeePickerMenuEvent(val employee: Employee) : EngineerEvent()
    data object HideEmployeePickerMenuEvent : EngineerEvent()

    data class ShowMessageDialog(val message: String) : EngineerEvent()
    data object HideMessageDialog : EngineerEvent()

    data object OnClickButtonEvent : EngineerEvent()
}