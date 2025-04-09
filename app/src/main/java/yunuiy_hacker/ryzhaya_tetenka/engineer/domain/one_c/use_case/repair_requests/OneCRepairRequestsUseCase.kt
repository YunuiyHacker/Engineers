package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

data class OneCRepairRequestsUseCase(
    val getOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator: GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator,
    val getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator: GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator,
    val getOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator: GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator,
    val getOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator: GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator,
    val putOneCRepairRequestsByDocumentNumberWithStatus: PutOneCRepairRequestsByDocumentNumberWithStatus
)