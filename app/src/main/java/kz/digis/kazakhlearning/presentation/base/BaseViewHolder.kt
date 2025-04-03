package kz.digis.kazakhlearning.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.data.models.Choice

abstract class BaseViewHolder<VB : ViewBinding, T>(protected open val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bindView(item: T)
}

abstract class BaseCategoryViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Category>(binding)


abstract class BaseAchievementViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Achievement>(binding)
abstract class BaseMessageViewHolder<VB : ViewBinding>(override val binding: VB) :
    BaseViewHolder<VB, Choice>(binding)