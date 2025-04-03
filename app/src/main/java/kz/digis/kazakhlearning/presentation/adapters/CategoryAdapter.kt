package kz.digis.kazakhlearning.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.databinding.ItemCategoryBinding
import kz.digis.kazakhlearning.presentation.base.BaseCategoryViewHolder

class CategoryAdapter: ListAdapter<Category, BaseCategoryViewHolder<*>>(CategoryDiffUtils()) {


    var itemClick:((Category) -> Unit)? = null
    private var originalList = listOf<Category>()

    fun submitFullList(list: List<Category>) {
        originalList = list
        submitList(list)
    }

    fun filter(query: String) {
        val filtered = if (query.isBlank()) {
            originalList
        } else {
            originalList.filter {
                it.categoryTitle.contains(query, ignoreCase = true)
            }
        }
        submitList(filtered)
    }
    class CategoryDiffUtils:DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCategoryViewHolder<*> {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseCategoryViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class CategoryViewHolder(binding:ItemCategoryBinding):BaseCategoryViewHolder<ItemCategoryBinding>(binding) {
        override fun bindView(item: Category) {
            with(binding){
                categoryIcon.setImageResource(item.imageId)
                categoryText.text = item.categoryTitle

            }
            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }

}