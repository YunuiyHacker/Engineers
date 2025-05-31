package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val application: Application,
    private val sharedPrefsHelper: SharedPrefsHelper
) :
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

            is ProfileEvent.ShowMessageDialog -> {
                state.message = event.message
                state.showMessageDialog = true
            }

            is ProfileEvent.HideMessageDialog -> state.showMessageDialog = false

            is ProfileEvent.ExitToAccountEvent -> exitToAccount()
        }
    }

    private fun loadData() {
        state.login = sharedPrefsHelper.login ?: ""
        state.fullName = sharedPrefsHelper.fullName ?: ""
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun exitToAccount() {
        state.contentState.isLoading.value = true

        GlobalScope.launch {
            runBlocking {
                try {
                    sharedPrefsHelper.login = ""
                    sharedPrefsHelper.fullName = ""

                    state.success = true
                } catch (e: Exception) {
                    state.message = e.message.toString()
                    state.showMessageDialog = true
                    state.contentState.isLoading.value = false
                }
            }
        }
    }
}