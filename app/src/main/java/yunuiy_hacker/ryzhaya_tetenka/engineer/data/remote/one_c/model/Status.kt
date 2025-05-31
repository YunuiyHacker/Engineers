package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(val title: String? = "", val synonym: String? = "") : Parcelable
