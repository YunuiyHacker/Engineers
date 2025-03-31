package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toData
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LoadingIndicatorDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.MessageDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotAvailableInternet
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotConnectionToServers
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.QuestionDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.composable.MasterCard
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminHomeScreen(
    navHostController: NavHostController,
    viewModel: AdminHomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val users = remember(viewModel.state.users) { viewModel.state.users }

    LaunchedEffect(Unit) {
        viewModel.onEvent(AdminHomeEvent.LoadDataEvent)
    }

    viewModel.state.let { state ->
        Scaffold(floatingActionButton = {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = {
                    navHostController.navigate(Route.EngineerScreen.route)
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        }, floatingActionButtonPosition = FabPosition.Center) {
            Column(modifier = Modifier.padding(it)) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 40.dp),
                        text = stringResource(R.string.masters_list),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
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
                Spacer(modifier = Modifier.height(24.dp))
                if (!state.contentState.isLoading.value && state.contentState.hasConnectionToServers.value && state.contentState.internetIsNotAvailable.value) {
                    if (users.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                            ) {
                                items(users) { user ->
                                    MasterCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .combinedClickable(onClick = {
                                                navHostController.navigate(
                                                    "${Route.EngineerScreen.route}/${
                                                        URLEncoder.encode(
                                                            viewModel.gson.toJson(user.toData()),
                                                            StandardCharsets.UTF_8
                                                        )
                                                    }"
                                                )
                                            }, onLongClick = {
                                                viewModel.onEvent(
                                                    AdminHomeEvent.ShowQuestionDialogEvent(
                                                        context.getString(R.string.deletion),
                                                        context.getString(R.string.you_really_want_to_delete_master),
                                                        user
                                                    )
                                                )
                                            }), user = user
                                    )

                                    if (users.last() != user) {
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
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                if (!state.contentState.internetIsNotAvailable.value) {
                    NotAvailableInternet(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                        viewModel.onEvent(AdminHomeEvent.LoadDataEvent)
                    })
                }
                if (!state.contentState.hasConnectionToServers.value) {
                    NotConnectionToServers(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                        viewModel.onEvent(AdminHomeEvent.LoadDataEvent)
                    })
                }
            }
        }

        if (state.showMessageDialog) {
            MessageDialog(state.message, onDismissRequest = {
                viewModel.onEvent(AdminHomeEvent.HideMessageDialogEvent)
            })
        }

        if (state.showQuestionDialog) {
            QuestionDialog(
                title = state.questionTitle,
                text = state.questionText,
                onDismissRequest = {
                    viewModel.onEvent(AdminHomeEvent.HideQuestionDialogEvent)
                },
                onConfirmRequest = {
                    viewModel.onEvent(AdminHomeEvent.DeleteMasterEvent)
                })
        }

        if (state.contentState.isLoading.value) {
            LoadingIndicatorDialog {}
        }
    }
}