package dev.robert.spacexgo.features.ships.presentation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dev.robert.spacexgo.features.ships.domain.model.Ship
import org.junit.Rule
import org.junit.Test


class ShipsScreenTest {
    /**
     * 1. Loading
     * 2. Error
     * 3. When the list is empty
     * 4. When successful
     *
     */

    @get:Rule
    val composeTestRule = createComposeRule()


    //Expectedbehavior_When_StateUnderTest
    @Test
    fun showShipLoadingStateComponent_When_Loading() {
        //Given / Arrange
        val state = ShipsState(
            isLoading = true,
            error = null,
            ships = emptyList()
        )

        // When / Act
        composeTestRule.setContent {
            ShipScreenContent(scaffoldState = rememberScaffoldState(), state = state)
        }

        //Then / Assert
        composeTestRule.onNodeWithTag("ShipsLoadingStateComponent").assertExists()
        composeTestRule.onNodeWithTag("ShipsEmptyStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipErrorStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipsSuccessStateComponent").assertDoesNotExist()

    }
    //Expectedbehavior_When_StateUnderTest
    @Test
    fun showShipsEmptyStateComponent_When_Ships_State_is_Empty(){
        // given / arrange
        val state = ShipsState(
            isLoading = false,
            error = null,
            ships = emptyList()
        )
        // when / act

        composeTestRule.setContent {
            ShipScreenContent(scaffoldState = rememberScaffoldState(), state = state)
        }

        //then / assert
        composeTestRule.onNodeWithTag("ShipsEmptyStateComponent").assertExists()
        composeTestRule.onNodeWithTag("ShipsLoadingStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipErrorStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipsSuccessStateComponent").assertDoesNotExist()
    }

    @Test
    fun showShipsErrorStateComponent_When_Ships_State_Contains_Error(){
        // given / arrange
        val state = ShipsState(
            isLoading = false,
            error = "An error occurred",
            ships = emptyList()
        )
        // when / act

        composeTestRule.setContent {
            ShipScreenContent(scaffoldState = rememberScaffoldState(), state = state)
        }

        //then / assert
        composeTestRule.onNodeWithTag("ShipsEmptyStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipsLoadingStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipErrorStateComponent").assertExists()
        composeTestRule.onNodeWithTag("ShipsSuccessStateComponent").assertDoesNotExist()
    }

    @Test
    fun showShipsSuccessStateComponent_When_Ships_State_Has_Data(){
        // given / arrange
        val state = ShipsState(
            isLoading = false,
            error = null,
            ships = testData,
        )
        // when / act

        composeTestRule.setContent {
            ShipScreenContent(scaffoldState = rememberScaffoldState(), state = state)
        }

        //then / assert
        composeTestRule.onNodeWithTag("ShipsEmptyStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipsLoadingStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipErrorStateComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("ShipsSuccessStateComponent").assertExists()
    }

}


private val testData = listOf(
    Ship(
        active = true,
        id = "123",
        image = "ship1.jpg",
        lastAisUpdate = "2022-01-01T12:00:00",
        launches = listOf("Launch 1", "Launch 2", null),
        massKg = 5000,
        model = "Model A",
        name = "Ship 1",
        roles = listOf("Transport", "Freight", null),
        speedKn = "20",
        status = "Active",
        type = "Cargo",
        yearBuilt = 2000
    )
)