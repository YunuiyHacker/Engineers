package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.use_case.epmloyees

import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.Employee
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.remote.one_c.EmployeesApi

class GetAllEmployeesOperator(private val employeesApi: EmployeesApi) {
    suspend operator fun invoke(): List<Employee>? {
        return employeesApi.getEmployees()
    }
}