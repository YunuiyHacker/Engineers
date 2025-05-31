package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toData
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.one_c.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.utils.HomeFilteringDateFormat
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.DatePickerDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LoadingIndicatorDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.MessageDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotAvailableInternet
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotConnectionToServers
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.RepairRequestCard
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.SearchBar
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngineerHomeScreen(
    navHostController: NavHostController, viewModel: EngineerHomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    val repairRequests: MutableList<RepairRequest> =
        remember(viewModel.state.repairRequests) { viewModel.state.repairRequests }
    var filteringExpanded by remember { mutableStateOf(false) }
    var datePickerState = rememberDatePickerState()
    var showDatePickerDialog =
        remember(viewModel.state.showDatePickerDialog) { viewModel.state.showDatePickerDialog }
    var searchBarValue by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(EngineerHomeEvent.LoadDataEvent)
    }

    viewModel.state.let { state ->
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        modifier = Modifier.weight(1f),
                        placeholder = stringResource(R.string.enter_nomenclature_title),
                        query = searchBarValue,
                        onQueryChange = {
                            searchBarValue = it.take(50)
                        },
                        onSearch = {
                            viewModel.onEvent(
                                EngineerHomeEvent.SearchRepairRequestsEvent(
                                    searchBarValue
                                )
                            )
                        })
                    Spacer(Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                width = 0.5.dp, color = Color.DarkGray, shape = CircleShape
                            )
                            .clickable {
                                navHostController.navigate(Route.ProfileScreen.route)
                            }) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FilterList,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.filtering),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = -4.dp)
                            .clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                if (state.contentState.internetIsNotAvailable.value && state.contentState.hasConnectionToServers.value && state.repairRequests.isNotEmpty()) filteringExpanded =
                                    !filteringExpanded
                            }) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = if (!filteringExpanded) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                AnimatedVisibility(filteringExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                modifier = Modifier.offset(x = -12.dp),
                                checked = state.applyStatusFiltering,
                                onCheckedChange = {
                                    viewModel.onEvent(EngineerHomeEvent.ToggleStatusApplyingEvent)
                                })
                            Text(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource, indication = null
                                ) {
                                    viewModel.onEvent(EngineerHomeEvent.ToggleStatusApplyingEvent)
                                },
                                text = stringResource(R.string.status),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .animateContentSize()
                                    .clickable {
                                        viewModel.onEvent(EngineerHomeEvent.ShowStatusPickerMenuEvent)
                                    }) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp, vertical = 6.dp
                                    )
                                ) {
                                    Text(
                                        text = state.selectedStatus.title,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            MaterialTheme(
                                colorScheme = MaterialTheme.colorScheme.copy(surface = MaterialTheme.colorScheme.surfaceVariant),
                                shapes = MaterialTheme.shapes.copy(extraSmall = ShapeDefaults.Medium)
                            ) {
                                DropdownMenu(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            width = 0.3.dp,
                                            color = Color.DarkGray,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    expanded = state.showStatusPickerMenu,
                                    onDismissRequest = {
                                        viewModel.onEvent(EngineerHomeEvent.HideStatusPickerMenuEvent)
                                    },
                                    offset = DpOffset(x = -12.dp, y = 0.dp)
                                ) {
                                    state.statuses.forEach { applicationStatus ->
                                        DropdownMenuItem(text = {
                                            Text(
                                                text = applicationStatus.title,
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }, onClick = {
                                            viewModel.onEvent(
                                                EngineerHomeEvent.SelectStatusPickerMenuEvent(
                                                    applicationStatus
                                                )
                                            )
                                        })
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                modifier = Modifier.offset(x = -12.dp),
                                checked = state.applyPeriodFiltering,
                                onCheckedChange = {
                                    viewModel.onEvent(EngineerHomeEvent.TogglePeriodApplyingEvent)
                                })
                            Text(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource, indication = null
                                ) {
                                    viewModel.onEvent(EngineerHomeEvent.TogglePeriodApplyingEvent)
                                },
                                text = stringResource(R.string.period),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .animateContentSize()
                                    .clickable {
                                        viewModel.onEvent(EngineerHomeEvent.ShowStartDatePickerDialogEvent)
                                    }) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp, vertical = 6.dp
                                    )
                                ) {
                                    Text(
                                        text = HomeFilteringDateFormat.format(Date(state.startDateInMilliseconds)),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "-", color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .animateContentSize()
                                    .clickable {
                                        viewModel.onEvent(EngineerHomeEvent.ShowEndDatePickerDialogEvent)
                                    }) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp, vertical = 6.dp
                                    )
                                ) {
                                    Text(
                                        text = HomeFilteringDateFormat.format(Date(state.endDateInMilliseconds)),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
                if (!state.contentState.isLoading.value && state.contentState.hasConnectionToServers.value && state.contentState.internetIsNotAvailable.value) {
                    if (repairRequests.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                items(repairRequests) { repairRequest ->
                                    RepairRequestCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .clickable {
                                                navHostController.navigate(
                                                    "${Route.RepairRequestScreen.route}/${
                                                        URLEncoder.encode(
                                                            viewModel.gson.toJson(repairRequest.toData()),
                                                            StandardCharsets.UTF_8
                                                        )
                                                    }"
                                                )
                                            }, repairRequest = repairRequest
                                    )

                                    if (repairRequests.last() != repairRequest) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                    } else {
                                        Spacer(modifier = Modifier.height(24.dp))
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(60.dp),
                                imageVector = Icons.Rounded.List,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = stringResource(R.string.list_is_empty),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                if (!state.contentState.internetIsNotAvailable.value) {
                    NotAvailableInternet(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                        viewModel.onEvent(EngineerHomeEvent.LoadDataEvent)
                    })
                }
                if (!state.contentState.hasConnectionToServers.value) {
                    NotConnectionToServers(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                        viewModel.onEvent(EngineerHomeEvent.LoadDataEvent)
                    })
                }
            }
        }

        if (state.showMessageDialog) {
            MessageDialog(state.message, onDismissRequest = {
                viewModel.onEvent(EngineerHomeEvent.HideMessageDialogEvent)
            })
        }

        if (state.contentState.isLoading.value) {
            LoadingIndicatorDialog {}
        }

        if (showDatePickerDialog) {
            DatePickerDialog(
                modifier = Modifier.heightIn(max = 700.dp),
                onDismissRequest = {
                    viewModel.onEvent(EngineerHomeEvent.HideDatePickerDialogEvent)
                },
                confirmButton = {},
                colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    DatePicker(state = datePickerState)
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = {
                            val dateInMilliseconds = datePickerState.selectedDateMillis ?: 0

                            if (state.selectStartDate) viewModel.onEvent(
                                EngineerHomeEvent.SelectStartDateEvent(
                                    dateInMilliseconds
                                )
                            )
                            else viewModel.onEvent(
                                EngineerHomeEvent.SelectEndDateEvent(
                                    dateInMilliseconds
                                )
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                    ) {
                        Text(
                            text = stringResource(R.string.select),
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}