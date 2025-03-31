package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class EngineerState {
    var user by mutableStateOf(User())
    var master by mutableStateOf(Master())

    var employees: MutableList<Employee> = mutableStateListOf()

    var surname by mutableStateOf("")
    var name by mutableStateOf("")
    var lastname by mutableStateOf("")
    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")
    var selectedEmployee by mutableStateOf(Employee())

    var showEmployeePickerMenu by mutableStateOf(false)

    var message by mutableStateOf("")
    var showMessageDialog by mutableStateOf(false)

    var contentState by mutableStateOf(ContentState())

    var success by mutableStateOf(false)
}