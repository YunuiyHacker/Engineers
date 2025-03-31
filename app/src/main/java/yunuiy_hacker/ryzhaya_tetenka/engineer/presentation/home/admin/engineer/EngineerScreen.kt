package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LoadingIndicatorDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.MessageDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotAvailableInternet
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.NotConnectionToServers
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EngineerScreen(
    navHostController: NavHostController, viewModel: EngineerViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val outlinedTextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        disabledTextColor = MaterialTheme.colorScheme.onSurface
    )

    val isEditMode by remember { mutableStateOf(viewModel.state.user.id != 0) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(EngineerEvent.LoadDataEvent)
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
                            text = stringResource(R.string.master),
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(EngineerEvent.OnClickButtonEvent)
                    },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isEditMode) stringResource(R.string.save_changes) else stringResource(
                            R.string.add_master
                        ),
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }) {
            if (state.contentState.hasConnectionToServers.value && state.contentState.internetIsNotAvailable.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it)
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.surname,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangeSurnameEvent(it.filter { letter -> letter.isLetter() }))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.surname),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.surname),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangeNameEvent(it.filter { letter -> letter.isLetter() }))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.name),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.name),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.lastname,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangeLastnameEvent(it.filter { letter -> letter.isLetter() }))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.lastname),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.lastname),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.login,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangeLoginEvent(it.filter { letter -> letter.isLetter() }))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.login),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.login),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangePasswordEvent(it))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.password),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.password),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.passwordConfirm,
                        onValueChange = {
                            viewModel.onEvent(EngineerEvent.ChangePasswordConfirmEvent(it))
                        },
                        colors = outlinedTextFieldColors,
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.password_confirm),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.password_confirm),
                                style = TextStyle(fontStyle = FontStyle.Normal),
                                textAlign = TextAlign.Left
                            )
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Left),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.employee),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable {
                                viewModel.onEvent(EngineerEvent.ShowEmployeePickerMenuEvent)
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            text = if (state.selectedEmployee.title.isNotEmpty()) state.selectedEmployee.title else stringResource(
                                R.string.not_selected
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = if (!state.showEmployeePickerMenu) Icons.Rounded.ArrowDropDown else Icons.Rounded.ArrowDropUp,
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
                                ), expanded = state.showEmployeePickerMenu, onDismissRequest = {
                                viewModel.onEvent(EngineerEvent.HideEmployeePickerMenuEvent)
                            }) {
                            state.employees.forEach { employee ->
                                DropdownMenuItem(text = {
                                    Column {
                                        Text(
                                            text = employee.title,
                                            fontWeight = FontWeight.Normal,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (employee.inn.isNotEmpty()) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = employee.inn,
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }, onClick = {
                                    viewModel.onEvent(
                                        EngineerEvent.SelectEmployeePickerMenuEvent(
                                            employee
                                        )
                                    )
                                })
                            }
                        }
                    }
                }
            }
            if (!state.contentState.internetIsNotAvailable.value) {
                NotAvailableInternet(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                    viewModel.onEvent(EngineerEvent.LoadDataEvent)
                })
            }
            if (!state.contentState.hasConnectionToServers.value) {
                NotConnectionToServers(modifier = Modifier.fillMaxSize(), requestTryAgain = {
                    viewModel.onEvent(EngineerEvent.LoadDataEvent)
                })
            }
        }

        if (state.showMessageDialog) {
            MessageDialog(message = state.message, onDismissRequest = {
                viewModel.onEvent(
                    EngineerEvent.HideMessageDialog
                )
            })
        }

        if (state.contentState.isLoading.value) {
            LoadingIndicatorDialog { }
        }

        LaunchedEffect(state.success) {
            if (state.success == true)
                navHostController.popBackStack(
                    route = Route.AdminHomeScreen.route,
                    inclusive = false,
                    saveState = false
                )
        }
    }
}