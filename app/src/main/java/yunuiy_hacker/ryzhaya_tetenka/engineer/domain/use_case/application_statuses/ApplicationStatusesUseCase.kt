package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.application_statuses

data class ApplicationStatusesUseCase(
    val getAllApplicationStatusesOperator: GetAllApplicationStatusesOperator,
    val insertApplicationStatusOperator: InsertApplicationStatusOperator
)
