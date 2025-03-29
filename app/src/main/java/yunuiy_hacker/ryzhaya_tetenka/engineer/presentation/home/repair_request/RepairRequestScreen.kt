package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.repair_request

import android.Manifest
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LoadingIndicatorDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.MessageDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.repair_request.composable.ContentRow
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.isInternetAvailable
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.toOneCDateStringFormat

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairRequestScreen(
    navHostController: NavHostController, viewModel: RepairRequestViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    var status by remember(viewModel.state.selectedApplicationStatus) { mutableStateOf(viewModel.state.selectedApplicationStatus) }
    var showContactPersonInfo by remember { mutableStateOf(false) }

    LaunchedEffect(isInternetAvailable(viewModel.application as Context)) {
        viewModel.state.contentState.internetIsNotAvailable.value =
            isInternetAvailable(viewModel.application as Context)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(RepairRequestEvent.LoadDataEvent)
    }

    viewModel.state.let { state ->
        Scaffold(topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 24.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.offset(x = -12.dp),
                            text = stringResource(R.string.repair_request),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                navHostController.popBackStack()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                })
        }, bottomBar = {}) {
            AnimatedVisibility(!state.contentState.isLoading.value) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (state.contentState.internetIsNotAvailable.value) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = stringResource(R.string.number),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(text = state.repairRequest.number.toString())
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.date_and_time),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(text = state.repairRequest.date?.toOneCDateStringFormat()!!)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.organization_title),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.organization_title
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.warehouse),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.warehouse
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.manager),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.manager_name
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.comment),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.comment
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.counterparty),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.counterparty
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.partner),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.partner
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    showContactPersonInfo = !showContactPersonInfo
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.contact_person),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 17.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = if (!showContactPersonInfo) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
                                    contentDescription = null
                                )
                            }
                            AnimatedVisibility(showContactPersonInfo) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.contact_person_title),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 17.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ContentRow(
                                        text = state.repairRequest.contact_person?.title ?: ""
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.contact_person_business_card_position),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 17.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ContentRow(
                                        text = state.repairRequest.contact_person?.business_card_position
                                            ?: ""
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.contact_person_phone_number),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 17.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ContentRow(
                                        text = state.repairRequest.contact_person?.phone_number
                                            ?: ""
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.nomenclature_title),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.nomenclature
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.malfunction),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.malfunction
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.malfunction_description),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.malfunction_description
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.completeness),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.completeness
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.status),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .clickable {
                                            viewModel.onEvent(RepairRequestEvent.ShowStatusPickerMenuEvent)
                                        }, verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 12.dp, vertical = 6.dp),
                                        text = status.normalizedTitle,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Icon(
                                        imageVector = if (!state.showStatusPickerMenu) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
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
                                            viewModel.onEvent(RepairRequestEvent.HideStatusPickerMenuEvent)
                                        }
                                    ) {
                                        state.applicationStatuses.forEach { applicationStatus ->
                                            DropdownMenuItem(text = {
                                                Text(
                                                    text = applicationStatus.normalizedTitle,
                                                    fontWeight = FontWeight.Normal,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }, onClick = {
                                                viewModel.onEvent(
                                                    RepairRequestEvent.SelectStatusPickerMenuEvent(
                                                        applicationStatus
                                                    )
                                                )
                                            })
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.under_warranty),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = stringResource(if (state.repairRequest.under_warranty) R.string.yes else R.string.no)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.series),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.series
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.date_of_equipment_issue),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ContentRow(
                                text = state.repairRequest.date_of_equipment_issue?.toOneCDateStringFormat()!!
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                viewModel.onEvent(RepairRequestEvent.SaveChangesEvent)
                            }, shape = RoundedCornerShape(12.dp)) {
                                Text(
                                    text = stringResource(R.string.record),
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                                Icon(
                                    modifier = Modifier.size(48.dp),
                                    imageVector = Icons.Rounded.WifiOff,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = stringResource(R.string.is_not_available_internet),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            if (state.showMessageDialog) {
                MessageDialog(message = state.message, onDismissRequest = {
                    viewModel.onEvent(RepairRequestEvent.HideMessageDialogEvent)
                })
            }

            if (state.contentState.isLoading.value) {
                LoadingIndicatorDialog {}
            }

            LaunchedEffect(state.success) {
                if (state.success) {
                    navHostController.popBackStack()
                }
            }
        }
    }

    BackHandler {
        navHostController.popBackStack()
    }
}