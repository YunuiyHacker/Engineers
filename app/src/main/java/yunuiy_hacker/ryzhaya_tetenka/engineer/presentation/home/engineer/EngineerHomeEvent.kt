package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.ApplicationStatus

sealed class EngineerHomeEvent {
    data object LoadDataEvent : EngineerHomeEvent()
    data class ShowMessageDialogEvent(val message: String) : EngineerHomeEvent()
    data object HideMessageDialogEvent : EngineerHomeEvent()

    data object ShowStatusPickerMenuEvent : EngineerHomeEvent()
    data class SelectStatusPickerMenuEvent(val status: ApplicationStatus) : EngineerHomeEvent()
    data object HideStatusPickerMenuEvent : EngineerHomeEvent()

    data object ShowStartDatePickerDialogEvent : EngineerHomeEvent()
    data class SelectStartDateEvent(val dateInMilliseconds: Long) : EngineerHomeEvent()

    data object ShowEndDatePickerDialogEvent : EngineerHomeEvent()
    data class SelectEndDateEvent(val dateInMilliseconds: Long) : EngineerHomeEvent()

    data object HideDatePickerDialogEvent : EngineerHomeEvent()

    data class SearchRepairRequestsEvent(val query: String) : EngineerHomeEvent()

    data object ToggleStatusApplyingEvent : EngineerHomeEvent()
    data object TogglePeriodApplyingEvent : EngineerHomeEvent()

    data object CheckInternetStateEvent : EngineerHomeEvent()
}