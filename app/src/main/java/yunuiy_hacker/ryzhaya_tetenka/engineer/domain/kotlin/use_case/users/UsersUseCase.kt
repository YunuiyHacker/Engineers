package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users

data class UsersUseCase(
    val getAllUsersOperator: GetAllUsersOperator,
    val getUserByIdOperator: GetUserByIdOperator,
    val getUserByLoginAndPasswordOperator: GetUserByLoginAndPasswordOperator,
    val insertUserOperator: InsertUserOperator,
    val updateUserOperator: UpdateUserOperator,
    val deleteUserOperatorByIdOperator: DeleteUserOperatorByIdOperator
)