package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat
import java.util.Calendar

class GetRepairRequestsByEngineerNameEngineerNameClarifyingAndStatusOperator(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        masterTitle: String,
        masterTitleClarifying: String,
        status: String
    ): List<RepairRequest>? {
        return repairRequestsApi.getRepairRequestsByEngineerNameEngineerNameClarifyingAndStatus(
            engineerName = masterTitle,
            engineerNameClarifying = masterTitleClarifying,
            status = status
        )
    }
}