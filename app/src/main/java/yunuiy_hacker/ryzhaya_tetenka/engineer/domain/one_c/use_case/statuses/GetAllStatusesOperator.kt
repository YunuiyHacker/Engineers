package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.statuses

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.StatusesApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.Status

class GetAllStatusesOperator(private val statusesApi: StatusesApi) {
    suspend operator fun invoke(): List<Status> {
        return statusesApi.getAllStatuses()
    }
}