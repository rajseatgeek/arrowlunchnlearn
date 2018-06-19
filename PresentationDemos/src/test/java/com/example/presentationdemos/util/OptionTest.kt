package com.example.presentationdemos.util

import com.example.presentationdemos.type.Option
import com.example.presentationdemos.type.ap
import com.example.presentationdemos.type.apWithFlatMap
import com.example.presentationdemos.type.flatMap
import com.example.presentationdemos.type.map
import com.example.presentationdemos.type.mapWithFlatMap
import org.junit.Assert.assertEquals
import org.junit.Test

class OptionTest {
    @Test
    fun `test map`() {
        assertEquals(Option.None, Option.None.map { it: Int -> it * 2 })
        assertEquals(Option.Some(4), Option.pure(2).map { it * 2 })
    }

    @Test
    fun `test flatMap`() {
        assertEquals(Option.None, Option.None.flatMap { it: Int -> Option.pure(it * 2) })
        assertEquals(Option.Some(4), Option.pure(2).flatMap { Option.pure(it * 2) })
        assertEquals(Option.Some(6), Option.pure(2).flatMap { Option.pure(it * 2) }.flatMap { Option.pure(it + 2) })
    }

    @Test
    fun `test mapWithFlatMap`() {
        assertEquals(Option.None, Option.None.mapWithFlatMap { it: Int -> it * 2 })
        assertEquals(Option.Some(4), Option.Some(2).mapWithFlatMap { it * 2 })
    }

    @Test
    fun `test pure`() {
        assertEquals(Option.Some(2), Option.pure(2))
    }

    @Test
    fun `test ap`() {
        assertEquals(Option.Some(4), Option.Some(2).ap(Option.pure({ it: Int -> 2 * it })))
    }

    @Test
    fun `test apWithFlatMap`() {
        assertEquals(Option.Some(4), Option.Some(2).apWithFlatMap(Option.Some({ it: Int -> 2 * it })))
    }

    @Test
    fun `test sum with flatMap`() {
        assertEquals(
                Option.Some(3),
                Option.pure(1).flatMap { t: Int ->
                    Option.pure(2)
                            .map {
                                it + t
                            }
                }
        )
    }

    @Test
    fun `test sum with ap`() {
        assertEquals(
                Option.Some(3),
                Option.pure(1).ap(
                        Option.pure(2)
                                .map { it ->
                                    { t: Int -> it + t }
                                }
                )
        )
    }
}