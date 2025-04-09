package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactPerson(
    val title: String? = "",
    val business_card_position: String? = "",
    val phone_number: String? = ""
) :
    Parcelable