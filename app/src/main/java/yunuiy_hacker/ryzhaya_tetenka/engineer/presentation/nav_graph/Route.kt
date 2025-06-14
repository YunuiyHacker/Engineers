package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph

sealed class Route(val route: String) {
    data object SignInScreen : Route(route = "signInScreen")
    data object EngineerHomeScreen : Route(route = "engineerHomeScreen")
    data object ProfileScreen : Route(route = "profileScreen")
    data object RepairRequestScreen : Route(route = "repairRequestScreen")
}