package com.raka.trendinggitwithdaggerhilt.view.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.raka.trendinggitwithdaggerhilt.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * lINK FOR TUTORIAL
 * https://medium.com/@er.ankitbisht/mvvm-model-view-viewmodel-kotlin-google-jetpack-f02ec7754854
 * Dagger Hilt
 * https://blog.mindorks.com/dagger-hilt-tutorial
 * https://proandroiddev.com/exploring-dagger-hilt-and-whats-main-differences-with-dagger-android-c8c54cd92f18
 * Paging Library
 * https://androidwave.com/android-paging-library/
 * https://medium.com/@harunwangereka/android-paging-library-with-kotlin-coroutines-b96602e3fae3
 * https://blog.mindorks.com/implementing-paging-library-in-android
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.main_nav_fragment))
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_fragment).navigateUp()
}
