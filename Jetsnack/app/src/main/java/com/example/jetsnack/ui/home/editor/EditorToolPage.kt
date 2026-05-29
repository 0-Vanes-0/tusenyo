package com.example.jetsnack.ui.home.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetsnack.ui.components.CustomButton
import com.example.jetsnack.ui.theme.JetsnackTheme

enum class EditorToolPage(
    val route: String,
    val title: String
) {
    ImagesAndTexts(
        route = "editor/images-and-texts",
        title = "Imágenes"
    ),
    ClothesType(
        route = "editor/clothes-type",
        title = "Tipo de prenda"
    )
}

@Composable
fun EditorToolsPanel(
    navController: NavHostController,
    selectedLayerId: Long?,
    isTextEditing: Boolean,
    selectedClothesType: String,
    onPickImage: () -> Unit,
    onAddTextLayer: () -> Unit,
    onDeleteSelectedLayer: () -> Unit,
    onFinishTextEditing: () -> Unit,
    onClothesTypeSelected: (String) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
        ?: EditorToolPage.ImagesAndTexts.route

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EditorToolTabs(
            currentRoute = currentRoute,
            onNavigate = { page ->
                if (currentRoute != page.route) {
                    navController.navigate(page.route) {
                        launchSingleTop = true
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        NavHost(
            navController = navController,
            startDestination = EditorToolPage.ImagesAndTexts.route,
            modifier = Modifier.fillMaxWidth()
        ) {
            addEditorToolsGraph(
                selectedLayerId = selectedLayerId,
                isTextEditing = isTextEditing,
                selectedClothesType = selectedClothesType,
                onPickImage = onPickImage,
                onAddTextLayer = onAddTextLayer,
                onDeleteSelectedLayer = onDeleteSelectedLayer,
                onFinishTextEditing = onFinishTextEditing,
                onClothesTypeSelected = onClothesTypeSelected
            )
        }
    }
}

@Composable
fun EditorToolTabs(
    currentRoute: String,
    onNavigate: (EditorToolPage) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        EditorToolPage.entries.forEach { page ->
            val selected = currentRoute == page.route

            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(page) },
                backgroundGradient = if (selected) {
                    JetsnackTheme.colors.enabled
                } else {
                    JetsnackTheme.colors.disabled
                }
            ) {
                Text(
                    text = page.title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun NavGraphBuilder.addEditorToolsGraph(
    selectedLayerId: Long?,
    isTextEditing: Boolean,
    selectedClothesType: String,
    onPickImage: () -> Unit,
    onAddTextLayer: () -> Unit,
    onDeleteSelectedLayer: () -> Unit,
    onFinishTextEditing: () -> Unit,
    onClothesTypeSelected: (String) -> Unit
) {
    composable(EditorToolPage.ImagesAndTexts.route) {
        ImagesAndTexts(
            selectedLayerId = selectedLayerId,
            isTextEditing = isTextEditing,
            onPickImage = onPickImage,
            onAddTextLayer = onAddTextLayer,
            onDeleteSelectedLayer = onDeleteSelectedLayer,
            onFinishTextEditing = onFinishTextEditing
        )
    }

    composable(EditorToolPage.ClothesType.route) {
        ClothesType(
            selectedClothesType = selectedClothesType,
            onClothesTypeSelected = onClothesTypeSelected
        )
    }
}

@Composable
private fun ImagesAndTexts(
    selectedLayerId: Long?,
    isTextEditing: Boolean,
    onPickImage: () -> Unit,
    onAddTextLayer: () -> Unit,
    onDeleteSelectedLayer: () -> Unit,
    onFinishTextEditing: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = onPickImage
            ) {
                Text(
                    text = "Cargar imagen",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = onAddTextLayer,
            ) {
                Text(
                    text = "Añadir texto",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                enabled = selectedLayerId != null,
                onClick = onDeleteSelectedLayer,
            ) {
                Text(
                    text = "Eliminar objeto",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            CustomButton(
                modifier = Modifier.weight(1f),
                enabled = isTextEditing,
                onClick = onFinishTextEditing
            ) {
                Text(
                    text = "Aceptar texto",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ClothesType(
    selectedClothesType: String,
    onClothesTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Tipo de prenda: $selectedClothesType",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = { onClothesTypeSelected("Camiseta") }
            ) {
                Text(
                    text = "Camiseta",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = { onClothesTypeSelected("Sudadera") },
            ) {
                Text(
                    text = "Sudadera",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = { onClothesTypeSelected("Hoodie") },
            ) {
                Text(
                    text = "Hoodie",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            CustomButton(
                modifier = Modifier.weight(1f),
                onClick = { onClothesTypeSelected("Polo") },
            ) {
                Text(
                    text = "Polo",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}