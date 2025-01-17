package com.business.fitrack.ui.composables.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.business.fitrack.R
import com.business.fitrack.ui.composables.RegularButton
import com.business.fitrack.ui.composables.home.Heading
import com.business.fitrack.ui.navigation.Screens
import com.business.fitrack.ui.theme.*
import com.business.fitrack.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    scaffoldState: ScaffoldState
) {

    val state = userViewModel.signInState

    var eMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val googleAuthLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { userViewModel.signInWithGoogle(it) }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it,
                null,
                SnackbarDuration.Short
            )
        }

    }

    Surface(
        color = veryDarkBlue.copy(0.7f),
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.login_background),
                contentScale = ContentScale.Crop,
            )
            .fillMaxSize(),
    ) {
        if (state.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    color = holoGreen,
                    strokeWidth = 5.dp,
                )
            }

        } else
            Column(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .wrapContentSize()
                    .background(Color.Gray.copy(alpha = 0.5f))
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally,
            ) {

                Heading(
                    text = stringResource(R.string.login), modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )

                InputField(
                    eMail,
                    onValueChange = { eMail = it },
                    placeholder = stringResource(R.string.email),
                    icon = Icons.Rounded.Email,
                    type = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(10.dp))
                InputField(
                    input = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.password),
                    icon = Icons.Rounded.Lock,
                    type = KeyboardType.Password,
                    true
                )
                SignUpRow(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screens.Signup.route) }
                )
                RegularButton(
                    Modifier
                        .padding(top = 20.dp)
                        .align(CenterHorizontally),
                    stringResource(R.string.login).lowercase(),
                    onClick = {
                        userViewModel.signInUser(
                            userEmail = eMail,
                            userPassword = password
                        )

                        eMail = ""
                        password = ""

                    }
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Spacer(
                        Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(Color.Black) )
                    Text("Or sign in with:")
                    Spacer(
                        Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(Color.Black) )
                }

                GoogleSignInButton { userViewModel.launchGoogleSignIn(googleAuthLauncher) }
            }
    }
}


@Composable
fun InputField(
    input: String = "",
    onValueChange: (String) -> Unit,
    placeholder: String = "email",
    icon: ImageVector = Icons.Rounded.Email,
    type: KeyboardType = KeyboardType.Email,
    password: Boolean = false
) {
    OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        label = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = type,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (password) PasswordVisualTransformation() else {
            VisualTransformation.None
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignUpRow(modifier: Modifier = Modifier) {
    FlowRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.sign_up_text) + " ",
            fontWeight = FontWeight.Normal,
            color = white,
            fontFamily = outfit
        )
        Text(
            text = stringResource(R.string.sign_up),
            fontWeight = FontWeight.Bold,
            color = holoGreen,
            fontFamily = outfit
        )
    }
}