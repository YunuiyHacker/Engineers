package yunuiy_hacker.ryzhaya_tetenka.engineer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.local.shared_prefs.SharedPrefsHelper
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.NavGraph
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph.Route
import yunuiy_hacker.ryzhaya_tetenka.engineer.ui.theme.EngineerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()

            val userId = sharedPrefsHelper.userId
            val masterId = sharedPrefsHelper.masterId

            EngineerTheme {
                NavGraph(
                    navHostController,
                    if (userId == 0) Route.SignInScreen.route else if (masterId != 0) Route.EngineerHomeScreen.route else Route.AdminHomeScreen.route
                )
            }
        }
    }
}