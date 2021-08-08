package com.percivalruiz.cartrack.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.percivalruiz.cartrack.databinding.FragmentUserListBinding
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

  private val viewModel: UserListViewModel by viewModel()

  private var _binding: FragmentUserListBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUserListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.retryButton.setOnClickListener {
      findNavController().navigate(UserListFragmentDirections.actionListToDetail(1))
    }

    viewModel.getUsers()
  }
}
