package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.ApplicationStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.ApplicationStatusesApi

class InsertApplicationStatusOperator(private val applicationStatusesApi: ApplicationStatusesApi) {
    suspend operator fun invoke(applicationStatus: ApplicationStatus): ApplicationStatus? {
        return applicationStatusesApi.postApplicationStatus(applicationStatus = applicationStatus)
    }
}