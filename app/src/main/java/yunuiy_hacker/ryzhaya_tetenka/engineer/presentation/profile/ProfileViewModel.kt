package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sharedPrefsHelper: SharedPrefsHelper) :
    ViewModel() {
    val state by mutableStateOf(ProfileState())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadDataEvent -> loadData()

            is ProfileEvent.ShowQuestionDialog -> {
                state.showQuestionDialog = true
                state.title = event.title
                state.text = event.text
            }

            is ProfileEvent.HideQuestionDialog -> state.showQuestionDialog = false

            is ProfileEvent.ExitToAccountEvent -> exitToAccount()
        }
    }

    private fun loadData() {
        state.surname = sharedPrefsHelper.surname ?: ""
        state.name = sharedPrefsHelper.name ?: ""
        state.lastname = sharedPrefsHelper.lastname ?: ""
    }

    private fun exitToAccount() {
        state.showQuestionDialog = false

        sharedPrefsHelper.userId = 0
        sharedPrefsHelper.surname = ""
        sharedPrefsHelper.name = ""
        sharedPrefsHelper.lastname = ""
        sharedPrefsHelper.login = ""

        state.success = true
    }
}