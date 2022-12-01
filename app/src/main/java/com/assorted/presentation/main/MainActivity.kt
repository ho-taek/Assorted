package com.assorted.presentation.main

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assorted.R
import com.assorted.databinding.ActivityMainBinding
import com.assorted.presentation.base.BaseActivity
import com.assorted.presentation.light.Torch
import com.assorted.presentation.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    @Inject
    lateinit var torch: Torch
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var appUpdateManager: AppUpdateManager

    //업데이트 상태 확인
    private var installStateUpdatedListener: InstallStateUpdatedListener = object :
        InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (appUpdateManager != null) {
                    appUpdateManager?.unregisterListener(this)

                }
            } else {
                // 예외 처리
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickLight()
        setLight()
        updateVersion()
        setPrivacyUri()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // 인 앱 업데이트가 실행된 상태라면 계속 업데이트 진행
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        when (resultCode) {
            UPDATE -> {
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "업데이트가 취소 되었습니다.", Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }
            else -> {

            }
        }
    }

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE, // or AppUpdateType.IMMEDIATE
                this,
                UPDATE
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    //snackbar
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            binding.crMain,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            setActionTextColor(ResourcesCompat.getColor(resources, R.color.teal_700, null))
            show()
        }
    }

    //버전 업데이트
    private fun updateVersion() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateManager.registerListener(installStateUpdatedListener)

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

                    requestUpdate(appUpdateInfo)
                }
                else -> {
                }
            }
        }
    }


    //손전등 클릭 이벤트
    private fun clickLight() {
        binding.btnLight.setOnClickListener {
            mainViewModel.setLightCheck()
        }
    }

    //손전등 관찰
    private fun setLight() {
        mainViewModel.lightCheck.flowWithLifecycle(lifecycle)
            .onEach {
                torch.flashCheck(it)
                binding.btnLight.isSelected = it
            }
            .launchIn(lifecycleScope)
    }

    fun setPrivacyUri() {
        binding.textPrivacy.setOnClickListener {
            val uri = Uri.parse(getString(R.string.privacy_uri))
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    companion object {
        const val UPDATE = 225

    }
}