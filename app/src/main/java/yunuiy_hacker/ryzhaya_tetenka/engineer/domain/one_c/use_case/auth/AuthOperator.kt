package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.use_case.auth

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.AuthApi
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.AuthData
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.User

class AuthOperator(private val authApi: AuthApi) {
    suspend operator fun invoke(authData: AuthData): User {
        return authApi.auth(user = authData.user ?: "", password = authData.password ?: "")
    }
}