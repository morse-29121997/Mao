package com.morse.movie.ui.composables.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.morse.movie.R
import com.morse.movie.data.entities.ui.OnboardButtonStatus
import com.morse.movie.data.entities.ui.OnboardPageItem
import com.morse.movie.ui.theme.Typography


@OptIn(ExperimentalUnitApi::class)
@Composable
fun PageItem(page: OnboardPageItem, onClick: (OnboardPageItem) -> Unit) {
    ConstraintLayout() {
        val (background, foreground, message, button) = createRefs()
        val textGuideline = createGuidelineFromTop(0.65F)
        val buttonGuideline = createGuidelineFromTop(0.9F)
        Image(
            painter = painterResource(id = page.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(background) {
                    top.linkTo(parent.top)
                }
        )
        Image(
            painter = painterResource(id = page.foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(foreground) {
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = stringResource(id = page.message),
            modifier = Modifier
                .constrainAs(message) {
                    top.linkTo(textGuideline)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                },
            style = Typography.body1,
            color = Color.White,
            fontSize = TextUnit(20F, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(2F, TextUnitType.Em)
        )

        when (page.buttonStatus) {
            OnboardButtonStatus.Next -> NextButton(Modifier.constrainAs(button) {
                top.linkTo(buttonGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                onClick.invoke(page)
            }
            else -> GetStartedButton(Modifier.constrainAs(button) {
                top.linkTo(buttonGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                onClick.invoke(page)
            }
        }

    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    OutlinedButton(
        onClick = onNextClick,
        modifier = modifier,
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.next),
            style = Typography.body1,
            color = Color.White,
        )
        Spacer(modifier = Modifier.size(width = 20.dp, height = 0.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.next),
            contentDescription = null
        )
    }
}

@Composable
fun GetStartedButton(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit
) {
    val gradient =
        Brush.horizontalGradient(listOf(Color(0xFFF99F00), Color(0xFFDB3069)))
    Button(
        onClick = onStartClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .background(gradient, RoundedCornerShape(30.dp))
                .padding(50.dp, 15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.get_started),
                style = Typography.body1,
                color = Color.White,
            )
        }
    }
}
