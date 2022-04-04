package com.example.movie28022022.screens

//import com.example.movie28022022.MainContent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movie28022022.models.Movie
import com.example.movie28022022.models.getMovies
import com.example.movie28022022.navigation.MovieScreens
import com.example.movie28022022.viewmodel.FavoritesViewModel


@Composable
fun HomeScreen(navController: NavController = rememberNavController(), vm: FavoritesViewModel){
    ToolbarWidget(navController)
}

@Composable
fun MainContent(navController: NavController, movies: List<Movie>) {
    LazyColumn {
        items(movies) { items ->
            CreateCard(movie = items) { movieId ->
                navController.navigate(route = MovieScreens.DetailScreen.name+"/$movieId")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreateCard(movie: Movie, onItemClick: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(movie.id)
            },
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = CircleShape,
                elevation = 6.dp
            ) {
                AsyncImage(
                    model = movie.images[0],
                    contentDescription = "seas",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }

            Column {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                if(movie.director != "N/A") {
                    Text(text = "Director: "+movie.director, fontSize = 13.sp)
                } else {
                    Text(text = "No director", fontSize = 13.sp)
                }
                Text(text = "Released: "+movie.year, fontSize = 13.sp)
                AnimatedVisibility(visible = expanded) {
                    Column(Modifier.padding(4.dp)){
                        Text(text = "Plot: "+movie.plot, fontSize = 13.sp)
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                        Text(text = "Genre: "+movie.genre, fontSize = 13.sp)
                        Text(text = "Actors: "+movie.actors, fontSize = 13.sp)
                        Text(text = "Rating: "+movie.rating, fontSize = 13.sp)
                    }
                }
                IconButton(modifier = Modifier.padding(horizontal = 0.dp), onClick = {
                    expanded = !expanded
                }) {
                    if(!expanded) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = "More Details"
                        )
                    } else {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "More Details"
                        )
                    }
                }
            }
            /*IconButton(onClick = {
            *//*TODO*//* }) {
                Icon(
                    Icons.Default.FavoriteBorder,

                    contentDescription = "More Details"
                )

            }*/
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                    /*TODO*/ }) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        tint = Color.Magenta,

                        contentDescription = "More Details"
                    )

                }
            }
        }

    }
}

@Composable
fun ToolbarWidget(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movies")
                },
                actions = {
                    TopAppBarDropdownMenu(navController)
                })
        }) {
        Column(
        ) {
            MainContent(navController, movies = getMovies())
        }
    }
}

@Composable
fun TopAppBarDropdownMenu(navController: NavController) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        Modifier.width(150.dp)
    ) {
        DropdownMenuItem(onClick = {
            expanded.value = false
        }) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(route = MovieScreens.FavouriteScreen.name) },
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites",
                )
                Text(text = "Favorites")
            }
        }
    }
}