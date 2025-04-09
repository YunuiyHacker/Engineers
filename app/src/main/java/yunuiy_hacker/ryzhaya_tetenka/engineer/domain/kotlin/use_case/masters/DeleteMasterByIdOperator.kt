package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.use_case.masters

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.MastersApi

class DeleteMasterByIdOperator(private val mastersApi: MastersApi) {
    suspend operator fun invoke(id: Int) {
        mastersApi.deleteMasterById(masterId = id)
    }
}