package com.morse.movie.data.entities.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.morse.movie.R

enum class OnboardButtonStatus {
    Next,
    GetStarted
}

data class OnboardPageItem(
    @DrawableRes val background: Int,
    @DrawableRes val foreground: Int,
    @StringRes val message: Int,
    val buttonStatus: OnboardButtonStatus = OnboardButtonStatus.Next
) {
    companion object {

        private val pages = arrayListOf(
            OnboardPageItem(
                R.drawable.background,
                R.drawable.onboarding_background_3,
                R.string.page3_message
            ),
            OnboardPageItem(
                R.drawable.background2,
                R.drawable.onboarding_foreground_1,
                R.string.page1_message
            ),
            OnboardPageItem(
                R.drawable.onboarding_background3,
                R.drawable.onboarding_foreground_2,
                R.string.page2_message,
                OnboardButtonStatus.GetStarted
            ),
        )

        fun getPageIndex (pageItem: OnboardPageItem) = pages.indexOf(pageItem)

        fun getPageByPosition(position: Int) = pages[position]
    }
}
