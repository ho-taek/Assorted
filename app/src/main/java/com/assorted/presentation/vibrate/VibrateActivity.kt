package com.assorted.presentation.vibrate

import android.content.Context
import android.os.*
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assorted.R
import com.assorted.databinding.ActivityVibrateBinding
import com.assorted.presentation.base.BaseActivity
import com.assorted.presentation.vibrate.viewmodel.VibrateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class VibrateActivity : BaseActivity<ActivityVibrateBinding>(R.layout.activity_vibrate) {
    private val vibrateViewModel: VibrateViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVibrate()
        clickVibrateButton()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setVibrate() {
        val vibrate = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val timings = longArrayOf(0,100)


        vibrateViewModel.vibrateState.flowWithLifecycle(lifecycle)
            .onEach { value ->

                //진동 변환
                val amplitudes = VibrateValue.values().find { it.value == value }?.setValue()
                Timber.d("진동 울리냐 ${VibrateValue.values()}, $value")
                val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, 0)
                val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
                //뷰 변환
                vibrateViewModel.apply {
                    if(vibrateUpDown) {
                        Timber.d("그림true $value")
                        changeTrueVibrateView(value)
                        upVibrateView(value).isSelected = true
                    } else {
                        changeFalseVibrateView(value)
                        Timber.d("그림false $value")
                        downVibrateView(value+1).isSelected = false
                    }
                }
                vibrate.vibrate(combinedVibration)
                if(value == 0){
                    vibrate.cancel()
                }
            }
            .launchIn(lifecycleScope)
    }
    //진동 버튼 클릭
    private fun clickVibrateButton() {
        binding.btnVibrateUp.setOnClickListener {
            with(vibrateViewModel){
                vibrateUpDown = true
                plusVibrateState()
            }
        }

        binding.btnVibrateDown.setOnClickListener {
            with(vibrateViewModel){
                vibrateUpDown = false
                minusVibrateState()
            }
        }
    }
    //뷰 변화
    private fun downVibrateView(index : Int) : ImageView{
         return when(index){
             0 -> binding.btnVibrateDown
            1 -> binding.imgVibrateFirst
            2 -> binding.imgVibrateSecond
            3 -> binding.imgVibrateThird
            else -> binding.imgVibrateFourth
        }
    }
    private fun upVibrateView(index : Int) : ImageView{
        return when(index){
            0 -> binding.btnVibrateDown
            1 -> binding.imgVibrateFirst
            2 -> binding.imgVibrateSecond
            3 -> binding.imgVibrateThird
            else -> binding.imgVibrateFourth
        }
    }
}