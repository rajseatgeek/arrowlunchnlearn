package com.example.presentationdemos.type

import arrow.core.andThen
import arrow.core.compose
import arrow.syntax.function.bind
import arrow.syntax.function.forwardCompose
import arrow.syntax.function.pipe
import org.junit.Assert.assertEquals
import org.junit.Test

class BasicFunctionsTest {
    @Test
    fun `basic functions`() {
        assertEquals(8, (doubleIt() compose addThree())(1))
        assertEquals(8, (addThree().bind(1).invoke() pipe doubleIt()))

        assertEquals(5, (doubleIt() andThen addThree())(1))
        assertEquals(5, (doubleIt() forwardCompose addThree())(1))
    }
}