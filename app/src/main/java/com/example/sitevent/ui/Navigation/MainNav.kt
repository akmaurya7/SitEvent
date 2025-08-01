package com.example.sitevent.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sitevent.data.Resource
import com.example.sitevent.ui.screen.Auth.LoginScreen
import com.example.sitevent.ui.screen.Auth.SignUpScreen
import com.example.sitevent.ui.screen.Auth.VerifyEmailScreen
import com.example.sitevent.ui.screen.ChatScreen
import com.example.sitevent.ui.screen.Club.AllClubScreen
import com.example.sitevent.ui.screen.Club.ClubDetailScreen
import com.example.sitevent.ui.screen.Club.ClubSettingScreen
import com.example.sitevent.ui.screen.Club.CreateClubScreen
import com.example.sitevent.ui.screen.Club.EditClubDetailScreen
import com.example.sitevent.ui.screen.Club.Event.CreateEventScreen
import com.example.sitevent.ui.screen.Club.Event.EventDetailScreen
import com.example.sitevent.ui.screen.Club.Event.EventRegistrationScreen
import com.example.sitevent.ui.screen.Club.ManageClubRolesScreen
import com.example.sitevent.ui.screen.ClubCategory.AllCategoryScreen
import com.example.sitevent.ui.screen.ClubCategory.CreateCategoryScreen
import com.example.sitevent.ui.screen.ClubCategory.SingleCategoryScreen
import com.example.sitevent.ui.screen.HomeScreen
import com.example.sitevent.ui.screen.ProfileScreen
import com.example.sitevent.ui.screen.User.UserTicketDetailedScreen
import com.example.sitevent.ui.screen.User.UserTicketScreen
import com.example.sitevent.ui.viewModel.AuthViewModel
import com.example.sitevent.ui.viewModel.UserViewModel


enum class Screen {
    LOGIN,
    SIGNUP,
    VERIFY,

    CREATE_CATEGORY_SCREEN,
    ALL_CATEGORY_SCREEN,
    SINGLE_CATEGORY_SCREEN,

    CREATE_EVENT_SCREEN,
    EVENT_DETAIL_SCREEN,
    EVENT_REGISTRATION_SCREEN,
    //bottom bar
    HOME,
    All_CLUBS_SCREEN,
    CLUB_DETAIL_SCREEN,
    CREATE_CLUB_SCREEN,
    EDIT_CLUB_SCREEN,
    CLUB_SETTING_SCREEN,
    MANAGE_CLUB_ROLES,
    CHATS,
    PROFILE,

    //User
    User_TICKET_SCREEN,
    User_EVENT_SCREEN,
    User_CLUB_SCREEN,
    EDIT_PROFILE_SCREEN,
    USER_TICKET_DETAIL_SCREEN,

}

sealed class NavigationItem(val route: String) {
    object Login : NavigationItem(Screen.LOGIN.name)
    object SignUp : NavigationItem(Screen.SIGNUP.name)
    object Verify : NavigationItem(Screen.VERIFY.name)
    object Home : NavigationItem(Screen.HOME.name)

    object CreateCategory : NavigationItem(Screen.CREATE_CATEGORY_SCREEN.name)
    object AllCategory : NavigationItem(Screen.ALL_CATEGORY_SCREEN.name)
    object SingleCategory : NavigationItem("${Screen.SINGLE_CATEGORY_SCREEN.name}/{categoryId}") {
        fun createRoute(categoryId: String) = "${Screen.SINGLE_CATEGORY_SCREEN.name}/$categoryId"
    }


    object CreateClub : NavigationItem("${Screen.CREATE_CLUB_SCREEN.name}/{categoryId}") {
        fun createRoute(categoryId: String) = "${Screen.CREATE_CLUB_SCREEN.name}/$categoryId"
    }

    object CreateEvent :
        NavigationItem("${Screen.CREATE_EVENT_SCREEN.name}/{categoryId}/{clubId}") {
        fun createRoute(categoryId: String, clubId: String) =
            "${Screen.CREATE_EVENT_SCREEN.name}/$categoryId/$clubId"
    }

    object EventDetail : NavigationItem(
        route = "${Screen.EVENT_DETAIL_SCREEN.name}/{categoryId}/{clubId}/{eventId}"
    ) {
        // pass all three into the generated path
        fun createRoute(categoryId: String, clubId: String, eventId: String) =
            "${Screen.EVENT_DETAIL_SCREEN.name}/$categoryId/$clubId/$eventId"
    }
    object EventRegistration :
        NavigationItem("${Screen.EVENT_REGISTRATION_SCREEN.name}/{categoryId}/{clubId}/{eventId}")
    {
        fun createRoute(categoryId: String, clubId: String, eventId: String) =
            "${Screen.EVENT_REGISTRATION_SCREEN.name}/$categoryId/$clubId/$eventId"
    }


    object AllClubs : NavigationItem(Screen.All_CLUBS_SCREEN.name)
    object ClubDetail : NavigationItem("${Screen.CLUB_DETAIL_SCREEN.name}/{categoryId}/{clubId}") {
        fun createRoute(categoryId: String, clubId: String) =
            "${Screen.CLUB_DETAIL_SCREEN.name}/$categoryId/$clubId"
    }

    object ClubSetting : NavigationItem("${Screen.CLUB_SETTING_SCREEN.name}/{categoryId}/{clubId}")
    {
        fun createRoute(categoryId: String, clubId: String) =
            "${Screen.CLUB_SETTING_SCREEN.name}/$categoryId/$clubId"
    }
    object EditClub : NavigationItem("${Screen.EDIT_CLUB_SCREEN.name}/{categoryId}/{clubId}")
    {
        fun createRoute(categoryId: String, clubId: String) =
            "${Screen.EDIT_CLUB_SCREEN.name}/$categoryId/$clubId"
    }
    object ManageClubRoles : NavigationItem("${Screen.MANAGE_CLUB_ROLES.name}/{categoryId}/{clubId}")
    {
        fun createRoute(categoryId: String, clubId: String) =
            "${Screen.MANAGE_CLUB_ROLES.name}/$categoryId/$clubId"
    }

    object Chats : NavigationItem(Screen.CHATS.name)
    object Profile : NavigationItem(Screen.PROFILE.name)


    //User
    object UserTicket : NavigationItem(Screen.User_TICKET_SCREEN.name)
    object UserEvent : NavigationItem(Screen.User_EVENT_SCREEN.name)
    object UserClub : NavigationItem(Screen.User_CLUB_SCREEN.name)
    object EditProfile : NavigationItem(Screen.EDIT_PROFILE_SCREEN.name)
    object UserTicketDetail :
        NavigationItem("${Screen.USER_TICKET_DETAIL_SCREEN.name}/{userId}/{ticketId}") {
        fun createRoute(userId: String, ticketId: String) =
            "${Screen.USER_TICKET_DETAIL_SCREEN.name}/$userId/$ticketId"
    }

}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {

    val currentUserRes by userViewModel.observeUser.collectAsState()
    val role = when (currentUserRes) {
        is Resource.Loading -> "Loading…"
        is Resource.Error -> "Error"
        is Resource.Success -> (currentUserRes as Resource.Success).data?.appRole?.toString()
            ?: "Unknown"

        else -> "…"
    }
    val userId = when (currentUserRes) {
        is Resource.Loading -> ""
        is Resource.Error -> ""
        is Resource.Success -> (currentUserRes as Resource.Success).data?.email ?: ""
        else -> ""
    }

    val startDestination = if (authViewModel.signInStatus()) {
        NavigationItem.Home.route
    } else {
        NavigationItem.Login.route
    }

    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.SignUp.route) {
            SignUpScreen(
                navController
            )
        }
        composable(NavigationItem.Verify.route) {
            VerifyEmailScreen(
                navController
            )
        }

        //Bottom bar
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }

        composable(NavigationItem.CreateCategory.route) {
            CreateCategoryScreen(
                navController
            )
        }
        composable(NavigationItem.AllCategory.route) {
            AllCategoryScreen(
                navController,
            )
        }
        composable(
            route = NavigationItem.SingleCategory.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            SingleCategoryScreen(
                categoryId = categoryId,
                navController = navController,
            )
        }

        composable(
            route = NavigationItem.CreateClub.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            CreateClubScreen(
                categoryId = categoryId,
                navController = navController
            )
        }


        composable(NavigationItem.AllClubs.route) {
            AllClubScreen(
                navController,

            )
        }

        composable(
            route = NavigationItem.ClubDetail.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType }
            )
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            val clubId = it.arguments?.getString("clubId") ?: ""
            ClubDetailScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId,
                userId = userId
            )
        }

        composable(
            route = NavigationItem.ClubSetting.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType }
            )
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            val clubId = it.arguments?.getString("clubId") ?: ""
            ClubSettingScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId,
                userId = userId
            )
        }

        composable(
            route = NavigationItem.EditClub.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType }
            )
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            val clubId = it.arguments?.getString("clubId") ?: ""
            EditClubDetailScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId,
            )
        }

        composable(
            route = NavigationItem.ManageClubRoles.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType }
            )
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            val clubId = it.arguments?.getString("clubId") ?: ""
            ManageClubRolesScreen(
                categoryId = categoryId,
                clubId = clubId,
                currentUserId = userId,
                navController
            )
        }

        composable(
            route = NavigationItem.CreateEvent.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType }
            )
        ) {
            val categoryId = it.arguments?.getString("categoryId") ?: ""
            val clubId = it.arguments?.getString("clubId") ?: ""
            CreateEventScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId
            )
        }

        composable(
            route = NavigationItem.EventDetail.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType },
                navArgument("eventId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments!!
            val categoryId = args.getString("categoryId") ?: ""
            val clubId = args.getString("clubId") ?: ""
            val eventId = args.getString("eventId") ?: ""
            EventDetailScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId,
                eventId = eventId
            )
        }

        composable(
            route = NavigationItem.EventRegistration.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("clubId") { type = NavType.StringType },
                navArgument("eventId") { type = NavType.StringType }
            )
        ) {
            val args = it.arguments!!
            val categoryId = args.getString("categoryId") ?: ""
            val clubId = args.getString("clubId") ?: ""
            val eventId = args.getString("eventId") ?: ""
            EventRegistrationScreen(
                navController = navController,
                categoryId = categoryId,
                clubId = clubId,
                eventId = eventId,
                userId = userId
            )
        }
        composable(NavigationItem.Chats.route) {
            ChatScreen(navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController)
        }

        //User
        composable(NavigationItem.UserTicket.route) {
            UserTicketScreen(navController,userId)
        }
        composable(NavigationItem.UserEvent.route) {
            ProfileScreen(navController)
        }
        composable(NavigationItem.UserClub.route) {
            ProfileScreen(navController)
        }
        composable(NavigationItem.EditProfile.route) {
            ProfileScreen(navController)
        }

        composable(
            route = NavigationItem.UserTicketDetail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("ticketId") { type = NavType.StringType }
            )
        ) {
            val userId = it.arguments?.getString("userId") ?: ""
            val ticketId = it.arguments?.getString("ticketId") ?: ""
            UserTicketDetailedScreen(
                userId = userId,
                ticketId = ticketId,
                navController = navController
            )
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val tabs = listOf(
        BottomNavigationItem(
            "Home",
            Icons.Default.Home,
            NavigationItem.Home.route
        ),
        BottomNavigationItem(
            "Clubs",
            Icons.Default.Groups,
            NavigationItem.AllClubs.route
        ),
        BottomNavigationItem(
            "Chats",
            Icons.AutoMirrored.Filled.Chat,
            NavigationItem.Chats.route
        ),
        BottomNavigationItem(
            "Profile",
            Icons.Default.Person,
            NavigationItem.Profile.route
        )
    )


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        tabs.forEach { tab ->
            val selected = (tab.route == currentRoute)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.title) },
                label = { Text(tab.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Blue.copy(alpha = 0.12f)
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)

