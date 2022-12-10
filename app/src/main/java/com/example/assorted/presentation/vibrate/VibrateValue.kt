package com.example.assorted.presentation.vibrate

enum class VibrateValue(val value: Int) {
    ZERO(0){
        override fun setValue(): IntArray = intArrayOf(0,0)
    },
    FIRST(1) {
        override fun setValue(): IntArray = intArrayOf(0,100)
    },
    SECOND(2) {
        override fun setValue(): IntArray = intArrayOf(0, 150)
    },
    THIRD(3) {
        override fun setValue(): IntArray = intArrayOf(0, 200)
    },
    FOURTH(4) {
        override fun setValue(): IntArray = intArrayOf(0, 255)
    };

    abstract fun setValue(): IntArray


}