package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi

class UpdateUserOperator(private val usersApi: UsersApi) {
    suspend operator fun invoke(user: User): User? {
        return usersApi.putUser(user = user)
    }
}