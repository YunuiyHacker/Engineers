package yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs

import android.content.Context

class SharedPrefsHelper(context: Context) {

    private val personal_data = context.getSharedPreferences(PERSONAL_DATA, Context.MODE_PRIVATE)

    var userId
        set(value) {
            with(personal_data.edit()) {
                if (value < 0) remove(USER_ID)
                else putInt(USER_ID, value)
                apply()
            }
        }
        get() = personal_data.getInt(USER_ID, 0)

    var login
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(LOGIN)
                else putString(LOGIN, value)
                apply()
            }
        }
        get() = personal_data.getString(LOGIN, "")

    var surname
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(SURNAME)
                else putString(SURNAME, value)
                apply()
            }
        }
        get() = personal_data.getString(SURNAME, "")

    var name
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(NAME)
                else putString(NAME, value)
                apply()
            }
        }
        get() = personal_data.getString(NAME, "")

    var lastname
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(LASTNAME)
                else putString(LASTNAME, value)
                apply()
            }
        }
        get() = personal_data.getString(LASTNAME, "")

    var masterId
        set(value) {
            with(personal_data.edit()) {
                if (value < 0) remove(MASTER_ID)
                else putInt(MASTER_ID, value)
                apply()
            }
        }
        get() = personal_data.getInt(MASTER_ID, 0)

    var title
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(TITLE)
                else putString(TITLE, value)
                apply()
            }
        }
        get() = personal_data.getString(TITLE, "")

    var titleClarifying
        set(value) {
            with(personal_data.edit()) {
                if (value.isNullOrEmpty()) remove(TITLE_CLARIFYING)
                else putString(TITLE_CLARIFYING, value)
                apply()
            }
        }
        get() = personal_data.getString(TITLE_CLARIFYING, "")

    companion object {
        private const val PERSONAL_DATA = "personal_data"

        private const val USER_ID = "user_id"
        private const val LOGIN = "login"
        private const val SURNAME = "surname"
        private const val NAME = "name"
        private const val LASTNAME = "lastname"

        private const val MASTER_ID = "master_id"
        private const val TITLE = "title"
        private const val TITLE_CLARIFYING = "title_clarifying"
    }
}