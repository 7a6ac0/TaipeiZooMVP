package tabacowang.me.taipeizoomvp.ui.house_detail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tabacowang.me.taipeizoomvp.R
import tabacowang.me.taipeizoomvp.api.model.HouseData
import tabacowang.me.taipeizoomvp.api.model.PlantData
import tabacowang.me.taipeizoomvp.databinding.ItemHouseDetailHeaderBinding
import tabacowang.me.taipeizoomvp.databinding.ItemHouseDetailPlantBinding

class HouseDetailAdapter(
    private val houseData: HouseData,
    private val listener: HouseDetailItemListener
) : RecyclerView.Adapter<HouseDetailViewHolder>() {

    private val list = AsyncListDiffer(this, DiffCallback)
    var plantItems: List<PlantData> = emptyList()
        set(value) {
            field = value
            list.submitList(generatorList())
        }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseDetailViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_house_detail_header -> HouseDetailViewHolder.HouseDetailHeader(
                ItemHouseDetailHeaderBinding.inflate(inflater, parent, false)
            )
            R.layout.item_house_detail_plant -> HouseDetailViewHolder.HouseDetailPlantInfo(
                ItemHouseDetailPlantBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: HouseDetailViewHolder, position: Int) {
        when (holder) {
            is HouseDetailViewHolder.HouseDetailHeader -> holder.binding.apply {
                Glide.with(image.context).load(houseData.picUrl).into(image)
                houseInfo.text = houseData.info
                houseMemo.text = if (houseData.memo.isNullOrBlank()) {
                    mContext.getString(R.string.taipei_zoo_no_close_info)
                } else {
                    houseData.memo
                }
                houseCategory.text = houseData.category
                houseWebUrl.setOnClickListener { houseData.webUrl?.let { url -> listener.onWebClicked(url) } }
            }
            is HouseDetailViewHolder.HouseDetailPlantInfo -> {
                holder.binding.apply {
                    Glide.with(image.context).load(plantItems[position - 1].picUrl).into(image)
                    title.text = plantItems[position - 1].nameChBug
                    description.text = plantItems[position - 1].nameAlias

                }
                holder.itemView.setOnClickListener { listener.onItemClicked(plantItems[position - 1]) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list.currentList[position]) {
            is HouseData -> R.layout.item_house_detail_header
            is PlantData -> R.layout.item_house_detail_plant
            else -> throw IllegalStateException("Unknown view type at position $position")
        }
    }

    override fun getItemCount() = list.currentList.size

    private fun generatorList(
        itemList: List<PlantData> = plantItems
    ) : List<Any> {
        val merged = mutableListOf<Any>()
        merged += houseData
        merged.addAll(itemList)

        return merged
    }

    interface HouseDetailItemListener {
        fun onWebClicked(url: String)
        fun onItemClicked(plantData: PlantData)
    }
}

object DiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is HouseData && newItem is HouseData -> oldItem._id == newItem._id
            oldItem is PlantData && newItem is PlantData -> oldItem._id == newItem._id
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is HouseData && newItem is HouseData -> oldItem == newItem
            oldItem is PlantData && newItem is PlantData -> oldItem == newItem
            else -> false
        }
    }
}

sealed class HouseDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    class HouseDetailHeader(
        val binding: ItemHouseDetailHeaderBinding
    ) : HouseDetailViewHolder(binding.root)

    class HouseDetailPlantInfo(
        val binding: ItemHouseDetailPlantBinding
    ) : HouseDetailViewHolder(binding.root)
}