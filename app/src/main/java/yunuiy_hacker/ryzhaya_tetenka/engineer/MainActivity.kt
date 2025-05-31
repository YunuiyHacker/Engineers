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

            val login = sharedPrefsHelper.login

            EngineerTheme {
                NavGraph(
                    navHostController,
                    if (login.isNullOrEmpty()) Route.SignInScreen.route else Route.EngineerHomeScreen.route
                )
            }
        }
    }
}