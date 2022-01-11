package tabacowang.me.taipeizoomvp.ui.house_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import tabacowang.me.taipeizoomvp.R
import tabacowang.me.taipeizoomvp.api.model.HouseData
import tabacowang.me.taipeizoomvp.databinding.ActivityHouseDetailBinding
import tabacowang.me.taipeizoomvp.util.replaceFragment
import tabacowang.me.taipeizoomvp.util.setupActionBar

class HouseDetailActivity : AppCompatActivity() {
    companion object {
        const val HOUSE_NAME = "HOUSE_NAME"
        const val HOUSE_DATA = "HOUSE_DATA"
    }

    private lateinit var houseDetailPresenter: HouseDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHouseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val houseName = intent.getStringExtra(HOUSE_NAME)
        val houseDataString = intent.getStringExtra(HOUSE_DATA)
//        val houseData = Gson().fromJson(houseDataString, HouseData::class.java)

        // Set up the toolbar.
        setupActionBar(binding.toolbar.id) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

            title = houseName
        }

        val houseDetailFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            as HouseDetailFragment?
            ?: HouseDetailFragment.newInstance(houseDataString).also {
                replaceFragment(it, binding.fragmentContainer.id)
            }

        houseDetailPresenter = HouseDetailPresenter(houseDetailFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}