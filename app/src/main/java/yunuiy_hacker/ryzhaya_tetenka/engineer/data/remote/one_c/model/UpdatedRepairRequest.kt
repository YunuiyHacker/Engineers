package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdatedRepairRequest(val number: String = "", val status: String = "") : Parcelable