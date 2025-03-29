package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph

sealed class Route(val route: String) {
    data object SignInScreen : Route(route = "signInScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object ProfileScreen : Route(route = "profileScreen")
    data object RepairRequestScreen : Route(route = "repairRequestScreen")
}