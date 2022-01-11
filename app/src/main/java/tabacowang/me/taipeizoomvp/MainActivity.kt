package tabacowang.me.taipeizoomvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tabacowang.me.taipeizoomvp.databinding.ActivityMainBinding
import tabacowang.me.taipeizoomvp.ui.house.HouseFragment
import tabacowang.me.taipeizoomvp.ui.house.HousePresenter
import tabacowang.me.taipeizoomvp.util.replaceFragment
import tabacowang.me.taipeizoomvp.util.setupActionBar

class MainActivity : AppCompatActivity() {
    private lateinit var housePresenter: HousePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar(binding.toolbar.id) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        val houseFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            as HouseFragment?
            ?: HouseFragment.newInstance().also {
                replaceFragment(it, binding.fragmentContainer.id)
            }

        housePresenter = HousePresenter(houseFragment)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}