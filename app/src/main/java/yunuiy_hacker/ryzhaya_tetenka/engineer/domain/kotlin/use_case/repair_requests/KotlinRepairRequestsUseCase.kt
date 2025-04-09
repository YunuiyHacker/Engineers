package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests

data class KotlinRepairRequestsUseCase(
    val getAllKotlinRepairRequestsOperator: GetAllKotlinRepairRequestsOperator,
    val getKotlinRepairRequestByIdOperator: GetKotlinRepairRequestByIdOperator,
    val getKotlinRepairRequestByNumberOperator: GetKotlinRepairRequestByNumberOperator,
    val insertKotlinRepairRequestOperator: InsertKotlinRepairRequestOperator,
    val updateKotlinRepairRequestOperator: UpdateKotlinRepairRequestOperator,
    val deleteKotlinRepairRequestByIdOperator: DeleteKotlinRepairRequestByIdOperator,
    val deleteKotlinRepairRequestByNumberOperator: DeleteKotlinRepairRequestByNumberOperator
)
