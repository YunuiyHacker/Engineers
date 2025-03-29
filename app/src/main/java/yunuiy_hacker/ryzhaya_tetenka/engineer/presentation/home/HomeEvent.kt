package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.ApplicationStatus

sealed class HomeEvent {
    data object LoadDataEvent : HomeEvent()
    data class ShowMessageEvent(val message: String) : HomeEvent()
    data object HideMessageEvent : HomeEvent()

    data object ShowStatusPickerMenuEvent : HomeEvent()
    data class SelectStatusPickerMenuEvent(val status: ApplicationStatus) : HomeEvent()
    data object HideStatusPickerMenuEvent : HomeEvent()

    data object ShowStartDatePickerDialogEvent : HomeEvent()
    data class SelectStartDateEvent(val dateInMilliseconds: Long) : HomeEvent()

    data object ShowEndDatePickerDialogEvent : HomeEvent()
    data class SelectEndDateEvent(val dateInMilliseconds: Long) : HomeEvent()

    data object HideDatePickerDialogEvent : HomeEvent()

    data class SearchRepairRequestsEvent(val query: String) : HomeEvent()

    data object ToggleStatusApplyingEvent : HomeEvent()
    data object TogglePeriodApplyingEvent : HomeEvent()

    data object CheckInternetStateEvent : HomeEvent()
}