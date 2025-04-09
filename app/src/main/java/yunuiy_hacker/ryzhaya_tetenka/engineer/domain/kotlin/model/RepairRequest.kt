package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model

data class RepairRequest(
    val id: Int = 0,
    val repairRequestNumber: String = "",
    val isViewed: Boolean = false,
    val userId: Int = 0
)
