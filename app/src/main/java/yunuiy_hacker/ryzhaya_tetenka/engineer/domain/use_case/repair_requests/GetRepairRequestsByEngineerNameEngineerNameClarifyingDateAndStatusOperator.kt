package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat
import java.util.Calendar
import java.util.Date

class GetRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatusOperator(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        masterTitle: String,
        masterTitleClarifying: String,
        startDate: Date,
        endDate: Date,
        status: String
    ): List<RepairRequest>? {
        return repairRequestsApi.getRepairRequestsByEngineerNameEngineerNameClarifyingDateAndStatus(
            engineerName = masterTitle,
            engineerNameClarifying = masterTitleClarifying,
            start_date = startDate.toOneCDateStringFormat(),
            end_date = endDate.toOneCDateStringFormat(),
            status = status
        )
    }
}