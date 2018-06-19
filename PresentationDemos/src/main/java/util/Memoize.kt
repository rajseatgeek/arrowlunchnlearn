package util

fun <T, R> ((T) -> R).memoize(): (T) -> R = object : (T) -> R {
    val values: MutableMap<T, R> = mutableMapOf()

    override fun invoke(t: T): R = values.getOrPut(t, { this@memoize(t) })
}