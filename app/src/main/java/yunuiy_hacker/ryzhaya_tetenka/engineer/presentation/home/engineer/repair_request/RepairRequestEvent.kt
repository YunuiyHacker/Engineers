package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.repair_request

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status

sealed class RepairRequestEvent {
    data object LoadDataEvent : RepairRequestEvent()

    data object ShowStatusPickerMenuEvent : RepairRequestEvent()
    data class SelectStatusPickerMenuEvent(val status: Status) :
        RepairRequestEvent()

    data object HideStatusPickerMenuEvent : RepairRequestEvent()

    data object SaveChangesEvent : RepairRequestEvent()

    data class ShowMessageDialogEvent(val message: String) : RepairRequestEvent()
    data object HideMessageDialogEvent : RepairRequestEvent()
}