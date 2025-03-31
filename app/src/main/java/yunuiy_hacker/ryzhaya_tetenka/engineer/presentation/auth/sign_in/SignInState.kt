package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class SignInState {
    var login by mutableStateOf("")
    var password by mutableStateOf("")

    var passwordVisibility by mutableStateOf(true)

    var validFields by mutableStateOf(false)

    var user by mutableStateOf(User())

    var showMessageDialog by mutableStateOf(false)
    var messageText by mutableStateOf("")

    var showForgotPasswordBottomSheet by mutableStateOf(false)

    var contentState by mutableStateOf(ContentState())

    var success by mutableStateOf(false)
}