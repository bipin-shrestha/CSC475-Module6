package com.business.fitrack.ui.composables.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fitrack.R
import com.business.fitrack.ui.composables.RegularButton
import com.business.fitrack.ui.composables.home.Heading
import com.business.fitrack.ui.composables.login.InputField
import com.business.fitrack.ui.navigation.Screens
import com.business.fitrack.ui.theme.holoGreen
import com.business.fitrack.ui.theme.veryDarkBlue
import com.business.fitrack.viewmodel.UserViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel,
    scaffoldState: ScaffoldState
) {
    val state = userViewModel.signUpState

    var userName by remember { mutableStateOf("") }
    var eMail by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it,
                null,
                SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(key1 = state.success) {
        if (state.success) navController.navigate(Screens.Login.route)
    }

    Surface(
        color = veryDarkBlue.copy(0.6f),
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.register_background),
                contentScale = ContentScale.Crop,
            )
            .fillMaxSize(),
    ) {
        if (state.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    color = holoGreen.copy(0.6f),
                    strokeWidth = 5.dp,
                )
            }

        } else Column(
            modifier = Modifier.padding(horizontal = 40.dp)
                .wrapContentSize()
                .background(Color.Gray.copy(alpha = 0.5f))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Heading(
                text = stringResource(R.string.signup), modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
            )

            InputField(
                userName,
                { userName = it },
                stringResource(R.string.enter_name_hint),
                Icons.Rounded.Person,
                type = KeyboardType.Text
            )
            InputField(
                eMail,
                { eMail = it },
                stringResource(R.string.enter_email_hint),
                Icons.Rounded.Email,
                type = KeyboardType.Email
            )
            InputField(
                password,
                { password = it },
                stringResource(R.string.enter_password_hint),
                Icons.Rounded.Lock,
                type = KeyboardType.Password,
                true
            )
            InputField(
                confirmPassword,
                { confirmPassword = it },
                stringResource(R.string.confirm_password_hint),
                Icons.Rounded.Lock,
                type = KeyboardType.Password,
                true
            )

            RegularButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                text = stringResource(R.string.continue_text)
            ) {
                userViewModel.signUpUser(
                    userName = userName,
                    userEmail = eMail,
                    userPassword = password,
                    confirmPassword = confirmPassword
                )
            }
        }
    }
}