package com.percivalruiz.cartrack.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.percivalruiz.cartrack.databinding.FragmentUserListBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class UserListFragment : Fragment() {

  private val viewModel: UserListViewModel by stateViewModel()
  private val adapter: UserListAdapter = UserListAdapter { id ->
      findNavController().navigate(UserListFragmentDirections.actionListToDetail(id))
    }

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

    binding.retryButton.setOnClickListener { adapter.retry() }

    binding.swiperefresh.setOnRefreshListener { viewModel.getUsers() }

    // Set up adapter footer, header, and layout
    binding.list.apply {
      adapter = this@UserListFragment.adapter.withLoadStateFooter(
        footer = LoadingAdapter(this@UserListFragment.adapter)
      )

      layoutManager = LinearLayoutManager(requireContext())
    }

    // Handles updating the list
    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      viewModel.users.collectLatest {
        adapter.submitData(it)
      }
    }

    // Updates progressbar, refresh, error toast by observing [Flow<CombinedLoadStates>]
    viewLifecycleOwner.lifecycleScope.launch {
      adapter.loadStateFlow.collectLatest { loadStates ->
        binding.swiperefresh.isRefreshing = loadStates.refresh is LoadState.Loading
        binding.retryButton.isVisible =
          loadStates.refresh !is LoadState.Loading && adapter.itemCount == 0 && loadStates.refresh is LoadState.Error

        if (loadStates.refresh !is LoadState.Loading && loadStates.refresh is LoadState.Error) {
          Toast.makeText(
            requireContext(),
            (loadStates.refresh as? LoadState.Error)?.error?.message,
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }
}
