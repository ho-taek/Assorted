package com.assorted.presentation.light

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class Torch @Inject constructor(
    @ActivityContext private val context: Context
) {
    private var cameraId: String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        cameraId = getCameraId()
    }

    fun flashCheck(value: Boolean) {
        cameraManager.setTorchMode(cameraId.toString(), value)
    }

    private fun getCameraId(): String? {
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)

            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)
            if (flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK
            ) return id
        }
        return null
    }
}