package tabacowang.me.taipeizoomvp.ui.plant_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import tabacowang.me.taipeizoomvp.api.model.PlantData
import tabacowang.me.taipeizoomvp.databinding.FragmentPlantDetailBinding

class PlantDetailFragment : Fragment() {

    companion object {
        private val ARGUMENT_PLANT_DATA_STRING = "PLANT_DATA_STRING"

        fun newInstance(plantDataString: String?) =
            PlantDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_PLANT_DATA_STRING, plantDataString)
                }
            }
    }

    private lateinit var plantData: PlantData

    private lateinit var binding: FragmentPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val plantDataString = requireArguments().getString(ARGUMENT_PLANT_DATA_STRING) ?: ""
        plantData = Gson().fromJson(plantDataString, PlantData::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        with(binding) {
            Glide.with(image.context).load(plantData.picUrl).into(image)
            nameCh.text = plantData.nameChBug
            nameEn.text = plantData.nameEn
            alias.text = plantData.nameAlias
            brief.text = plantData.brief
            feature.text = plantData.feature
            application.text = plantData.application
            lastUpdate.text = "最後更新：${plantData.lastUpdate}"
        }

        return binding.root
    }
}