package com.assorted.presentation


fun main(){

    val exampleData1 = ExampleData("알파카")
    val exampleData2 = ExampleData("알파카")
    println(exampleData1.equals(exampleData2))
    println(exampleData1.hashCode())
    println(exampleData2.hashCode())
    println(exampleData1.hashCode() == exampleData2.hashCode())
}