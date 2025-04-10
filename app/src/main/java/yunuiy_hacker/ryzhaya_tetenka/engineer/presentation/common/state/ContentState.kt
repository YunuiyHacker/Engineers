package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.lang.Exception

data class ContentState(
    var data: MutableState<String?> = mutableStateOf(null),
    var exception: MutableState<Exception?> = mutableStateOf(null),
    var isLoading: MutableState<Boolean> = mutableStateOf(false),
    var internetIsNotAvailable: MutableState<Boolean> = mutableStateOf(false),
    var hasConnectionToServers: MutableState<Boolean> = mutableStateOf(true)
)