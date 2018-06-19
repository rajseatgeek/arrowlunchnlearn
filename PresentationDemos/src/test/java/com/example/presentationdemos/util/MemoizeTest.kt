package com.example.presentationdemos.util

import org.junit.Assert.assertEquals
import org.junit.Test
import util.memoize

class MemoizeTest {
    @Test
    fun `test memoizing`() {
        val double = { t: Int ->
            println("doubling $t")
            t * 2
        }.memoize()

        assertEquals(4, double(2))
        assertEquals(4, double(2))
    }
}