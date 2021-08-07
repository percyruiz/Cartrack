package com.percivalruiz.cartrack.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.databinding.ActivityLoginBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding
  private val viewModel: LoginViewModel by viewModel()

  @InternalCoroutinesApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.username.addTextChangedListener {
      viewModel.setUsername(it.toString())
    }

    binding.password.addTextChangedListener {
      viewModel.setPassword(it.toString())
    }

    binding.loginButton.setOnClickListener {
      viewModel.login(binding.username.text.toString(), binding.password.text.toString())
    }

    // Observe if login button should be enabled
    lifecycleScope.launchWhenStarted {
      viewModel.isLoginButtonEnabled.collect {
        binding.loginButton.isEnabled  = it
      }
    }

    // Observe result of login
    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.loginUserFlow.collect {
          when (it.status) {
            Result.Status.SUCCESS -> {
              Log.d("test", "success")
            }
            Result.Status.ERROR -> {
              Log.d("test", it.message.toString())
              Toast.makeText(
                this@LoginActivity,
                "Invalid username and/or password",
                Toast.LENGTH_SHORT
              ).show()
            }
            Result.Status.NONE -> {
              Log.d("test", "none")
            }
          }
        }
      }
    }
  }
}
