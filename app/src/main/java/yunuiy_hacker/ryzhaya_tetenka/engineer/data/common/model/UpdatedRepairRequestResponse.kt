package yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdatedRepairRequestResponse(
    val updatedRepairRequest: UpdatedRepairRequest = UpdatedRepairRequest(),
    val updated: Boolean = false
) : Parcelable
