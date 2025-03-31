package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.RepairRequest
import yunuiy_hacker.ryzhaya_tetenka.engineer.data.common.model.User
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.mappers.toDomain
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.auth.sign_in.SignInScreen
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.AdminHomeScreen
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer.EngineerScreen
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.engineer.EngineerViewModel
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.EngineerHomeScreen
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.repair_request.RepairRequestScreen
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.engineer.repair_request.RepairRequestViewModel
import yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.profile.ProfileScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(navHostController: NavHostController, startDestination: String) {
    NavHost(navHostController, startDestination) {
        composable(route = Route.SignInScreen.route, enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }) {
            SignInScreen(navHostController)
        }
        composable(route = Route.EngineerHomeScreen.route) {
            EngineerHomeScreen(navHostController)
        }
        composable(route = Route.AdminHomeScreen.route) {
            AdminHomeScreen(navHostController)
        }
        composable(route = Route.ProfileScreen.route, enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }) {
            ProfileScreen(navHostController)
        }
        composable(
            route = "${Route.RepairRequestScreen.route}/{repair_request}",
            arguments = listOf(navArgument("repair_request") {
                NavType.StringType
                nullable = false
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            }) {
            it.arguments?.getString("repair_request")?.let { json ->
                var repairRequest = Gson().fromJson(
                    URLDecoder.decode(json, StandardCharsets.UTF_8), RepairRequest::class.java
                ).toDomain()

                val viewModel: RepairRequestViewModel = hiltViewModel()
                viewModel.state.repairRequest = repairRequest
                RepairRequestScreen(navHostController, viewModel = viewModel)
            }
        }
        composable(
            route = "${Route.EngineerScreen.route}/{user}",
            arguments = listOf(navArgument("user") {
                NavType.StringType
                nullable = false
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            }) {
            it.arguments?.getString("user")?.let { json ->
                var user = Gson().fromJson(
                    URLDecoder.decode(json, StandardCharsets.UTF_8), User::class.java
                ).toDomain()

                val viewModel: EngineerViewModel = hiltViewModel()
                viewModel.state.user = user
                EngineerScreen(navHostController, viewModel = viewModel)
            }
        }
        composable(route = Route.EngineerScreen.route, enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }, popEnterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            )
        }) {
            EngineerScreen(navHostController)
        }
    }
}