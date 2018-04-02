package io.armcha.arch

fun `if`(predicate: Boolean?, action: () -> Unit) {
    if (predicate == true) {
        action.invoke()
    }
}

fun եթե(predicate: Boolean, action: () -> Unit): Boolean {
    if (predicate) {
        action()
    }
    return predicate
}

infix fun Boolean.հակառակԴեպքում(action: () -> Unit) {
    if (!this)
        action()
}
