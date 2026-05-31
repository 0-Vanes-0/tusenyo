package com.example.jetsnack.ui.home.editor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jetsnack.R
import com.example.jetsnack.ui.components.CustomButton
import kotlinx.coroutines.launch

private enum class EditorLayerType {
    Image,
    Text
}

private data class EditorLayer(
    val id: Long,
    val type: EditorLayerType,
    val imageUri: Uri? = null,
    val text: String = "Tu texto",
    val offset: Offset = Offset.Zero,
    val scale: Float = 1f,
    val rotation: Float = 0f,
    val isEditingText: Boolean = false
)

@Composable
fun Editor(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    val layers = remember { mutableStateListOf<EditorLayer>() }
    var nextLayerId by remember { mutableLongStateOf(1L) }
    var selectedLayerId by remember { mutableStateOf<Long?>(null) }

    val initialLayerOffset = remember(density) {
        with(density) {
            Offset(
                x = 0f,
                y = 115.dp.toPx()
            )
        }
    }

    val editorToolsNavController = rememberNavController()
    var selectedClothesType by remember { mutableStateOf("Camiseta") }

    fun updateLayer(id: Long, update: (EditorLayer) -> EditorLayer) {
        val index = layers.indexOfFirst { it.id == id }
        if (index != -1) {
            layers[index] = update(layers[index])
        }
    }

    fun finishTextEditing() {
        focusManager.clearFocus()
        for (index in layers.indices) {
            layers[index] = layers[index].copy(isEditingText = false)
        }
    }

    fun addTextLayer() {
        finishTextEditing()

        val id = nextLayerId++

        layers.add(
            EditorLayer(
                id = id,
                type = EditorLayerType.Text,
                text = "Lorem ipsum",
                offset = initialLayerOffset,
                isEditingText = true
            )
        )

        selectedLayerId = id
    }

    fun deleteSelectedLayer() {
        val selectedId = selectedLayerId ?: return
        layers.removeAll { it.id == selectedId }
        selectedLayerId = null
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        finishTextEditing()

        val id = nextLayerId++

        layers.add(
            EditorLayer(
                id = id,
                type = EditorLayerType.Image,
                imageUri = uri,
                offset = initialLayerOffset
            )
        )

        selectedLayerId = id
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F9))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Design Editor",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(410.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF1500FF))
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(18.dp)
                )
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.tshirt),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
                    .size(360.dp)
            )

            layers.forEach { layer ->
                key(layer.id) {
                    EditorLayerView(
                        layer = layer,
                        isSelected = layer.id == selectedLayerId,
                        onSelect = {
                            selectedLayerId = layer.id
                        },
                        onTransform = { pan, zoom, rotation ->
                            selectedLayerId = layer.id

                            updateLayer(layer.id) { current ->
                                current.copy(
                                    offset = current.offset + pan,
                                    scale = (current.scale * zoom).coerceIn(0.25f, 4f),
                                    rotation = current.rotation + rotation
                                )
                            }
                        },
                        onStartTextEditing = {
                            updateLayer(layer.id) { current ->
                                current.copy(isEditingText = true)
                            }
                        },
                        onTextChanged = { newText ->
                            updateLayer(layer.id) { current ->
                                current.copy(text = newText)
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        EditorToolsPanel(
            navController = editorToolsNavController,
            selectedLayerId = selectedLayerId,
            isTextEditing = layers.any { it.isEditingText },
            selectedClothesType = selectedClothesType,
            onPickImage = {
                imagePickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            onAddTextLayer = { addTextLayer() },
            onDeleteSelectedLayer = { deleteSelectedLayer() },
            onFinishTextEditing = { finishTextEditing() },
            onClothesTypeSelected = { selectedClothesType = it },
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                scope.launch {
                    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                    DesignStorage.saveDesign(context, bitmap)
                }
            }
        ) {
            Text(text = "Guardar diseno")
        }
    }
}

@Composable
private fun BoxScope.EditorLayerView(
    layer: EditorLayer,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onTransform: (pan: Offset, zoom: Float, rotation: Float) -> Unit,
    onStartTextEditing: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    val transformModifier =
        if (layer.isEditingText) {
            Modifier
        } else {
            Modifier.pointerInput(layer.id) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    onTransform(pan, zoom, rotation)
                }
            }
        }

    val selectionModifier =
        if (isSelected) {
            Modifier.border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
        } else {
            Modifier
        }

    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .zIndex(if (isSelected) 1f else 0f)
            .graphicsLayer {
                translationX = layer.offset.x
                translationY = layer.offset.y
                scaleX = layer.scale
                scaleY = layer.scale
                rotationZ = layer.rotation
                transformOrigin = TransformOrigin.Center
            }
            .pointerInput(layer.id, layer.type) {
                detectTapGestures(
                    onTap = {
                        onSelect()

                        if (layer.type == EditorLayerType.Text) {
                            onStartTextEditing()
                        }
                    }
                )
            }
            .then(transformModifier)
            .then(selectionModifier)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        when (layer.type) {
            EditorLayerType.Image -> {
                AsyncImage(
                    model = layer.imageUri,
                    contentDescription = "Custom design image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp)
                )
            }

            EditorLayerType.Text -> {
                if (layer.isEditingText) {
                    EditableDesignText(
                        text = layer.text,
                        onTextChanged = onTextChanged
                    )
                } else {
                    Text(
                        text = layer.text,
                        color = Color.Black,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun EditableDesignText(
    text: String,
    onTextChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 30.sp
        ),
        modifier = Modifier
            .background(
                color = Color.Black.copy(alpha = 0.28f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .widthIn(min = 80.dp)
            .focusRequester(focusRequester)
    )
}
