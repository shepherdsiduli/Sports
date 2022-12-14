package co.shepherd.sports.viewState

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.ui.schedule.ScheduleViewState
import co.shepherd.sports.utils.domain.Status
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ScheduleViewStateShould {
    @Test
    fun `return loading true when status is loading`() {
        // Given
        val givenViewState = ScheduleViewState(status = Status.LOADING)

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }

    @Test
    fun `not return loading false when status is error`() {
        // Given
        val givenViewState = ScheduleViewState(status = Status.SUCCESS)

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }

    @Test
    fun `not return loading false when status is success`() {
        // Given
        val givenViewState = ScheduleViewState(status = Status.ERROR)

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }

    @Test
    fun `return correct error message when status is error`() {
        // Given
        val givenViewState =
            ScheduleViewState(
                status = Status.ERROR,
                error = "500 Internal Server Error"
            )

        // When
        val actualResult = givenViewState.getErrorMessage()

        // Then
        Truth.assertThat(actualResult).isEqualTo("500 Internal Server Error")
    }

    @Test
    fun `return true for error placeholder visibility  when status is error`() {
        // Given
        val givenViewState =
            ScheduleViewState(
                status = Status.ERROR,
                error = "500 Internal Server Error"
            )

        // When
        val actualResult = givenViewState.shouldShowErrorMessage()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }
}