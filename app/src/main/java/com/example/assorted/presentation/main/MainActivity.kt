package com.example.assorted.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.assorted.R
import com.example.assorted.databinding.ActivityMainBinding
import com.example.assorted.presentation.base.BaseActivity
import com.example.assorted.presentation.light.Torch
import com.example.assorted.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity  : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    @Inject lateinit var torch : Torch
    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickLight()
        setLight()
    }


    //손전등 클릭 이벤트
    private fun clickLight(){
        binding.btnLight.setOnClickListener {
            mainViewModel.setLightCheck()
        }
    }

    //손전등 관찰
    private fun setLight(){
        mainViewModel.lightCheck.flowWithLifecycle(lifecycle)
            .onEach {
                torch.flashCheck(it)
                binding.btnLight.isSelected = it
            }
            .launchIn(lifecycleScope)
    }
}