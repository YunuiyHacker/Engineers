package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters

data class MastersUseCase(
    val getAllMastersOperator: GetAllMastersOperator,
    val getMasterById: GetMasterById,
    val insertMasterOperator: InsertMasterOperator,
    val updateMasterOperator: UpdateMasterOperator,
    val deleteMasterByIdOperator: DeleteMasterByIdOperator
)
