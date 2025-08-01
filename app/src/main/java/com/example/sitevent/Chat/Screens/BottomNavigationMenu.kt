package com.example.sitevent.Chat.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Stars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sitevent.Chat.Navigation.DestinationScreen
import com.example.sitevent.Chat.navigateTo

enum class BottomNavigationItem(val icon: ImageVector, val navDestination: DestinationScreen) {
    CHATLIST(Icons.AutoMirrored.Filled.Chat, DestinationScreen.ChatList),
    STATUSLIST(Icons.Rounded.Stars,DestinationScreen.StatusList),
    PROFILE(Icons.Default.AccountCircle,DestinationScreen.Profile)
}

@Composable
fun BottomNavigationMenu(
    selectedItem:BottomNavigationItem,
    navController: NavController
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.White)
    ){

        for(item in BottomNavigationItem.values()){
            Image(imageVector = item.icon, contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController,item.navDestination.route)
                    },
                colorFilter = if(item == selectedItem){
                    ColorFilter.tint(color = Color.Black)
                }else{
                    ColorFilter.tint(color = Color.Gray)
                }
            )
        }

    }
}