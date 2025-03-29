package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model

data class User(
    val id: Int = 0,
    val surname: String = "",
    val name: String = "",
    val lastname: String = "",
    val login: String = "",
    val password: String = "",
    val masterId: Int = 0,
    var master: Master? = null
)
