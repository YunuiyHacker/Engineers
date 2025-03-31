package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile

import android.R.attr.fontWeight
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.QuestionDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvent.LoadDataEvent)
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
                            text = stringResource(R.string.profile),
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
        }, bottomBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                viewModel.onEvent(
                                    ProfileEvent.ShowQuestionDialog(
                                        title = context.getString(
                                            R.string.exit_from_account
                                        ),
                                        text = context.getString(R.string.you_really_want_to_exit_from_account)
                                    )
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ExitToApp,
                                contentDescription = null,
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.exit_to_account),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }) {
            Column(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = if (state.masterId != 0) stringResource(R.string.master) else stringResource(
                            R.string.admin
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.surname),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = if (state.surname.isNotEmpty()) state.surname else stringResource(
                                R.string.not_filled
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.name),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = if (state.name.isNotEmpty()) state.name else stringResource(R.string.not_filled),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.lastname),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = if (state.lastname.isNotEmpty()) state.lastname else stringResource(
                                R.string.not_filled
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        if (state.showQuestionDialog) {
            QuestionDialog(
                title = state.title,
                text = state.text,
                onConfirmRequest = {
                    viewModel.onEvent(ProfileEvent.ExitToAccountEvent)
                },
                onDismissRequest = {
                    viewModel.onEvent(ProfileEvent.HideQuestionDialog)
                }
            )
        }

        LaunchedEffect(state.success) {
            if (state.success == true)
                navHostController.navigate(Route.SignInScreen.route)
        }
    }

    BackHandler {
        navHostController.popBackStack()
    }
}