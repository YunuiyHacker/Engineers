package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.repair_request

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.ApplicationStatus

sealed class RepairRequestEvent {
    data object LoadDataEvent : RepairRequestEvent()

    data object ShowStatusPickerMenuEvent : RepairRequestEvent()
    data class SelectStatusPickerMenuEvent(val applicationStatus: ApplicationStatus) :
        RepairRequestEvent()

    data object HideStatusPickerMenuEvent : RepairRequestEvent()

    data object SaveChangesEvent : RepairRequestEvent()

    data class ShowMessageDialogEvent(val message: String) : RepairRequestEvent()
    data object HideMessageDialogEvent : RepairRequestEvent()
}