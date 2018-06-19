package com.example.presentationdemos.type

import arrow.syntax.function.curried
import arrow.syntax.function.partially1

private val add = { x: Int, y: Int -> x + y }

fun addThree() = add.partially1(3)

private val mult: ((Int, Int) -> Int) = { x: Int, y: Int -> x * y }

fun doubleIt(): (Int) -> Int = mult.curried()(2)
