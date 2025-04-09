package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi

class DeleteUserOperatorByIdOperator(private val usersApi: UsersApi) {
    suspend operator fun invoke(id: Int) {
        usersApi.deleteUserById(userId = id)
    }
}