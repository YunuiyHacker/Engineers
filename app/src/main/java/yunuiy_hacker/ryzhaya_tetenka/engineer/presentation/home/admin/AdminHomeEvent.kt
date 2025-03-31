package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin

import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.User

sealed class AdminHomeEvent {
    data object LoadDataEvent : AdminHomeEvent()

    data class ShowMessageDialogEvent(val message: String) : AdminHomeEvent()
    data object HideMessageDialogEvent : AdminHomeEvent()

    data class ShowQuestionDialogEvent(val title: String, val text: String, val user: User) :
        AdminHomeEvent()

    data object HideQuestionDialogEvent : AdminHomeEvent()

    data object DeleteMasterEvent : AdminHomeEvent()
}