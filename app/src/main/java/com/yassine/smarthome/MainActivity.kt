package com.yassine.smarthome

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.yassine.smarthome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var controller : NavController
    companion object {
        val numberOFItemsInRow = 3;
        val displayAdAfterNumberOfRows = 2;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar();
    }

    private fun initBottomBar() {
         controller = findNavController(R.id.navFragment)
         binding.bottomNavigationBar.setupWithNavController(controller)
    }
    private fun initWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.statue_bar_back)
    }
}