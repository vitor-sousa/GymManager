package com.vitorsousa.gymmanager.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.vitorsousa.gymmanager.R
import com.vitorsousa.gymmanager.databinding.ActivityMainBinding
import com.vitorsousa.gymmanager.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupNavigation()
        setupBottomNavigation()
        setupObservers()
    }


    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.treinosFragment, R.id.accountFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, navDestination: NavDestination, _ ->
            when (navDestination.id) {
                R.id.treinoDetailFragment -> {
                    binding.toolbar.menu.clear()
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.newTreinoFragment -> {
                    binding.toolbar.menu.clear()
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.newExercicioFragment -> {
                    binding.toolbar.menu.clear()
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.exercicioDetailFragment -> {
                    binding.toolbar.menu.clear()
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupObservers() {
        authViewModel.currentUser.observe(this) { user ->
            if (user == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_out -> authViewModel.userSignOut()
            android.R.id.home -> onSupportNavigateUp()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}