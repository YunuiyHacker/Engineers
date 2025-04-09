package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class AdminHomeState {
    var users: MutableList<User> = mutableStateListOf()

    var message by mutableStateOf("")
    var showMessageDialog by mutableStateOf(false)

    var questionTitle by mutableStateOf("")
    var questionText by mutableStateOf("")
    var selectedMaster by mutableStateOf(User())
    var showQuestionDialog by mutableStateOf(false)

    val contentState by mutableStateOf(ContentState())
}