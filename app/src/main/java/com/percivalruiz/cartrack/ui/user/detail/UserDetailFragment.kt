package com.percivalruiz.cartrack.ui.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.percivalruiz.cartrack.databinding.FragmentUserDetailBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class UserDetailFragment : Fragment() {

  private var _binding: FragmentUserDetailBinding? = null
  private val args: UserDetailFragmentArgs by navArgs()
  private val viewModel: UserDetailViewModel by stateViewModel()
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      // Get user id from navArgs
      viewModel.getUser(args.id)

      // Set-up UI contents
      viewModel.user.collectLatest {
        it?.run {
          requireActivity().toolbar.title = name
          binding.username.text = username
          binding.email.text = email
          binding.phone.text = phone
          binding.website.text = website
          binding.workName.text = company.name
          binding.workCatchPhrase.text = company.catchPhrase
          binding.workBs.text = company.bs
          binding.address.text =
            "${address.suite}, ${address.street}, ${address.city}, ${address.zipcode}"

        }
      }
    }
  }
}
