import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

import notifications.NotificationProvider

@Composable
@Preview
fun App(
    notificationProvider: NotificationProvider,
) {
    val snackBarHost = SnackbarHostState()
    val snackBarScope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    notificationProvider.registerNotificationPermissionLauncher(
        onDenied = {
            snackBarScope.launch {
                snackBarHost.showSnackbar("You did not allow notifications")
            }
        },
        onAllowed = {
            snackBarScope.launch {

                snackBarHost.showSnackbar("Great notifications are not allowed")
            }
        },
    )
    LaunchedEffect(
        Unit,
    ) {
        notificationProvider.requestPermission()
    }


    MaterialTheme {

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    snackBarHost
                )
            },
        ) { padding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .padding(padding)
                    .fillMaxSize()
            ) {

                Text(
                    "Local Notifications KMP",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title,
                    label = { Text("Title") },

                    onValueChange = { title = it }
                )

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = body,
                    label = { Text("Body") },

                    onValueChange = { body = it }
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                Button(
                    enabled = title.isNotEmpty() && body.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        notificationProvider.notificationsPermissionGranted { granted ->
                            if (granted) {
                                notificationProvider.show(
                                    title,
                                    body
                                )
                            } else {
                                snackBarScope.launch {
                                    snackBarHost.showSnackbar("Notifications are not allowed , please enable it  from settings")
                                }
                            }
                        }

                    },
                ) {
                    Text("Show")
                }
            }
        }

    }
}