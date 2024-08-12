package com.assorted.presentation

abstract class abstractOne {

}

abstract class abstractTwo {
    abstract fun checkAssort()
}

interface interfaceOne{}

interface interfaceTwo{}





class Interface() : interfaceOne, interfaceTwo, abstractTwo(){
    override fun checkAssort() {
        TODO("Not yet implemented")
    }
}