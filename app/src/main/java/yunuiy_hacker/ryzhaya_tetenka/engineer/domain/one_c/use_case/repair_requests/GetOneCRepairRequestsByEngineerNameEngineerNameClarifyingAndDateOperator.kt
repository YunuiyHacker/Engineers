package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat
import java.util.Date

class GetOneCRepairRequestsByEngineerNameEngineerNameClarifyingAndDateOperator(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        login: String,
        full_name: String,
        startDate: Date,
        endDate: Date
    ): List<RepairRequest>? {
        return repairRequestsApi.getRepairRequestsByEngineerNameEngineerNameClarifyingAndDate(
            login = login,
            full_name = full_name,
            start_date = startDate.toOneCDateStringFormat(),
            end_date = endDate.toOneCDateStringFormat()
        )
    }
}