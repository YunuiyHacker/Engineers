package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.KotlinRepairRequestsApi

class DeleteKotlinRepairRequestByIdOperator(private val kotlinRepairRequestsApi: KotlinRepairRequestsApi) {
    suspend operator fun invoke(repairRequestId: Int) {
        kotlinRepairRequestsApi.deleteRepairRequestById(repairRequestId = repairRequestId)
    }
}