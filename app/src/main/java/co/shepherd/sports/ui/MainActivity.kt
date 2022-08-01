package co.shepherd.sports.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
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
//        val appBarConfig = AppBarConfiguration(
//            setOf(R.id.dashboardFragment),
//            binding.drawerLayout
//        )

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        binding.toolbar.overflowIcon = getDrawable(R.drawable.ic_menu)
//        binding.toolbar.navigationIcon?.setTint(Color.parseColor("#130e51"))
//        setupWithNavController(binding.toolbar, navController, appBarConfig)
//        setupWithNavController(binding.bottomNavigationView, navController)
//        binding.bottomNavigationView.setNavigationItemSelectedListener(this)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.splashFragment -> {
//                    binding.toolbar.hide()
//                }
//                R.id.dashboardFragment -> {
//                    binding.toolbar.show()
//                    binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
//                }
//                R.id.searchFragment -> {
//                    binding.toolbar.hide()
//                }
//                else -> {
//                    binding.toolbar.setNavigationIcon(R.drawable.ic_back)
//                    binding.toolbar.show()
//                }
//            }
//        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.schedule -> navigateToFragment(BottomNavigationItem.SCHEDULE)
                R.id.events -> navigateToFragment(BottomNavigationItem.EVENTS)
            }
            true
        }
    }

//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
//
////        val navController = findNavController(R.id.nav_host_fragment_content_main)
////        appBarConfiguration = AppBarConfiguration(navController.graph)
////        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        // binding.bottomNavigationView.setOnNavigationItemSelectedListener {
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.schedule -> navigateToFragment(BottomNavigationItem.SCHEDULE)
//                R.id.events -> navigateToFragment(BottomNavigationItem.EVENTS)
//            }
//            true
//        }
//    }

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

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val navigationGraph = navController.navInflater.inflate(navigationId)
        navController.graph = navigationGraph
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

}