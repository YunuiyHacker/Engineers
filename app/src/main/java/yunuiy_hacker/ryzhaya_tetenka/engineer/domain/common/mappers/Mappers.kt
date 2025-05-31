package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.AuthData
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.ContactPerson
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.Status
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toKotlinDate
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat

fun User.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.User {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.User(
        auth_is_success = auth_is_success ?: false,
        login = login ?: "",
        full_name = full_name ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.User.toData(): User {
    return User(
        auth_is_success = auth_is_success,
        login = login,
        full_name = full_name
    )
}

fun Status.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status(
        title = title ?: "", synonym = synonym ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status.toData(): Status {
    return Status(title = title, synonym = synonym)
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

fun AuthData.toDomain(): yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.AuthData {
    return yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.AuthData(
        user = user ?: "",
        password = password ?: ""
    )
}

fun yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.AuthData.toData(): AuthData {
    return AuthData(user = user, password = password)
}