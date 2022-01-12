package tabacowang.me.taipeizoomvp.ui.house

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
import tabacowang.me.taipeizoomvp.databinding.FragmentHouseBinding
import tabacowang.me.taipeizoomvp.ui.house_detail.HouseDetailFragment
import tabacowang.me.taipeizoomvp.util.showSnackBar

class HouseFragment : Fragment(), HouseContract.View, HouseAdapter.HouseItemListener {
    override var isActive: Boolean = false
        get() = isAdded

    companion object {
        fun newInstance() = HouseFragment()
    }

    private val listAdapter by lazy { HouseAdapter(this) }

    private lateinit var binding: FragmentHouseBinding

    private lateinit var presenter: HousePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHouseBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).setActionBar {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)

            title = getString(R.string.taipei_zoo)
        }

        with(binding) {
            houseRecycleview.apply {
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
        presenter = HousePresenter(this)
        presenter.start()
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

    override fun showHouseList(list: List<HouseData>) {
        if (list.isNotEmpty()) {
            listAdapter.houseItems = list
        }
    }

    override fun showLoadingHouseError() {
        showMessage(getString(R.string.message_load_house_error))
    }

    override fun showLoadingHouseSuccess() {
        showMessage(getString(R.string.message_load_house_success))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    override fun showHouseDetail(houseData: HouseData) {
        val dataString = Gson().toJson(houseData)
        (requireActivity() as MainActivity).replaceFragmentWithTag(HouseDetailFragment.newInstance(dataString), javaClass.name)
    }

    override fun onItemClicked(houseData: HouseData) {
        showHouseDetail(houseData)
    }
}