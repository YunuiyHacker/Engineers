package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi

class GetRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        masterTitle: String,
        masterTitleClarifying: String
    ): List<RepairRequest>? {
        return repairRequestsApi.getRepairRequestsByEngineerNameAndEngineerNameClarifying(
            engineerName = masterTitle,
            engineerNameClarifying = masterTitleClarifying
        )
    }
}