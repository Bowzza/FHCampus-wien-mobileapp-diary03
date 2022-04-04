package com.example.movie28022022.screens

import android.widget.HorizontalScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.movie28022022.R
import com.example.movie28022022.models.Movie
import com.example.movie28022022.models.getMovies
import com.example.movie28022022.viewmodel.FavoritesViewModel

@Preview(showBackground = true)
@Composable
fun DetailScreen(navController: NavController = rememberNavController(), movieId: String?, vm: FavoritesViewModel){
    val movie = filterMovie(movieId = movieId)
    MainContent(movie, navController) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CreateCard(movie = movie)

                Spacer(modifier = Modifier.height(8.dp))

                Divider()

                Text(text = "${movie.title}", style = MaterialTheme.typography.h5)

                HorizontalScrollView(movie)
            }
        }

    }
}

@Composable
fun MainContent(movie: Movie, navController: NavController, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.Cyan, elevation = 3.dp) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = movie.title)
                }
            }
        }) {
        Column(
        ) {
            content()
        }
    }
}

@Composable
fun HorizontalScrollView(movie: Movie = getMovies()[0]) {
    LazyRow {
        items(movie.images) { image ->
            Card(modifier = Modifier.padding(12.dp).size(240.dp), elevation = 4.dp) {
                AsyncImage(model = image, contentDescription = "image")
            }
        }
    }
}

fun filterMovie(movieId: String?) : Movie {
    return getMovies().filter { it.id == movieId }[0]
}