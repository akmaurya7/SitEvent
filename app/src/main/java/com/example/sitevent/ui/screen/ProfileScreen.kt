package com.example.sitevent.ui.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sitevent.data.Resource
import com.example.sitevent.ui.Navigation.NavigationItem
import com.example.sitevent.ui.viewModel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var theme by remember { mutableStateOf("Light") }
    var notificationsEnabled by remember { mutableStateOf(true) }

    val signOutState by authViewModel.signoutState.collectAsState()
    LaunchedEffect(signOutState) {
        if (signOutState is Resource.Success) {
            navController.navigate(NavigationItem.Login.route) {
                popUpTo(NavigationItem.Profile.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }


    BottomBarScaffold(navController) {padding->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
        {
            // Title
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )

            // ACCOUNT
            SectionHeader("Account")
            SectionCard {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    onClick = { /* TODO */ }
                )
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.AirplaneTicket,
                    title = "My Tickets",
                    onClick = { navController.navigate(NavigationItem.UserTicket.route)}
                )
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Change Password",
                    onClick = { /* TODO */ }
                )
                SettingsItem(
                    icon = Icons.Default.PrivacyTip,
                    title = "Privacy Policy",
                    onClick = { /* TODO */ }
                )
            }

            Spacer(Modifier.height(24.dp))

            // PREFERENCES
            SectionHeader("Preferences")
            SectionCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Brightness6,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Theme", modifier = Modifier.weight(1f))
                    listOf("Light", "Dark", "System").forEach { option ->
                        val selected = theme == option
                        TextButton(
                            onClick = { theme = option },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (selected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .background(
                                    if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                    else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                        ) {
                            Text(option)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Notifications", modifier = Modifier.weight(1f))
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // SUPPORT
            SectionHeader("Support")
            SectionCard {
                SettingsItem(
                    icon = Icons.Default.Help,
                    title = "Help Center",
                    onClick = { /* TODO */ }
                )
                SettingsItem(
                    icon = Icons.Default.BugReport,
                    title = "Report a Bug",
                    onClick = { /* TODO */ }
                )
            }

            Spacer(Modifier.height(24.dp))

            // DANGER ZONE
            SectionHeader("Danger Zone", color = MaterialTheme.colorScheme.error)
            SectionCard(
                borderColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.error
            ) {
                OutlinedButton(
                    onClick = { authViewModel.signOut() },
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.5.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sign Out", fontWeight = FontWeight.SemiBold)
                }
                TextButton(
                    onClick = { /* delete account */ },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete Account")
                }
            }
        }
    }

}

@Composable
fun SectionHeader(text: String, color: Color = MaterialTheme.colorScheme.secondary) {
    Text(
        text.uppercase(),
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
        color = color,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun SectionCard(
    borderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = LocalContentColor.current)
        Spacer(Modifier.width(12.dp))
        Text(
            title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
