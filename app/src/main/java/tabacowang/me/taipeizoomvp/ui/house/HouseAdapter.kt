package tabacowang.me.taipeizoomvp.ui.house

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tabacowang.me.taipeizoomvp.R
import tabacowang.me.taipeizoomvp.api.model.HouseData
import tabacowang.me.taipeizoomvp.databinding.ItemHouseBinding

class HouseAdapter(
    private val listener: HouseItemListener
) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    private val list = AsyncListDiffer(this, DiffCallback)
    var houseItems: List<HouseData> = emptyList()
        set(value) {
            field = value
            list.submitList(value)
        }

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        return HouseViewHolder(ItemHouseBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val houseData = list.currentList[position]
        val binding = holder.binding

        Glide.with(binding.image.context).load(houseData.picUrl).into(binding.image)
        binding.title.text = houseData.name
        binding.description.text = houseData.info
        binding.memo.text = if (houseData.memo.isNullOrBlank()) {
            mContext.getString(R.string.taipei_zoo_no_close_info)
        } else {
            houseData.memo
        }

        holder.itemView.setOnClickListener {
            listener.onItemClicked(houseItems[position])
        }
    }

    override fun getItemCount() = houseItems.size

    inner class HouseViewHolder(val binding: ItemHouseBinding): RecyclerView.ViewHolder(binding.root)

    interface HouseItemListener {
        fun onItemClicked(houseData: HouseData)
    }
}

object DiffCallback : DiffUtil.ItemCallback<HouseData>() {

    override fun areItemsTheSame(oldItem: HouseData, newItem: HouseData): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: HouseData, newItem: HouseData): Boolean {
        return oldItem == newItem
    }
}