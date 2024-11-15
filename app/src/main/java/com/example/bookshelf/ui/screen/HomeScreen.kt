import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.ui.BookShelfViewModel
import com.example.bookshelf.ui.BookUIState

@Composable
fun HomeScreen(viewModel: BookShelfViewModel, modifier: Modifier = Modifier) {

    val currentUIState by remember { derivedStateOf { viewModel.bookUiState}}
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        when (currentUIState) {
            is BookUIState.Searching -> StartScreen(viewModel)
            is BookUIState.ShowingResult -> ResultScreen(viewModel)
            is BookUIState.Error -> ErrorScreen(viewModel)
        }
    }
}

@Composable
fun StartScreen(viewModel: BookShelfViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter search query") },
            placeholder = { Text("Type book name") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.updateSearchTerm(searchQuery)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
fun ResultScreen(viewModel: BookShelfViewModel) {
    val uiState = viewModel.bookUiState

    // Although ResultScreen is intended to be displayed only when uiState is BookUIState.ShowingResult,
    // Compose functions run synchronously, meaning the UI might not immediately reflect state changes.
    // For example, when the GoBackButton is pressed and uiState is updated to "Searching",
    // ResultScreen may still be rendered temporarily before the UI has fully updated.
    if (uiState is BookUIState.ShowingResult) {
        val result = uiState.result
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(items = result) { item ->
                    BookPhotoCard(
                        imgSrc = item,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            GoBackButton(
                viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }
    } else {
        Text("Loading")
    }
}


@Composable
fun BookPhotoCard(imgSrc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().aspectRatio(2f / 3f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(imgSrc.replace("http://", "https://"))
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_image_generic),
                placeholder = painterResource(R.drawable.loading_image_generic),
                contentDescription = "BookPicture",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ErrorScreen(viewModel: BookShelfViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connection Error: Between GoogleAPI and your Internet, at least one is down!",
            modifier = Modifier.padding(16.dp) // Add some padding around the message
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoBackButton(viewModel)
    }
}

@Composable
fun GoBackButton(viewModel: BookShelfViewModel, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {  Handler(Looper.getMainLooper()).post { viewModel.reset() } },
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Text(text = "Search Again")
        }
    }
}

