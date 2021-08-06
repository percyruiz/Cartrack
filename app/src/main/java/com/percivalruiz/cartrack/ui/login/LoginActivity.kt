package com.percivalruiz.cartrack.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.percivalruiz.cartrack.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

  private val viewModel: LoginViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
  }
}
