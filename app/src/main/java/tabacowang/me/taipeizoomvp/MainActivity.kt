package tabacowang.me.taipeizoomvp

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import tabacowang.me.taipeizoomvp.databinding.ActivityMainBinding
import tabacowang.me.taipeizoomvp.ui.house.HouseFragment
import tabacowang.me.taipeizoomvp.ui.house.HousePresenter
import tabacowang.me.taipeizoomvp.util.replaceFragment
import tabacowang.me.taipeizoomvp.util.replaceFragmentWithTag
import tabacowang.me.taipeizoomvp.util.setupActionBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            as HouseFragment?
            ?: replaceFragment(HouseFragment.newInstance())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStackImmediate()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(fragment: Fragment) {
        replaceFragment(fragment, binding.fragmentContainer.id)
    }

    fun replaceFragmentWithTag(fragment: Fragment, tag: String) {
        replaceFragmentWithTag(fragment, binding.fragmentContainer.id, tag)
    }

    fun setActionBar(action: ActionBar.() -> Unit) {
        setupActionBar(binding.toolbar.id) {
            action()
        }
    }
}