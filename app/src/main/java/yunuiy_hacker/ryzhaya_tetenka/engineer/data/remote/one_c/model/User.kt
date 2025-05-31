package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val auth_is_success: Boolean? = false,
    val login: String? = "",
    val full_name: String? = ""
) : Parcelable
