package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi

class GetOneCRepairRequestsByEngineerNameAndEngineerNameClarifyingOperator(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        login: String,
        full_name: String
    ): List<RepairRequest>? {
        return repairRequestsApi.getRepairRequestsByEngineerNameAndEngineerNameClarifying(
            login = login,
            full_name = full_name
        )
    }
}