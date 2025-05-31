package yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs

import android.content.Context

class SharedPrefsHelper(context: Context) {

    private val personal_data = context.getSharedPreferences(PERSONAL_DATA, Context.MODE_PRIVATE)

    var login
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(LOGIN)
                else putString(LOGIN, value)
                apply()
            }
        }
        get() = personal_data.getString(LOGIN, "")

    var fullName
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(FULL_NAME)
                else putString(FULL_NAME, value)
                apply()
            }
        }
        get() = personal_data.getString(FULL_NAME, "")

    companion object {
        private const val PERSONAL_DATA = "personal_data"


        private const val LOGIN = "login"
        private const val FULL_NAME = "full_name"
    }
}