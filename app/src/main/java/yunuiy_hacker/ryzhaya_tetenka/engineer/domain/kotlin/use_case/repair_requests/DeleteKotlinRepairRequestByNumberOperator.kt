package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.KotlinRepairRequestsApi

class DeleteKotlinRepairRequestByNumberOperator(private val kotlinRepairRequestsApi: KotlinRepairRequestsApi) {
    suspend operator fun invoke(repairRequestNumber: String) {
        kotlinRepairRequestsApi.deleteRepairRequestByNumber(repairRequestNumber = repairRequestNumber)
    }
}