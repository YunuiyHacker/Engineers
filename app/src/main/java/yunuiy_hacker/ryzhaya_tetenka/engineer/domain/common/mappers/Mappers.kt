package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.ApplicationStatus
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.ContactPerson
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.Master
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toKotlinDate
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat

fun User.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.User {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.User(
        id = id,
        surname = surname ?: "",
        name = name ?: "",
        lastname = lastname ?: "",
        login = login ?: "",
        password = password ?: "",
        masterId = masterId ?: 0,
        deviceToken = deviceToken ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.User.toData(): User {
    return User(
        id = id,
        surname = surname,
        name = name,
        lastname = lastname,
        login = login,
        password = password,
        masterId = masterId,
        deviceToken = deviceToken
    )
}

fun ApplicationStatus.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.ApplicationStatus {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.ApplicationStatus(
        id = id, title = title ?: "", normalizedTitle = normalizedTitle ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.ApplicationStatus.toData(): ApplicationStatus {
    return ApplicationStatus(id = id, title = title, normalizedTitle = normalizedTitle)
}

fun Employee.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Employee {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Employee(
        title = title ?: "",
        title_clarifying = title_clarifying ?: "",
        inn = inn ?: "",
        date_of_birth = date_of_birth ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Employee.toData(): Employee {
    return Employee(
        title = title, title_clarifying = title_clarifying, inn = inn, date_of_birth = date_of_birth
    )
}

fun RepairRequest.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest(
        number = number,
        date = date?.toKotlinDate(),
        organization_title = organization_title ?: "",
        warehouse = warehouse ?: "",
        manager_name = manager_name ?: "",
        comment = comment ?: "",
        counterparty = counterparty ?: "",
        partner = partner ?: "",
        contact_person = contact_person?.toDomain(),
        nomenclature = nomenclature ?: "",
        malfunction_description = malfunction_description ?: "",
        malfunction = malfunction ?: "",
        completeness = completeness ?: "",
        status = status ?: "",
        under_warranty = under_warranty ?: false,
        series = series ?: "",
        date_of_equipment_issue = date_of_equipment_issue?.toKotlinDate()
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest.toData(): RepairRequest {
    return RepairRequest(
        number = number,
        date = date?.toOneCDateStringFormat(),
        organization_title = organization_title,
        warehouse = warehouse,
        manager_name = manager_name,
        comment = comment,
        counterparty = counterparty,
        partner = partner,
        contact_person = contact_person?.toData(),
        nomenclature = nomenclature,
        malfunction_description = malfunction_description,
        malfunction = malfunction,
        completeness = completeness,
        status = status,
        under_warranty = under_warranty,
        series = series,
        date_of_equipment_issue = date_of_equipment_issue?.toOneCDateStringFormat()
    )
}

fun ContactPerson.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.ContactPerson {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.ContactPerson(
        title = title ?: "",
        business_card_position = business_card_position ?: "",
        phone_number = phone_number ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.ContactPerson.toData(): ContactPerson {
    return ContactPerson(
        title = title,
        business_card_position = business_card_position,
        phone_number = phone_number
    )
}

fun Master.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.Master {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.Master(
        id = id, title = title ?: "", titleClarifying = titleClarifying ?: "", inn = inn ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.Master.toData(): Master {
    return Master(id = id, title = title, titleClarifying = titleClarifying, inn = inn)
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.RepairRequest.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.RepairRequest {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.RepairRequest(
        id = id,
        repairRequestNumber = repairRequestNumber ?: "",
        isViewed = isViewed ?: false,
        userId = userId ?: 0
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.kotlin.model.RepairRequest.toData(): yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.RepairRequest {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.kotlin.model.RepairRequest(
        id = id,
        repairRequestNumber = repairRequestNumber,
        isViewed = isViewed,
        userId = userId
    )
}