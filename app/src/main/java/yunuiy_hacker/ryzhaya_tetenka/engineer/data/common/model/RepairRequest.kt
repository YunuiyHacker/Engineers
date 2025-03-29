package yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairRequest(
    val number: String = "",
    val date: String? = "",
    val organization_title: String? = "",
    val warehouse: String? = "",
    val manager_name: String? = "",
    val comment: String? = "",
    val counterparty: String? = "",
    val partner: String? = "",
    val contact_person: ContactPerson? = null,
    val nomenclature: String? = "",
    val malfunction_description: String? = "",
    val malfunction: String? = "",
    val completeness: String? = "",
    val status: String? = "",
    val under_warranty: Boolean? = false,
    val series: String? = "",
    val date_of_equipment_issue: String? = ""
) : Parcelable