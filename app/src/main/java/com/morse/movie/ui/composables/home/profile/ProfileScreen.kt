package com.morse.movie.ui.composables.home.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.codelab.android.datastore.MediaItem
import com.morse.movie.R
import com.morse.movie.app.MovieDetailsDirection
import com.morse.movie.app.TVDetailsDirection
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.entities.ui.Statics
import com.morse.movie.ui.composables.home.shared.Empty
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.ui.composables.home.shared.MediaImage
import com.morse.movie.utils.Constants

@Preview(showSystemUi = true)
@Composable
fun ShowProfilePreview() {
    ProfileScreen()
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ProfileScreen(controller: NavHostController? = null, vm: ProfileViewModel = viewModel()) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val loading = createRef()
        val likesItems = vm.liked.collectAsState(initial = State.Loading)
        val staredItems = vm.stared.collectAsState(initial = State.Loading)
        when {
            likesItems.value is State.Loading || staredItems.value is State.Loading -> {
                Loading(modifier = Modifier.constrainAs(loading) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                })
            }
            else -> {
                RenderProfile(
                    controller,
                    (likesItems.value as State.Success<List<MediaItem>>).response,
                    (staredItems.value as State.Success<List<MediaItem>>).response,
                    emptyList()
                )
            }
        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ConstraintLayoutScope.RenderProfile(
    controller: NavHostController? = null,
    likedItems: List<MediaItem> = arrayListOf(),
    staredItems: List<MediaItem> = arrayListOf(),
    commentsItems: List<MediaItem> = arrayListOf(),
) {
    Statics.run {
        updateLikeValue(likedItems.size)
        updateStarValue(staredItems.size)
    }
    val footer = createGuidelineFromTop(0.35F)
    val topGuideline = createGuidelineFromTop(0.05F)
    val (profileTitle, settingIcon, background, nameBg,
        name, myPhoto, empty, statics, items) = createRefs()

    Image(
        painter = painterResource(id = R.drawable.profile_bg),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.constrainAs(background) {
            linkTo(parent.top, footer)
            linkTo(parent.start, parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

    Image(painter = painterResource(id = R.drawable.name_bg),
        contentDescription = null,
        modifier = Modifier.constrainAs(nameBg) {
            bottom.linkTo(background.bottom)
            linkTo(background.start, background.end)
        }
    )

    Text(
        text = stringResource(id = R.string.me), modifier = Modifier.constrainAs(name) {
            linkTo(nameBg.start, nameBg.end)
            bottom.linkTo(nameBg.bottom, 20.dp)
        },
        color = Color.Black,
        style = MaterialTheme.typography.h1,
        fontSize = TextUnit(16F, TextUnitType.Sp),
        fontWeight = FontWeight.Bold
    )

    Image(
        painter = painterResource(id = R.drawable.me), contentDescription = null,
        modifier = Modifier.constrainAs(myPhoto) {
            bottom.linkTo(name.top, 40.dp)
            linkTo(parent.start, parent.end)
            width = Dimension.value(Constants.regularImageWidth)
            height = Dimension.value(Constants.regularImageWidth)
        }
    )

    Text(
        text = stringResource(id = R.string.profile_lable),
        color = Color.White,
        style = MaterialTheme.typography.h1,
        fontSize = TextUnit(18F, TextUnitType.Sp),
        fontWeight = FontWeight.Black,
        modifier = Modifier.constrainAs(profileTitle) {
            top.linkTo(topGuideline)
            start.linkTo(parent.start, 10.dp)
        }
    )

    IconButton(modifier = Modifier.constrainAs(settingIcon) {
        top.linkTo(profileTitle.top)
        bottom.linkTo(profileTitle.bottom)
        end.linkTo(parent.end, 10.dp)
    }, onClick = { }) {
        Icon(Icons.Default.Settings, tint = Color.White, contentDescription = null)
    }

    LazyRow(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.constrainAs(statics) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end)
            top.linkTo(background.bottom, 20.dp)
            width = Dimension.fillToConstraints
        }
    ) {
        items(Statics.Items) {
            StaticsItem(
                number = it.value,
                name = it.title,
                isSelected = it.isSelected.value
            ) {
                Statics.unselectTab()
                Statics.selectTab(it)
            }
        }
    }

    if (likedItems.isEmpty()) {
        RenderEmptyMediaItems(modifier = Modifier.constrainAs(empty) {
            linkTo(statics.bottom, parent.bottom)
            linkTo(parent.start, parent.end)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        })
    } else {
        RenderFullMediaItems(modifier = Modifier.constrainAs(items) {
            linkTo(statics.bottom, parent.bottom)
            linkTo(parent.start, parent.end)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }, medias = likedItems) {
            when (it.isMovie) {
                true -> MovieDetailsDirection.apply {
                    injectMovieId(it.id)
                    navigate(controller)
                }
                false -> TVDetailsDirection.apply {
                    injectTVId(it.id)
                    navigate(controller)
                }
            }
        }

    }

}

@Composable
fun RenderFullMediaItems(
    modifier: Modifier,
    medias: List<MediaItem>,
    onClick: (MediaItem) -> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(4)) {
        items(items = medias, key = { item: MediaItem -> item.id }) {
            MediaImage(
                url = it.poster, modifier = Modifier
                    .padding(10.dp)
                    .size(
                        Constants.smallImageWidth, Constants.smallImageHeight
                    )
                    .clickable {
                        onClick.invoke(it)
                    }
            )
        }
    }
}

@Composable
fun RenderEmptyMediaItems(modifier: Modifier) {
    Empty(modifier = modifier, message = R.string.empty_interaction_on_movies)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun StaticsItem(number: Int, @StringRes name: Int, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = number.toString(),
            color = if (isSelected) Color.Black else Color(0XFF666666),
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(19F, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
        )

        Text(
            text = stringResource(id = name),
            color = Color(0XFF999999),
            style = MaterialTheme.typography.h1,
            fontSize = TextUnit(15F, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.selected),
                contentDescription = null
            )
        }


    }
}
