package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.ApplicationStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.ApplicationStatusesApi

class GetAllApplicationStatusesOperator(private val applicationStatusesApi: ApplicationStatusesApi) {
    suspend operator fun invoke(): List<ApplicationStatus>? {
        return applicationStatusesApi.getAllApplicationStatuses()
    }
}