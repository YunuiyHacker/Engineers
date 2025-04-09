package yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    val id: Int = 0,
    val surname: String? = "",
    val name: String? = "",
    val lastname: String? = "",
    val login: String? = "",
    val password: String? = "",
    val masterId: Int? = 0,
    val deviceToken: String? = ""
) : Parcelable