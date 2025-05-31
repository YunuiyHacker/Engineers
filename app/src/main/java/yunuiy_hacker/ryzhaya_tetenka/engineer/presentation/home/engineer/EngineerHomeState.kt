package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class EngineerHomeState {
    var login by mutableStateOf("")
    var full_name by mutableStateOf("")

    var allRepairRequests: MutableList<RepairRequest> = mutableStateListOf()
    var repairRequests: MutableList<RepairRequest> = mutableStateListOf()
    var statuses: MutableList<Status> = mutableStateListOf()

    var showMessageDialog by mutableStateOf(false)
    var message by mutableStateOf("")

    var selectedStatus by mutableStateOf(Status())

    var showStatusPickerMenu by mutableStateOf(false)
    var selectStartDate by mutableStateOf(false)
    var startDateInMilliseconds by mutableStateOf(0L)
    var endDateInMilliseconds by mutableStateOf(0L)

    var query by mutableStateOf("")
    var applyStatusFiltering by mutableStateOf(false)
    var applyPeriodFiltering by mutableStateOf(false)

    var showDatePickerDialog by mutableStateOf(false)

    var contentState by mutableStateOf(ContentState())
}