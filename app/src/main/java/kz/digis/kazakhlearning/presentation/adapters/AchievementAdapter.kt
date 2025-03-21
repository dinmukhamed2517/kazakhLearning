package kz.digis.kazakhlearning.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.google.android.play.integrity.internal.f
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.databinding.ItemAchievementBinding
import kz.digis.kazakhlearning.presentation.base.BaseAchievementViewHolder

class AchievementAdapter:ListAdapter<Achievement, BaseAchievementViewHolder<*>>(AchievementDiffUtils()) {

    class AchievementDiffUtils:DiffUtil.ItemCallback<Achievement>(){
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAchievementViewHolder<*> {
        return AchievementViewHolder(
            ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseAchievementViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class AchievementViewHolder(binding:ItemAchievementBinding):BaseAchievementViewHolder<ItemAchievementBinding>(binding) {
        override fun bindView(item: Achievement) {
            with(binding){
                achievementImage.setImageResource(item.imageId)
                achievementTxt.text = item.title
                if(item.isUnlocked){
                    itemView.alpha = 0.5f
                }
                else{
                    itemView.alpha = 1f
                }
            }
        }
    }
}