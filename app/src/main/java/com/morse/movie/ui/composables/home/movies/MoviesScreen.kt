package com.morse.movie.ui.composables.home.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.morse.movie.R
import com.morse.movie.app.MovieDetailsDirection
import com.morse.movie.data.entities.remote.MoviesResponse
import com.morse.movie.data.entities.ui.State
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.ui.composables.home.shared.MediaItem
import com.morse.movie.ui.composables.home.shared.RatedMediaItem
import com.morse.movie.utils.LoadFromVM
import com.morse.movie.utils.log
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@Preview(showSystemUi = true)
@Composable
fun PreviewMoviesScreen() {
    MoviesScreen()
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun MoviesScreen(controller: NavHostController? = null, vm: MoviesViewModel = viewModel()) {
    val scrollable = rememberScrollState()
    LoadFromVM(true) {
        vm.load()
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollable, Orientation.Vertical)
    ) {
        val popular = vm.popular.collectAsState(initial = State.Loading)
        val now = vm.now.collectAsState(initial = State.Loading)
        val topGuideline = createGuidelineFromTop(0.05F)
        val (moviesTitle, searchIcon, loading, scrollableContent) = createRefs()
        when {
            popular.value is State.Loading || now.value is State.Loading -> {
                Loading(Modifier.constrainAs(loading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            }
            else -> {
                Text(
                    text = stringResource(id = R.string.movies_lable),
                    color = Color.Black,
                    style = MaterialTheme.typography.h1,
                    fontSize = TextUnit(18F, TextUnitType.Sp),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.constrainAs(moviesTitle) {
                        top.linkTo(topGuideline)
                        start.linkTo(parent.start, 10.dp)
                    }
                )

                IconButton(modifier = Modifier.constrainAs(searchIcon) {
                    top.linkTo(moviesTitle.top)
                    bottom.linkTo(moviesTitle.bottom)
                    end.linkTo(parent.end, 10.dp)
                }, onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }

                LazyColumn(modifier = Modifier.constrainAs(scrollableContent) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        startMargin = 10.dp,
                        endMargin = 10.dp
                    )
                    top.linkTo(moviesTitle.bottom, 10.dp)
                    bottom.linkTo(parent.bottom, 60.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {
                    item { ADSBanner(modifier = Modifier) }
                    item {
                        NowMovies(
                            nowMovies = (now.value as State.Success<*>).response as ArrayList<MoviesResponse.Movie>,
                            modifier = Modifier
                        ) {
                            MovieDetailsDirection.apply {
                                injectMovieId(it.id)
                                navigate(controller)
                            }
                        }
                    }
                    item {
                        PopularsMovies(
                            popularMovies = (popular.value as State.Success<*>).response as ArrayList<MoviesResponse.Movie>,
                            modifier = Modifier
                        ) {
                            " Name : ${it.title} and With Id : ${it.id}".log()
                            MovieDetailsDirection.apply {
                                injectMovieId(it.id)
                                navigate(controller)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@Composable
fun ADSBanner(modifier: Modifier) {
    val trailerResources = arrayOf(R.drawable.trailier1, R.drawable.trailer2)
    val pagerState = rememberPagerState()
    HorizontalPager(
        count = 2,
        modifier = modifier,
        state = pagerState,
    ) {
        Image(
            painter = painterResource(id = trailerResources[it]),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun NowMovies(
    modifier: Modifier,
    nowMovies: ArrayList<MoviesResponse.Movie>,
    onClick: (MoviesResponse.Movie) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.now),
            color = Color.Black,
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
            items(nowMovies, key = {it.id}) {
                MediaItem(imageUrl = it.getFullPosterPath(), name = it.title) {
                    it.title.log()
                    onClick.invoke(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun PopularsMovies(
    modifier: Modifier,
    popularMovies: ArrayList<MoviesResponse.Movie>,
    onClick: (MoviesResponse.Movie) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.popular),
            color = Color.Black,
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.height(500.dp)
        ) {
            items(popularMovies, key = {it.id}) {
                RatedMediaItem(
                    imageUrl = it.getFullPosterPath(),
                    mediaName = it.title,
                    mediaYear = it.getYear(),
                    rateValue = it.voteAverage.toString()
                ) {
                    it.title.log()
                    onClick.invoke(it)
                }
            }
        }
    }
}
