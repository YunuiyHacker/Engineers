package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

sealed class ProfileEvent {
    data object LoadDataEvent : ProfileEvent()

    data class ShowQuestionDialog(val title: String, val text: String) : ProfileEvent()
    data object HideQuestionDialog : ProfileEvent()

    data object ExitToAccountEvent : ProfileEvent()
}