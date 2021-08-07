package com.percivalruiz.cartrack.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.percivalruiz.cartrack.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  @InternalCoroutinesApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
  }
}
