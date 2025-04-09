package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.OneCRepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat
import java.util.Date

class GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(private val oneCRepairRequestsApi: OneCRepairRequestsApi) {
    suspend operator fun invoke(
        masterTitle: String,
        masterTitleClarifying: String,
        startDate: Date,
        endDate: Date
    ): List<RepairRequest>? {
        return oneCRepairRequestsApi.getRepairRequestsByEngineerNameEngineerNameClarifyingAndDate(
            engineerName = masterTitle,
            engineerNameClarifying = masterTitleClarifying,
            start_date = startDate.toOneCDateStringFormat(),
            end_date = endDate.toOneCDateStringFormat()
        )
    }
}