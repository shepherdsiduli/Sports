package co.shepherd.sports.ui

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.navigation.findNavController
import co.shepherd.sports.R
import co.shepherd.sports.core.BaseActivity
import co.shepherd.sports.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    MainActivityViewModel::class.java
), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setTransparentStatusBar()
        setupNavigation()
    }

    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.schedule -> navigateToFragment(BottomNavigationItem.SCHEDULE)
                R.id.events -> navigateToFragment(BottomNavigationItem.EVENTS)
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.schedule -> navigateToFragment(BottomNavigationItem.SCHEDULE)
            R.id.events -> navigateToFragment(BottomNavigationItem.EVENTS)
        }
        return true
    }

    enum class BottomNavigationItem {
        EVENTS,
        SCHEDULE
    }

    private fun navigateToFragment(fragment: BottomNavigationItem) {
        val navigationId = when (fragment) {
            BottomNavigationItem.EVENTS -> {
                R.navigation.events_navigation
            }
            BottomNavigationItem.SCHEDULE -> {
                R.navigation.schedule_navigation
            }
        }

        val navController = findNavController(R.id.container_fragment)
        val navigationGraph = navController.navInflater.inflate(navigationId)
        navController.graph = navigationGraph
    }
}