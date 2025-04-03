package kz.digis.kazakhlearning.presentation.screens

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.CategoryDao
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.databinding.FragmentCategoryBinding
import kz.digis.kazakhlearning.databinding.FragmentGrammarCategoryBinding
import kz.digis.kazakhlearning.presentation.adapters.CategoryAdapter
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class GrammarCategoryFragment: BaseFragment<FragmentGrammarCategoryBinding>(FragmentGrammarCategoryBinding::inflate) {
    @Inject
    lateinit var categoryDao: CategoryDao

    private val categoryList:List<Category> = listOf(
        Category(2, R.drawable.category2, "Цифры"),
        Category(3, R.drawable.category3, "Приветствия и прощания"),
        Category(4, R.drawable.category4, "Семья"),
        Category(5, R.drawable.category5, "Еда"),
        Category(6, R.drawable.category6, "Цвета"),
        Category(7, R.drawable.category7, "Время и дни недели"),
        Category(8, R.drawable.category8, "Животные"),
        Category(9, R.drawable.category9, " Часто употребляемые глаголы"),

        )
    override fun onBindView() {
        super.onBindView()

        val adapter = CategoryAdapter()
        binding.categoryRecycler.adapter = adapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitFullList(categoryList)

        binding.searchInput.addTextChangedListener { text ->
            adapter.filter(text.toString())
        }

        adapter.itemClick = { category ->
            lifecycleScope.launch {
                categoryDao.insertCategory(category)
            }
            val action = GrammarCategoryFragmentDirections.actionGrammarCategoryFragmentToGrammarFragment(category.categoryTitle)
            findNavController().navigate(action)
        }
    }


}