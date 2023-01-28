package com.morse.movie.ui.composables.home.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.morse.movie.R
import com.morse.movie.ui.theme.Shapes
import com.morse.movie.utils.Constants

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
fun MediaImage(modifier: Modifier = Modifier, url: String) {
    AsyncImage(
        modifier = modifier
            .clip(Shapes.large)
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