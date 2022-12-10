package com.assorted.presentation.vibrate

import android.os.Bundle
import com.assorted.R
import com.assorted.databinding.ActivityVibrateBinding
import com.example.assorted.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VibrateActivity : BaseActivity<ActivityVibrateBinding>(R.layout.activity_vibrate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}