package io.armcha.arch

fun `if`(predicate: Boolean?, action: () -> Unit) {
    if (predicate == true) {
        action.invoke()
    }
}
