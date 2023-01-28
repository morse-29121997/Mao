package com.morse.movie.ui.composables.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.morse.movie.app.OnBoardingDirection
import com.morse.movie.data.entities.ui.OnboardButtonStatus
import com.morse.movie.data.entities.ui.OnboardPageItem
import com.morse.movie.utils.execute

@OptIn(ExperimentalUnitApi::class, ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(navController: NavHostController? = null) {
    ConstraintLayout {
        val scope = rememberCoroutineScope()
        val indicator = createRef()
        val pagerState = rememberPagerState()
        val indicatorGuideline = createGuidelineFromTop(0.82F)
        HorizontalPager(
            count = 3, state = pagerState, modifier = Modifier
                .fillMaxSize()
        ) { pageIndex ->
            val currentPage = OnboardPageItem.getPageByPosition(pageIndex)
            PageItem(page = currentPage) {
                when (it.buttonStatus) {
                    OnboardButtonStatus.GetStarted -> {
                        OnBoardingDirection.navigate(navController)
                    }
                    else -> {
                        scope.execute {
                            pagerState.animateScrollToPage(OnboardPageItem.getPageIndex(currentPage) + 1)
                        }
                    }
                }
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.constrainAs(indicator) {
                top.linkTo(indicatorGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            pageCount = 3,
            activeColor = Color.White,
            inactiveColor = Color.LightGray
        )
    }
}

@Preview(device = Devices.PIXEL_4_XL, showSystemUi = true)
@Composable
fun PreviewOnSplashScreenEn() {
    GetStartedButton {

    }
}

