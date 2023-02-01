package dev.robert.spacexgo.features.capsules.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.robert.spacexgo.core.presentation.common.SearchBar
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.presentation.CapsuleItem


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CapsulesSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: CapsuleSearchViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val searchString = viewModel.query.value
    val state = viewModel.capsuleState.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CapsulesSearchAppBar(
                onNavigateUp = { navigator.navigateUp() },
                searchString = searchString,
                viewModel = viewModel
            )
        }
    ) {
        Box(modifier =Modifier.fillMaxSize()){
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if(!state.isLoading && state.capsules.isEmpty() && state.error == null) {
                Text(text = "No capsules found", modifier = Modifier.align(Alignment.Center))
            }
            if(!state.isLoading && state.capsules.isNotEmpty() && state.error == null) {
                CapsulesSearchScreenContent(
                    capsuleList = state.capsules,
                    navigator = navigator
                )
            }
            if(!state.isLoading && state.error == null && state.capsules.isEmpty() && searchString.isEmpty()) {
                Text(text = "Search for a capsule", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun CapsulesSearchScreenContent(capsuleList: List<Capsule>, navigator: DestinationsNavigator) {
    LazyColumn {
        items(capsuleList.size) { capsule ->
            CapsuleItem(
                capsule = capsuleList[capsule],
                index = capsule,
                navigator = navigator,
            )
        }
    }
}


@Composable
fun CapsulesSearchAppBar(
    onNavigateUp: () -> Unit,
    searchString: String,
    viewModel: CapsuleSearchViewModel
) {
    TopAppBar(
        title = {
            SearchBar(
                searchStringState = searchString,
                onTextChange = { viewModel.onQueryChanged(it) },
                onSearchClicked = { viewModel.onSearchClicked(it.trim()) }
            )
        },

        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )

}

/*@Composable
fun CapsulesSearchTextField(
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current
    BasicTextField(
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        value = query,
        onValueChange = {
            query = it
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .focusRequester(focusRequester = focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    inputService?.showSoftwareKeyboard()
                } else {
                    inputService?.hideSoftwareKeyboard()
                }
            },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        inputService?.showSoftwareKeyboard()
        onDispose {}
    }
}*/


