package com.example.streesakha.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.streesakha.R
import com.example.streesakha.ui.theme.StreeSakhaTheme

@Composable
fun NotificationDialog(
    messageText: String,
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var newMessageText by remember { mutableStateOf(messageText) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.period_notification_title))
        },
        text = {
            TextField(
                value = newMessageText,
                onValueChange = { newMessageText = it },
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(newMessageText)
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(id = R.string.save_button))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel_button))
            }
        }
    )
}

@Preview
@Composable
private fun NotificationDialogPreview() {
    StreeSakhaTheme {
        NotificationDialog(
            messageText = "Example message",
            onDismissRequest = {},
            onSave = {},
        )
    }
}
