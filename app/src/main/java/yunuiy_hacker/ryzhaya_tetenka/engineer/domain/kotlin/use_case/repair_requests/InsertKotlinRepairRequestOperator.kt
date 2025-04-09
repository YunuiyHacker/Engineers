package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.repair_requests

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.KotlinRepairRequestsApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.RepairRequest

class InsertKotlinRepairRequestOperator(private val kotlinRepairRequestsApi: KotlinRepairRequestsApi) {
    suspend operator fun invoke(repairRequest: RepairRequest): RepairRequest? {
        return kotlinRepairRequestsApi.postRepairRequest(repairRequest = repairRequest)
    }
}