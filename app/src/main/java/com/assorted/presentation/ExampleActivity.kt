package com.assorted.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assorted.R
import com.assorted.databinding.ActivityExampleBinding
import com.assorted.presentation.base.BaseActivity
import timber.log.Timber

class ExampleActivity : BaseActivity<ActivityExampleBinding>(R.layout.activity_example) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startThread()
    }


    fun startThread(){
        binding.btnStart.setOnClickListener{
            for(i in 0..10){
                Timber.d("초 확인 $i")
                Thread.sleep(1000)
            }
        }
    }
}