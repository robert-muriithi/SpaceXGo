package dev.robert.spacexgo.features.ships.presentation


import com.google.common.truth.Truth.assertThat
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.features.ships.domain.repository.ShipsRepository
import dev.robert.spacexgo.features.ships.domain.usecase.GetAllShipsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ShipsViewModelTest {
    /**
     * 1. Test shows correct data when success
     * 2. Test shows error message when there is an error
     */

    val useCase: GetAllShipsUseCase = mockk()
    lateinit var viewModel: ShipsViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ShipsViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun showCorrect_Data_When_Success() = runBlocking {
        //Given / Arrange
        coEvery { useCase() } returns flow { emit(Resource.Success(data = testData)) }

        // When / Act
        viewModel.getAllShips()
        val state = viewModel.shipState.value

        // Then / Assert
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
        assertThat(state.ships).isEqualTo(testData)
    }

    @Test
    fun showCorrectErrorMessage_When_ErrorOccurs() = runBlocking {
        //Given / Arrange
        coEvery { useCase() } returns flow { emit(Resource.Error(message = "Unknown Error Occurred")) }

        // When / Act
        viewModel.getAllShips()
        val state = viewModel.shipState.value

        // Then / Assert
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo("Unknown Error Occurred")
        assertThat(state.ships).isEmpty()
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