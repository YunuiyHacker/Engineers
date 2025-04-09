package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RepairRequest(
    val id: Int = 0,
    val repairRequestNumber: String? = "",
    val isViewed: Boolean? = false,
    val userId: Int? = 0
) : Parcelable
