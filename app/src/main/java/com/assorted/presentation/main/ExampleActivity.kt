package com.assorted.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assorted.R
import com.assorted.databinding.ActivityExampleBinding
import com.assorted.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExampleActivity : BaseActivity<ActivityExampleBinding>(R.layout.activity_example) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch{
            setExample()
        }

    }

    private suspend fun setExample(){

        val deffer = CoroutineScope(Dispatchers.Main).async {
            (1..500).sortedByDescending{it}
        }

        val job1 = CoroutineScope(Dispatchers.Main).launch {
            println("dodo 1실행")

            val job3Result = deffer.await()


            job3Result.forEach {
                println("dodo"+ it)
            }

            println("dodo 1-2실행")
        }

        val job2 = CoroutineScope(Dispatchers.Main).launch {
            println("dodo 2실행됨")
        }
        job1.join()
        job2.join()

    }
}