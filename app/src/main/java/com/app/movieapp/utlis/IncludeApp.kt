package com.app.movieapp.utlis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.app.movieapp.R

class IncludeApp {

    @Composable
    fun showExitDialog(onDismiss: () -> Unit) {

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Exit App?") },
            text = { Text(text = "Are you sure you want to exit the app?") },
            confirmButton = {
                TextButton(onClick = {

                    // Exit the app (optional)
                     android.os.Process.killProcess(android.os.Process.myPid())
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "No")
                }
            }
        )

    }
    @ExperimentalMaterial3Api
    @ExperimentalLayoutApi
    @Composable
    fun showAbout(onDismiss: () -> Unit) {
        val mContext = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            icon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_star),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            },
            title = { Text(text = "ComposeX") },
            text = {
                Text(stringResource(R.string.app_name))
            },
            confirmButton = {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    Tools.openLink(mContext, "https://codecanyon.net/user/dream_space/portfolio")
                }
                ) {
                    Text("PURCHASE NOW")
                }

            }
        )
    }

    @Composable
    fun emptyState() {
        Column(
            modifier = Modifier.width(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                Spacer(Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                Spacer(Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                }
            }
        }
    }
}
@Composable
fun MyAppBar(title: String, onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        color = Color(0xFF001945),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back") // Use Material Icons
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
    }
}