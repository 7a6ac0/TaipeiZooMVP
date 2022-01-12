package tabacowang.me.taipeizoomvp.ui.house_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import tabacowang.me.taipeizoomvp.MainActivity
import tabacowang.me.taipeizoomvp.R
import tabacowang.me.taipeizoomvp.api.model.HouseData
import tabacowang.me.taipeizoomvp.api.model.PlantData
import tabacowang.me.taipeizoomvp.databinding.FragmentHouseDetailBinding
import tabacowang.me.taipeizoomvp.ui.plant_detail.PlantDetailFragment
import tabacowang.me.taipeizoomvp.util.showSnackBar

class HouseDetailFragment : Fragment(), HouseDetailContract.View,
    HouseDetailAdapter.HouseDetailItemListener {
    override var isActive: Boolean = false
        get() = isAdded

    companion object {
        private val ARGUMENT_HOUSE_DATA_STRING = "HOUSE_DATA_STRING"

        fun newInstance(houseDataString: String?) =
            HouseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_HOUSE_DATA_STRING, houseDataString)
                }
            }
    }

    private lateinit var houseData: HouseData

    private lateinit var listAdapter: HouseDetailAdapter

    private lateinit var binding: FragmentHouseDetailBinding

    private lateinit var presenter: HouseDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val houseDataString = requireArguments().getString(ARGUMENT_HOUSE_DATA_STRING) ?: ""
        houseData = Gson().fromJson(houseDataString, HouseData::class.java)

        listAdapter = HouseDetailAdapter(houseData, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHouseDetailBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).setActionBar {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            setDisplayHomeAsUpEnabled(true)

            title = houseData.name
        }

        with(binding) {
            plantsRecycleview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HouseDetailPresenter(this)
        presenter.loadPlantList(houseData.name ?: "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (!isActive) return

        if (active) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun showHouseDetailInfo(list: List<PlantData>) {
        listAdapter.plantItems = list
    }

    override fun showLoadingHouseDetailError() {
        showMessage(getString(R.string.message_load_plant_error))
    }

    override fun showLoadingHouseDetailSuccess() {
        showMessage(getString(R.string.message_load_plant_success))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    override fun onWebClicked(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    override fun onItemClicked(plantData: PlantData) {
        val dataString = Gson().toJson(plantData)
        (requireActivity() as MainActivity).replaceFragmentWithTag(PlantDetailFragment.newInstance(dataString), javaClass.name)
    }
}