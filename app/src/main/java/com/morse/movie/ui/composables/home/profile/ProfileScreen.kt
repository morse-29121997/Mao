package com.morse.movie.ui.composables.home.profile

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.morse.movie.R
import com.morse.movie.data.entities.ui.Statics
import com.morse.movie.ui.composables.home.shared.Empty
import com.morse.movie.utils.Constants

@Preview(showSystemUi = true)
@Composable
fun ShowProfilePreview() {
    ProfileScreen()
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ProfileScreen(controller: NavHostController? = null) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val footer = createGuidelineFromTop(0.35F)
        val topGuideline = createGuidelineFromTop(0.05F)
        val (profileTitle, settingIcon, background, nameBg,
            name, myPhoto, empty, statics
                , items) = createRefs()
        val likesItems = arrayListOf<Int>()
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
                ){
                    Statics.unselectTab()
                    Statics.selectTab(it)
                }
            }
        }

        if (likesItems.isEmpty()) {
            Empty(modifier = Modifier.constrainAs(empty) {
                linkTo(statics.bottom, parent.bottom)
                linkTo(parent.start, parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }, message = R.string.empty_interaction_on_movies)
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(4)) {

            }
        }

    }
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
            Image(painter = painterResource(id = R.drawable.selected), contentDescription = null)
        }


    }
}