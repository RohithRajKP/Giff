package com.freshworks.giff

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.freshworks.giff.databinding.ActivityMainBinding
import com.freshworks.giff.ui.viewmodel.MainViewModel


/*
Structure of the app is one activity with 2 fragmnet
1st fragment is trending fragment //for trending giff and search giff
2nd fragment is favorite fragment // for locally stored fragment as favorites
view pager and view model is used for binding the fragmnts to activity
please refer main view model and pager adapter
 */

class MainActivity : AppCompatActivity() {
    private val viewModel = createVm()
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).setVariable(BR.viewModel, viewModel)
    }

    private fun createVm() = MainViewModel(object : MainViewModel.MainActivityContract {
        override fun getFragmentManger(): FragmentManager = supportFragmentManager
    })

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            finish()
            return
        } else {
            backToast= Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT)
            backToast!!.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
