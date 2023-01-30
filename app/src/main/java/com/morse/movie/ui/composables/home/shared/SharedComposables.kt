package com.morse.movie.ui.composables.home.shared

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.morse.movie.R
import com.morse.movie.ui.theme.Shapes
import com.morse.movie.utils.Constants
import com.morse.movie.utils.customBackground

@Preview(showSystemUi = true)
@Composable
fun ShowSharedComposable() {
    Column {
        MediaItem(imageUrl = "", name = " Morse") {}
        Rate(modifier = Modifier, rateValue = "9.5")
        RatedMediaItem(
            imageUrl = "",
            mediaName = "Ahmed Morse",
            mediaYear = "2022",
            rateValue = "9.9"
        ) {}
    }
}

@Composable
fun MediaItem(imageUrl: String, name: String, onClick: () -> Unit) {
    ConstraintLayout(modifier = Modifier
        .padding(horizontal = 10.dp)
        .clickable { onClick.invoke() }) {

        val (poster, title) = createRefs()

        MediaImage(
            modifier = Modifier
                .size(Constants.regularImageWidth, Constants.regularImageHeight)
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            url = imageUrl
        )
        Text(
            text = name, color = Color(0xFF222222), style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(poster.bottom, 10.dp)
                start.linkTo(poster.start)
                end.linkTo(poster.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun Loading(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = Color(0XFFF89A04), strokeWidth = 5.dp
    )
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Empty(modifier: Modifier, @StringRes message: Int) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0F)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = message),
            color = Color.Black,
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.SemiBold,
            fontSize = TextUnit(
                15F,
                TextUnitType.Sp
            )
        )
    }
}

@Composable
fun RatedMediaItem(
    imageUrl: String,
    mediaName: String,
    mediaYear: String,
    rateValue: String,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .clickable { onClick.invoke() }
    ) {
        val (background, rate, name, year) = createRefs()
        MediaImage(
            modifier = Modifier
                .constrainAs(background) { top.linkTo(parent.top) }
                .size(
                    Constants.regularImageWidth, Constants.ratedRegularImageHeight
                ), url = imageUrl
        )
        Rate(modifier = Modifier.constrainAs(rate) {
            end.linkTo(background.end, 10.dp)
            top.linkTo(background.top, 10.dp)
        }, rateValue = rateValue)
        Text(
            text = mediaName,
            modifier = Modifier.constrainAs(name) {
                bottom.linkTo(background.bottom, 10.dp)
                linkTo(
                    start = background.start,
                    end = background.end,
                    startMargin = 10.dp,
                    endMargin = 10.dp,
                    bias = 0.0F
                )
                width = Dimension.fillToConstraints
            },
            color = Color.White,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = mediaYear, modifier = Modifier.constrainAs(year) {
            bottom.linkTo(name.top, 1.dp)
            start.linkTo(name.start)

        }, color = Color.White, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TVRatedMediaItem(imageUrl: String, mediaName: String, rateValue: String, onClick: () -> Unit) {
    ConstraintLayout(modifier = Modifier
        .padding(vertical = 5.dp)
        .clickable { onClick.invoke() }) {
        val (background, rate, name) = createRefs()
        MediaImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.tvImageHeight)
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                }, url = imageUrl
        )
        Rate(modifier = Modifier.constrainAs(rate) {
            end.linkTo(background.end, 10.dp)
            top.linkTo(background.top, 10.dp)
        }, rateValue = rateValue)
        Text(
            text = mediaName, modifier = Modifier.constrainAs(name) {
                top.linkTo(background.bottom, 10.dp)
                start.linkTo(background.start)
                linkTo(background.start, background.end, bias = 0.0F)
            }, color = Color.Black,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun RateBar(modifier: Modifier, count: Int, until: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(count) {
            val currentStar = it + 1
            if (until >= currentStar) {
                Image(
                    painter = painterResource(id = R.drawable.yellow_star),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(5.dp))
            } else {
                Image(
                    painter = painterResource(id = R.drawable.gray_star),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

@Composable
fun MediaImage(modifier: Modifier = Modifier, url: String, shape: Shape = Shapes.large) {
    AsyncImage(
        modifier = modifier
            .clip(shape)
            .shadow(10.dp, ambientColor = Color.Black),
        model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .build(),
        placeholder = painterResource(id = R.drawable.placeholder),
        contentScale = ContentScale.FillBounds,
        contentDescription = ""
    )
}

@Composable
fun Rate(modifier: Modifier, rateValue: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val rate = rateValue.split('.')
        Image(
            painter = painterResource(id = R.drawable.rate_shape), contentDescription = null
        )
        Row {
            Text(text = rate.first(), color = Color.White, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                text = ".${rate.last()}",
                color = Color.White,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun Fab(
    modifier: Modifier,
    isSelected : Boolean ,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick.invoke() }, modifier = Modifier
            .size(56.dp)
            .customBackground(isSelected)
            .then(modifier)
    ) {
        androidx.compose.material3.Icon(
            painter = painterResource(id = if (isSelected) selectedIcon else unselectedIcon),
            contentDescription = null,
            tint = if (isSelected) Color.White else Color(0XFF979797),
        )
    }
}