package com.example.newsapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.saved_news.SavedNewsFragment
import com.example.newsapp.ui.top_headlines.TopHeadlinesFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //using navigation component and bottom navigation is recreating the fragments
        //which is not good
        //I can't find the solution cuz google haven't fixed for that, yet
        //so, have to use the old way

        val fragment1 = TopHeadlinesFragment()
        val fragment2 = SavedNewsFragment()
        val fm = supportFragmentManager
        var active: Fragment = fragment1
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit()
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit()

        binding.bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.topHeadlinesFragment -> {
                        fm.beginTransaction().hide(active).show(fragment1).commit()
                        active = fragment1
                        return true
                    }

                    R.id.savedNewsFragment -> {
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    }
                }
                return false
            }
        })
    }
}