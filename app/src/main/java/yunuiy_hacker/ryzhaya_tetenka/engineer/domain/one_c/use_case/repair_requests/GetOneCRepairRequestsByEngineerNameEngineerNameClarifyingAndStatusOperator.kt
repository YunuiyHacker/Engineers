package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.OneCRepairRequestsApi

class GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(private val oneCRepairRequestsApi: OneCRepairRequestsApi) {
    suspend operator fun invoke(
        masterTitle: String,
        masterTitleClarifying: String,
        status: String
    ): List<RepairRequest>? {
        return oneCRepairRequestsApi.getRepairRequestsByEngineerNameEngineerNameClarifyingAndStatus(
            engineerName = masterTitle,
            engineerNameClarifying = masterTitleClarifying,
            status = status
        )
    }
}