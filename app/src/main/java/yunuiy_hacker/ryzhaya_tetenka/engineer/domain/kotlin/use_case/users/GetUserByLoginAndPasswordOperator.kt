package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.users

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.UsersApi

class GetUserByLoginAndPasswordOperator(private val usersApi: UsersApi) {
    suspend operator fun invoke(login: String, password: String): User? {
        return usersApi.getUserByLoginAndPassword(login = login, password = password)
    }
}