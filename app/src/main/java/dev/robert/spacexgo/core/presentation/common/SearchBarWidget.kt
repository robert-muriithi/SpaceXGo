package dev.robert.spacexgo.core.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun SearchBar(
    searchStringState: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    SearchAppBar(
        text = searchStringState,
        onTextChange = onTextChange,
        onSearchClicked = onSearchClicked
    )
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    inputService?.showSoftwareKeyboard()
                } else {
                    inputService?.hideSoftwareKeyboard()
                }
            },

        value = text,
        onValueChange = {
            onTextChange(it)
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked(text)
            }
        ),
        cursorBrush = Brush.linearGradient(
            colors = listOf(
                Color.White,
                Color.White
            )
        ),
    )
}

