package com.percivalruiz.cartrack.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.percivalruiz.cartrack.ui.user.MainActivity
import com.percivalruiz.cartrack.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

  private val viewModel: SplashViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportActionBar?.setDisplayHomeAsUpEnabled(false)

    Handler().postDelayed({
      val intent = if(viewModel.isLoggedIn()) {
        Intent(this@SplashActivity, MainActivity::class.java)
      } else {
        Intent(this@SplashActivity, LoginActivity::class.java)
      }
      startActivity(intent)
      finish()
    }, 2000)
  }
}
