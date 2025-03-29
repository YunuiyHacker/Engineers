package yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    val title: String? = "",
    val title_clarifying: String? = "",
    val inn: String? = "",
    val date_of_birth: String? = ""
) :
    Parcelable