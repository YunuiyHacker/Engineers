package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.UpdatedRepairRequestResponse
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.RepairRequestsApi

class PutRepairRequestsByDocumentNumberWithStatus(private val repairRequestsApi: RepairRequestsApi) {
    suspend operator fun invoke(
        number: String,
        status: String
    ): UpdatedRepairRequestResponse? {
        return repairRequestsApi.putRepairRequestsByDocumentNumberWIthStatus(
            number = number,
            status = status
        )
    }
}