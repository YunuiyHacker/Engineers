package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Master(
    val id: Int = 0,
    val title: String? = "",
    val titleClarifying: String? = "",
    val inn: String? = ""
) :
    Parcelable