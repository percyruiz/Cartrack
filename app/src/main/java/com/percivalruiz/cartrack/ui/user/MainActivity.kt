package com.percivalruiz.cartrack.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.percivalruiz.cartrack.R
import com.percivalruiz.cartrack.databinding.ActivityMainBinding
import com.percivalruiz.cartrack.ui.login.LoginActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Screen containing user list and detail fragments
 *
 */
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfig: AppBarConfiguration
  private val viewModel: MainViewModel by viewModel()

  @InternalCoroutinesApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // Setup navigation for the two fragments
    val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navHost.navController.apply {
      appBarConfig = AppBarConfiguration(graph)

      addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
          R.id.userListFragment -> {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
          }
          R.id.userDetailFragment -> {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
          }
        }
      }
    }

    setSupportActionBar(binding.toolbar)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.logout -> {
        viewModel.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfig)
  }
}
