package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi

class GetUserByIdOperator(private val usersApi: UsersApi) {
    suspend operator fun invoke(id: Int): User? {
        return usersApi.getUserById(userId = id)
    }
}