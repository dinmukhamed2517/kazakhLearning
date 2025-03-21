package kz.digis.kazakhlearning.presentation.screens

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.databinding.FragmentTimePickerBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import kz.digis.kazakhlearning.presentation.viewmodels.SharedViewModel
import javax.inject.Inject


@AndroidEntryPoint
class TimePickerFragment:BaseFragment<FragmentTimePickerBinding>(FragmentTimePickerBinding::inflate) {

    private val viewModel: SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var storageReference: StorageReference

    @Inject
    lateinit var userDao: UserDao

    private var chosenTime:Int = 0
    override fun onBindView() {
        with(binding) {
            numberPicker.maxValue = 120
            numberPicker.minValue = 1
            numberPicker.wrapSelectorWheel = false
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                chosenTime = newVal
            }
            nextBtn.setOnClickListener {
                userDao.saveAppTime(chosenTime)
                findNavController().navigate(R.id.action_timePickerFragment_to_homeFragment)
            }
        }

    }



}