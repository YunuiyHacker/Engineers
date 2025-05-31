package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.auth.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.common.io.Files.append
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LinkAnnotationText
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.LoadingIndicatorDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable.MessageDialog
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navHostController: NavHostController, viewModel: SignInViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    viewModel.state.let { state ->
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(R.string.authorization),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.login,
                    onValueChange = {
                        viewModel.onEvent(SignInEvent.ChangeLoginEvent(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.login), fontSize = 16.sp)
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.login)
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(SignInEvent.ChangePasswordEvent(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.password), fontSize = 16.sp)
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.password)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource, indication = null
                            ) {
                                viewModel.onEvent(SignInEvent.TogglePasswordVisibilityEvent)
                            },
                            imageVector = if (!state.passwordVisibility) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (!state.passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            viewModel.onEvent(SignInEvent.ShowForgotPasswordBottomSheetEvent)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                        text = stringResource(R.string.forgot_password),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    val annotatedString = buildAnnotatedString {
                        append(stringResource(R.string.in_auth_your_confirm_with))
                        append(" ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            withLink(
                                link = LinkAnnotation.Clickable(
                                    tag = "TAG",
                                    linkInteractionListener = {
                                        viewModel.onEvent(SignInEvent.ShowPrivacyPolicyBottomSheetEvent)
                                    },
                                ),
                            ) {
                                append(stringResource(R.string.with_privacy_policy))
                            }
                        }
                    }
                    Text(text = annotatedString)
                }
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(SignInEvent.SignInOnClickEvent)
                    },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(12.dp),
                    enabled = state.validFields
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }

        AnimatedVisibility(state.showForgotPasswordBottomSheet) {
            ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
                viewModel.onEvent(SignInEvent.HideForgotPasswordBottomSheetEvent)
            }, containerColor = MaterialTheme.colorScheme.surfaceVariant) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.forgot_password),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.forgot_password_info),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        AnimatedVisibility(state.showPrivacyPolicyBottomSheet) {
            ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
                viewModel.onEvent(SignInEvent.HidePrivacyPolicyBottomSheetEvent)
            }, containerColor = MaterialTheme.colorScheme.surfaceVariant) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp).verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.privacy_policy),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.privacy_policy_info),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        if (state.showMessageDialog) {
            MessageDialog(message = state.messageText, onDismissRequest = {
                viewModel.onEvent(SignInEvent.HideMessageDialogEvent)
            })
        }

        if (state.contentState.isLoading.value) {
            LoadingIndicatorDialog(onDismissRequest = {})
        }

        LaunchedEffect(state.success) {
            if (state.success) navHostController.navigate(Route.EngineerHomeScreen.route)
        }
    }
}