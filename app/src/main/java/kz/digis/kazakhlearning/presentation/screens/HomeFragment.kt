package kz.digis.kazakhlearning.presentation.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.CategoryDao
import kz.digis.kazakhlearning.data.local.LocalWordProvider.wordList
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.data.models.WordCard
import kz.digis.kazakhlearning.databinding.FragmentHomeBinding
import kz.digis.kazakhlearning.presentation.adapters.CategoryAdapter
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private val categoryList:List<Category> = listOf(
        Category(111, R.drawable.letter, "Буквы"),
        Category(2, R.drawable.category2, "Цифры"),
        Category(3, R.drawable.category3, "Приветствия и прощания"),
        )


    @Inject lateinit var categoryDao: CategoryDao
    @Inject lateinit var firebaseAuth: FirebaseAuth

    override fun onBindView() {
        super.onBindView()

        val defaultAdapter =CategoryAdapter()
        val adapter = CategoryAdapter()
        binding.chosenCategoryRecycler.adapter = adapter
        binding.chosenCategoryRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.defaultCategoryRecycler.adapter = defaultAdapter
        binding.defaultCategoryRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        defaultAdapter.submitList(categoryList)
        categoryDao.getAllChosenCategories().observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
            binding.continueText.isVisible = categories.isNotEmpty()
        }
        defaultAdapter.itemClick = {category ->
            val action = HomeFragmentDirections.actionHomeFragmentToCategoryCardFragment(category.categoryTitle)
            findNavController().navigate(action)
        }
        adapter.itemClick = { category ->
            val action = HomeFragmentDirections.actionHomeFragmentToCategoryCardFragment(category.categoryTitle)
            findNavController().navigate(action)

        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(R.id.action_homeFragment_to_mainFragment)
        }
    }
}