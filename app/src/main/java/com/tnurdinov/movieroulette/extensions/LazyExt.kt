package com.tnurdinov.movieroulette.extensions

fun <T> lazyUnsynchronized(initializer: () -> T): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE, initializer)