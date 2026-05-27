/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.ui.home.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnack.R
import com.example.jetsnack.model.Filter
import com.example.jetsnack.model.SearchCategoryCollection
import com.example.jetsnack.model.SearchRepo
import com.example.jetsnack.model.SearchSuggestionGroup
import com.example.jetsnack.model.Snack
import com.example.jetsnack.model.SnackRepo
import com.example.jetsnack.ui.components.CustomButton
import com.example.jetsnack.ui.components.Divider
import com.example.jetsnack.ui.components.SnackCollection
import com.example.jetsnack.ui.components.Surface
import com.example.jetsnack.ui.theme.JetsnackTheme
import com.example.jetsnack.ui.theme.Theme

@Composable
fun Search(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                tint = JetsnackTheme.colors.brand,
                contentDescription = null,
            )
            Divider()
            CustomButton(
                onClick = { /* todo */ },
                shape = RectangleShape,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(id = R.string.cart_checkout),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                )
            }
        }
    }
}