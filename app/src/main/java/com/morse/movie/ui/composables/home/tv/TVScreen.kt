package com.morse.movie.ui.composables.home.tv

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.morse.movie.R
import com.morse.movie.app.TVDetailsDirection
import com.morse.movie.data.entities.remote.MoviesResponse
import com.morse.movie.data.entities.remote.TVResponse
import com.morse.movie.data.entities.ui.State
import com.morse.movie.ui.composables.home.movies.ADSBanner
import com.morse.movie.ui.composables.home.movies.NowMovies
import com.morse.movie.ui.composables.home.movies.PopularsMovies
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.ui.composables.home.shared.MediaItem
import com.morse.movie.ui.composables.home.shared.RatedMediaItem
import com.morse.movie.ui.composables.home.shared.TVRatedMediaItem
import com.morse.movie.utils.LoadFromVM

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TVsScreen(controller: NavHostController? = null, vm: TvViewModel = viewModel()) {
    val scrollable = rememberScrollState()
    LoadFromVM(true) {
        vm.load()
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val topGuideline = createGuidelineFromTop(0.05F)
        val (tvTitle, searchIcon, loading, scrollableContent) = createRefs()
        val nowPlaying = vm.nowPlaying.collectAsState(initial = State.Loading)
        val popular = vm.popular.collectAsState(initial = State.Loading)
        when {
            popular.value is State.Loading || nowPlaying.value is State.Loading -> {
                Loading(Modifier.constrainAs(loading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            }
            else -> {
                Text(
                    text = stringResource(id = R.string.tv_lable),
                    color = Color.Black,
                    style = MaterialTheme.typography.h1,
                    fontSize = TextUnit(18F, TextUnitType.Sp),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.constrainAs(tvTitle) {
                        top.linkTo(topGuideline)
                        start.linkTo(parent.start, 10.dp)
                    }
                )

                IconButton(modifier = Modifier.constrainAs(searchIcon) {
                    top.linkTo(tvTitle.top)
                    bottom.linkTo(tvTitle.bottom)
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
                    top.linkTo(tvTitle.bottom, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {
                    item {
                        NewComingTV(
                            tvs = (nowPlaying.value as State.Success<*>).response as ArrayList<TVResponse.TV>,
                            modifier = Modifier
                        ) {
                            TVDetailsDirection.apply {
                                injectTVId(it.id)
                                navigate(controller)
                            }
                        }
                    }
                    item {
                        PopularTV(
                            tvs = (popular.value as State.Success<*>).response as ArrayList<TVResponse.TV>,
                            modifier = Modifier
                        ) {
                            TVDetailsDirection.apply {
                                injectTVId(it.id)
                                navigate(controller)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewComingTV(
    tvs: ArrayList<TVResponse.TV>,
    modifier: Modifier,
    onClick: (TVResponse.TV) -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        items(tvs) {
            MediaItem(imageUrl = it.getFullPosterPath(), name = it.name) {
                onClick.invoke(it)
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun PopularTV(tvs: ArrayList<TVResponse.TV>, modifier: Modifier, onClick: (TVResponse.TV) -> Unit) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.popular),
            color = Color.Black,
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.height(500.dp)
        ) {
            items(tvs) {
                TVRatedMediaItem(
                    imageUrl = it.getFullPosterPath(),
                    mediaName = it.name,
                    rateValue = it.vote_average.toString()
                ) {
                    onClick.invoke(it)
                }
            }
        }
    }
}