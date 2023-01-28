package com.morse.movie.ui.composables.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.morse.movie.R
import com.morse.movie.app.OnBoardingDirection
import com.morse.movie.app.SplashDirection
import com.morse.movie.ui.theme.MoonFont
import com.morse.movie.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController? = null) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (copyRight, appName, logoIcon) = createRefs()
        val topGuideline = createGuidelineFromTop(0.27F)

        LaunchedEffect(true) {
            delay(3000)
            SplashDirection.navigate(navController)
        }

        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = "Splash-Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.mao), contentDescription = "Meo-Logo",
            modifier = Modifier
                .constrainAs(logoIcon) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
                .size(150.dp),
            contentScale = ContentScale.Crop

        )

        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.constrainAs(appName) {
                top.linkTo(logoIcon.bottom, 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            style = Typography.body1,
            color = Color.White
        )

        Text(
            text = stringResource(id = R.string.copy_right),
            modifier = Modifier.constrainAs(copyRight) {
                bottom.linkTo(parent.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            style = Typography.body1,
            color = Color.White
        )


    }
}

@Preview(device = Devices.PIXEL_4_XL, showSystemUi = true)
@Composable
fun PreviewSplashScreenEn() {
    SplashScreen()
}

