package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.repair_request

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.Status
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.state.ContentState

class RepairRequestState {
    var repairRequest by mutableStateOf(RepairRequest())
    var statuses: MutableList<Status> = mutableStateListOf()
    var selectedStatus by mutableStateOf(Status())

    var showStatusPickerMenu by mutableStateOf(false)

    var message by mutableStateOf("")
    var showMessageDialog by mutableStateOf(false)

    val contentState by mutableStateOf(ContentState())

    var success by mutableStateOf(false)
}