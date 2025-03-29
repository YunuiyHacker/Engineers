package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.masters

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.MastersApi

class GetAllMastersOperator(private val mastersApi: MastersApi) {
    suspend operator fun invoke(): List<Master>? {
        return mastersApi.getAllMasters()
    }
}