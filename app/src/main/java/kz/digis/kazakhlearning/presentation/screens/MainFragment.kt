package kz.digis.kazakhlearning.presentation.screens

import androidx.navigation.fragment.findNavController
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.databinding.FragmentMainBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment

class MainFragment:BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {


    override var showBottomNavigation = false
    override fun onBindView() {
        super.onBindView()
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }
        binding.haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }


}