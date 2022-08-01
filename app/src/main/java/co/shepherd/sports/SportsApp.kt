package co.shepherd.sports

import android.app.Application
import android.os.Build
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SportsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG && !isRoboUnitTest()) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }
}