package com.dayj.dayj



sealed class BottomNavItem(val title: String, val unSelectedIcon: Int, val selectedIcon: Int, val screenRoute: String) {
    data object HOME : BottomNavItem("홈", unSelectedIcon = R.drawable.home_unselected, selectedIcon = R.drawable.home_selected, screenRoute = BottomNavRoute.HOME)
    data object STATISTICS : BottomNavItem("통계", unSelectedIcon = R.drawable.statistics_unselected, selectedIcon = R.drawable.statistics_selected, screenRoute = BottomNavRoute.STATISTICS)
    data object LOUNGE : BottomNavItem("라운지", unSelectedIcon = R.drawable.lounge_unselected, selectedIcon = R.drawable.lounge_selected, screenRoute = BottomNavRoute.LOUNGE)
    data object FRIENDS : BottomNavItem("친구", unSelectedIcon = R.drawable.friends_unselected, selectedIcon = R.drawable.friends_selected, screenRoute = BottomNavRoute.FRIENDS)
    data object MY : BottomNavItem("마이", unSelectedIcon = R.drawable.my_unselected, selectedIcon = R.drawable.my_selected, screenRoute = BottomNavRoute.MY)
}