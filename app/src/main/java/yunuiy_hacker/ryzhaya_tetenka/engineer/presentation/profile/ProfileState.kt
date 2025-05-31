package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class ProfileState {
    var login by mutableStateOf("")
    var fullName by mutableStateOf("")

    var showQuestionDialog by mutableStateOf(false)
    var title by mutableStateOf("")
    var text by mutableStateOf("")

    var showMessageDialog by mutableStateOf(false)
    var message by mutableStateOf("")

    var contentState by mutableStateOf(ContentState())

    var success by mutableStateOf(false)
}