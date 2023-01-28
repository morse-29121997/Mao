package com.morse.movie.ui.composables.home.profile

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
        val (profileTitle, settingIcon, background, nameBg, name, myPhoto, like, watching, comments, items) = createRefs()

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

        StaticsItem(modifier = Modifier.constrainAs(like) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(watching.start)
            top.linkTo(background.bottom, 20.dp)
        }, number = 3210, name = R.string.like, isSelected = true)


        StaticsItem(modifier = Modifier.constrainAs(watching) {
            start.linkTo(like.end, 10.dp)
            end.linkTo(comments.start)
            linkTo(like.top, like.bottom)
        }, number = 1231, name = R.string.watching, isSelected = false)


        StaticsItem(modifier = Modifier.constrainAs(comments) {
            start.linkTo(watching.end)
            linkTo(like.top, like.bottom)
            end.linkTo(parent.end, 10.dp)
        }, number = 44, name = R.string.comment, isSelected = false)

        LazyVerticalGrid(columns = GridCells.Fixed(4)){

        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun StaticsItem(modifier: Modifier, number: Int, @StringRes name: Int, isSelected: Boolean) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .then(modifier),
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