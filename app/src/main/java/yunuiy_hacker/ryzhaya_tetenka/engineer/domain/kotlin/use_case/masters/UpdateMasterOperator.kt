package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.MastersApi

class UpdateMasterOperator(private val mastersApi: MastersApi) {
    suspend operator fun invoke(master: Master): Master? {
        return mastersApi.putMaster(master = master)
    }
}