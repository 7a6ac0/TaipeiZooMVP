package tabacowang.me.taipeizoomvp.ui.plant_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tabacowang.me.taipeizoomvp.databinding.ActivityPlantDetailBinding
import tabacowang.me.taipeizoomvp.util.replaceFragment
import tabacowang.me.taipeizoomvp.util.setupActionBar

class PlantDetailActivity : AppCompatActivity() {
    companion object {
        const val PLANT_NAME = "PLANT_NAME"
        const val PLANT_DATA = "PLANT_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantName = intent.getStringExtra(PLANT_NAME)
        val plantDataString = intent.getStringExtra(PLANT_DATA)

        // Set up the toolbar.
        setupActionBar(binding.toolbar.id) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

            title = plantName
        }

        supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            as PlantDetailFragment?
            ?: PlantDetailFragment.newInstance(plantDataString).also {
                replaceFragment(it, binding.fragmentContainer.id)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}