package dev.robert.spacexgo.core.presentation.common

import dev.robert.spacexgo.R
import dev.robert.spacexgo.features.destinations.*

sealed class BottomNavItem(val label: String, val icon: Int, val destination: Destination ){

    data object Rockets: BottomNavItem(
        label = "Rockets",
        icon = R.drawable.ic_rocket,
        destination = RocketsScreenDestination
    )

    data object Upcoming : BottomNavItem(
        label =  "Launches",
        icon = R.drawable.ic_launches,
        destination = LaunchesScreenDestination,
    )

    data object Ships : BottomNavItem(
        label =  "Ships",
        icon = R.drawable.ic_ships,
        destination = ShipsScreenDestination,
    )

    data object Capsules : BottomNavItem(
        label =  "Capsule",
        icon = R.drawable.ic_capsule,
        destination = CapsulesScreenDestination,
    )

    data object Company : BottomNavItem(
        label =  "Company",
        icon = R.drawable.ic_company,
        destination = CompanyScreenDestination
    )

}
