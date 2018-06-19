package com.example.raj.arrpg.type

import arrow.core.identity
import arrow.data.run
import com.example.presentationdemos.type.DIVIDE_BY_ZERO
import com.example.presentationdemos.type.doubleAndAddThree
import com.example.presentationdemos.type.eitherDivide
import com.example.presentationdemos.type.tryDivision
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BasicTypesTest {
    @Test
    fun `test either division`() {
        eitherDivide(1, 0)
                .fold({ assertEquals(DIVIDE_BY_ZERO, it) }, { /* NOP */ })

        eitherDivide(1, 2)
                .fold({ /* NOP */ }, { assertEquals(0.5f, identity(it)) })
    }

    @Test
    fun `test try division`() {
        assertTrue(tryDivision(1, 2).isSuccess())
        assertTrue(tryDivision(1, 0).isFailure())

        tryDivision(1, 0)
                .fold({ println("Error: ${it.message}") }, { /* NOP */ })

        tryDivision(1, 2)
                .fold({ /* NOP */ }, { println("${it.b} / ${it.c} = ${identity(it.a)}") })
    }

    @Test
    fun `test state`() {
        assertEquals(5, doubleAndAddThree().run(1).extract())
    }
}