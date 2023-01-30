package com.morse.movie.ui.composables.details.movies

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.morse.movie.data.entities.ui.State
import com.morse.movie.utils.customBackground
import com.morse.movie.R
import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.MovieDetailsResponse
import com.morse.movie.ui.composables.home.shared.*
import com.morse.movie.ui.theme.Shapes
import com.morse.movie.utils.Constants
import com.morse.movie.utils.LoadFromVM

@Composable
fun MovieDetailsScreen(
    controller: NavHostController? = null,
    vm: MovieDetailsViewModel
) {
    val movieId = controller?.currentBackStackEntry?.arguments?.getInt("movieId") ?: 240
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val details = vm.details.collectAsState(initial = State.Loading)
        val credits = vm.credits.collectAsState(initial = State.Loading)
        val loading = createRef()
        LoadFromVM(movieId) {
            if(movieId > 0) {
                vm.load(movieId)
            }
        }
        when {
            details.value is State.Loading || credits.value is State.Loading -> Loading(
                modifier = Modifier.constrainAs(
                    loading
                ) {
                    linkTo(parent.top, parent.bottom)
                    linkTo(parent.start, parent.end)
                })
            else -> {
                RenderMovieDetails(
                    (details.value as State.Success<MovieDetailsResponse>).response,
                    (credits.value as State.Success<CastResponse>).response
                )
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ConstraintLayoutScope.RenderMovieDetails(
    detailsModel: MovieDetailsResponse,
    castModel: CastResponse
) {
    val topGuideline = createGuidelineFromTop(0.05F)
    val textDetailsGuideline = createGuidelineFromTop(0.29F)
    val detailGuidelineBottom = createGuidelineFromTop(0.55F)
    val (name, watching, rateNumbers, rateStars, playFab,
        details, likeFab, starFab, commentsFab, backBtn,
        shareBtn, detailBackground, rateBar, rateValueInt,
        rateValueDouble, cast) = createRefs()
    MediaImage(
        url = detailsModel.getBackgroundImage(),
        modifier = Modifier.constrainAs(detailBackground) {
            linkTo(parent.top, detailGuidelineBottom)
            linkTo(parent.start, parent.end)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        },
        shape = RectangleShape
    )

    Row(modifier = Modifier.constrainAs(backBtn) {
        start.linkTo(parent.start, 10.dp)
        top.linkTo(topGuideline)
    }, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.back),
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    IconButton(modifier = Modifier.constrainAs(shareBtn) {
        top.linkTo(backBtn.top)
        bottom.linkTo(backBtn.bottom)
        end.linkTo(parent.end, 10.dp)
    }, onClick = { }) {
        Icon(
            painter = painterResource(id = R.drawable.share_icon),
            contentDescription = null,
            tint = Color.White
        )
    }

    Text(
        text = detailsModel.title,
        modifier = Modifier.constrainAs(name) {
            linkTo(backBtn.start, shareBtn.end)
            top.linkTo(textDetailsGuideline)
            width = Dimension.fillToConstraints
        },
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = TextUnit(22F, TextUnitType.Sp)
    )

    Text(
        text = stringResource(id = R.string.people_watching, detailsModel.popularity.toString()),
        modifier = Modifier.constrainAs(watching) {
            linkTo(backBtn.start, shareBtn.end)
            top.linkTo(name.bottom, 5.dp)
            width = Dimension.fillToConstraints
        },
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = TextUnit(18F, TextUnitType.Sp)
    )

    Text(
        text = detailsModel.getVoteDecimal(),
        modifier = Modifier.constrainAs(rateValueInt) {
            start.linkTo(watching.start)
            top.linkTo(watching.bottom, 5.dp)
        },
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
        color = Color(0XFFFECB2F),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = TextUnit(18F, TextUnitType.Sp)
    )
    Text(
        text = detailsModel.getVoteFraction(),
        modifier = Modifier.constrainAs(rateValueDouble) {
            start.linkTo(rateValueInt.end)
            top.linkTo(rateValueInt.top)
        },
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
        color = Color(0XFFFECB2F),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = TextUnit(14F, TextUnitType.Sp)
    )

    RateBar(modifier = Modifier.constrainAs(rateBar) {
        linkTo(rateValueInt.top, rateValueInt.bottom, bias = 0.5F)
        start.linkTo(rateValueDouble.end, 10.dp)
    }, count = 5, until = 4)

    Fab(
        modifier = Modifier
            .constrainAs(playFab) {
                linkTo(rateBar.end, parent.end, endMargin = 10.dp, bias = 1F)
                top.linkTo(watching.top)
            },
        isSelected = true,
        selectedIcon = R.drawable.play,
        unselectedIcon = R.drawable.play
    ) {

    }

    Text(
        text = detailsModel.overview,
        modifier = Modifier.constrainAs(details) {
            start.linkTo(watching.start)
            linkTo(playFab.bottom, detailBackground.bottom, 5.dp)
            linkTo(backBtn.start, shareBtn.end)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        },
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Companion.Justify,
        color = Color.White,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        fontSize = TextUnit(18F, TextUnitType.Sp)
    )

    RenderActors(modifier = Modifier.constrainAs(cast) {
        linkTo(detailBackground.bottom, likeFab.top, 10.dp)
        linkTo(backBtn.start, shareBtn.end)
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
    }, castModel)

    DetailsAction(
        onClick = {},
        modifier = Modifier
            .constrainAs(likeFab) {
                bottom.linkTo(parent.bottom, 10.dp)
                linkTo(parent.start, starFab.start)
            },
        isSelected = true,
        count = "230k",
        unselectedIcon = R.drawable.unselected_like,
        selectedIcon = R.drawable.selected_like
    )

    DetailsAction(
        onClick = {},
        modifier = Modifier
            .constrainAs(starFab) {
                bottom.linkTo(parent.bottom, 10.dp)
                linkTo(likeFab.end, commentsFab.start)
            }, isSelected = false,
        count = "2003",
        unselectedIcon = R.drawable.unselected_star,
        selectedIcon = R.drawable.selected_star
    )

    DetailsAction(
        onClick = {}, modifier = Modifier
            .constrainAs(commentsFab) {
                bottom.linkTo(parent.bottom, 10.dp)
                linkTo(starFab.end, parent.end)
            }, isSelected = false,
        count = "390",
        unselectedIcon = R.drawable.unselected_comment,
        selectedIcon = R.drawable.selected_comment
    )


}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ConstraintLayoutScope.RenderActors(
    modifier: Modifier,
    casts: CastResponse,
    scrollableState: ScrollableState = rememberScrollState(),
) {
    Column(
        modifier = Modifier
            .scrollable(scrollableState, Orientation.Vertical)
            .then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.full_cast_and_crew),
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Companion.Justify,
            color = Color(0XFF666666),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(18F, TextUnitType.Sp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(modifier = modifier) {
            items(casts.cast) {
                MediaItem(
                    imageUrl = it.getPosterPath(),
                    name = it.name
                ) {

                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun DetailsAction(
    modifier: Modifier,
    isSelected: Boolean,
    count: String,
    @DrawableRes unselectedIcon: Int,
    @DrawableRes selectedIcon: Int,
    onClick: () -> Unit
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Fab(
            modifier = modifier,
            isSelected = isSelected,
            selectedIcon = selectedIcon,
            unselectedIcon = unselectedIcon
        ) {
            onClick.invoke()
        }

        Text(
            text = count,
            style = MaterialTheme.typography.h1,
            color = Color(0xFF999999),
            fontWeight = FontWeight.Medium,
            fontSize = TextUnit(13F, TextUnitType.Sp),
        )

    }
}


