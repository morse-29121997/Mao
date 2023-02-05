package com.morse.movie.ui.composables.details.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.morse.movie.data.entities.ui.State
import com.morse.movie.R
import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.ui.composables.home.shared.*
import com.morse.movie.utils.LoadFromVM
import com.morse.movie.utils.shareMedia

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
            if (movieId > 0) {
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
                RenderDetails(
                    { controller?.popBackStack() },
                    (details.value as State.Success<DetailsResponse>).response,
                    (credits.value as State.Success<CastResponse>).response.cast
                )
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ConstraintLayoutScope.RenderDetails(
    onBackPressed: () -> Unit,
    detailsModel: DetailsResponse,
    castModel: ArrayList<CastResponse.Cast>
) {
    val context = LocalContext.current
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

    Row(modifier = Modifier
        .constrainAs(backBtn) {
            start.linkTo(parent.start, 10.dp)
            top.linkTo(topGuideline)
        }
        .clickable {
            onBackPressed.invoke()
        }, verticalAlignment = Alignment.CenterVertically
    ) {

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
    }, onClick = {
        context.shareMedia(detailsModel)
    }) {
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




