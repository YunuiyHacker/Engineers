package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.UpdatedRepairRequestResponse
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.OneCRepairRequestsApi

class PutOneCRepairRequestsByDocumentNumberWithStatus(private val oneCRepairRequestsApi: OneCRepairRequestsApi) {
    suspend operator fun invoke(
        number: String,
        status: String
    ): UpdatedRepairRequestResponse? {
        return oneCRepairRequestsApi.putRepairRequestsByDocumentNumberWIthStatus(
            number = number,
            status = status
        )
    }
}