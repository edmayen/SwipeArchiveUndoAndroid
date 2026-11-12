package com.example.reversiblelist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reversiblelist.domain.PaymentNotification
import com.example.reversiblelist.utils.Utils.notificationsSample
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationArchiveScreen() {
    val notifications = rememberSaveable {
        mutableStateListOf<PaymentNotification>().apply { addAll(notificationsSample) }
    }
    val snackBarHostState =  remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var pendingUndo by remember { mutableStateOf(false) }

    fun archiveNotification(notification: PaymentNotification) {
        if (pendingUndo) return
        val index = notifications.indexOfFirst { it.id == notification.id }
        if (index == -1) return

        notifications.removeAt(index)
        pendingUndo = true

        scope.launch {
            try {
                val result = snackBarHostState.showSnackbar(
                    message = "${notification.title} archived",
                    actionLabel = "Undo",
                    withDismissAction = true,
                    duration = SnackbarDuration.Long
                )
                if (
                    result == SnackbarResult.ActionPerformed &&
                    notifications.none { it.id == notification.id }
                ) {
                    val targetIndex = index.coerceIn(0, notifications.size)
                    notifications.add(targetIndex, notification)
                }
            } finally {
                pendingUndo = false
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(title = { Text(text = "Notification Archive") })
        }
    ) { paddingValues ->
        Column(
            Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Text(
                "Swipe left to archive, or use the Archive button. " +
                        "Undo is available in the snackbar.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications, key = { it.id}) { notification ->
                    SwipeToArchiveListItem(
                        notification = notification,
                        gestureEnabled = !pendingUndo,
                        onArchive = { archiveNotification(notification) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToArchiveListItem(
    notification: PaymentNotification,
    gestureEnabled: Boolean,
    onArchive: () -> Unit
) {
    val density = LocalDensity.current
    val dismissState = remember(notification.id) {
        SwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            density = density,
            confirmValueChange = { newValue ->
                newValue == SwipeToDismissBoxValue.EndToStart &&
                        gestureEnabled
            },
            positionalThreshold = { distance -> distance * 0.5f }
        )
    }

    LaunchedEffect(dismissState.currentValue) {
        if (
            dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
        ) {
            onArchive()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        gesturesEnabled = gestureEnabled,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Archive",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    ) {
        ListItem(
            headlineContent = { Text(notification.title) },
            supportingContent = { Text(notification.message) },
            trailingContent = {
                TextButton(
                    onClick = onArchive,
                    enabled = gestureEnabled
                ) {
                    Text("Archive")
                }
            }
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
private fun LightPreview() {
    MaterialTheme { NotificationArchiveScreen() }
}

@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DarkPreview() {
    MaterialTheme { NotificationArchiveScreen() }
}