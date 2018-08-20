package io.github.armcha.architecturesampleproject.ui.util

infix fun <P> Boolean.ifTrue(param: P): P? = if (this) param else null

infix fun <P> P.then(param: P): P? = this ?: param

fun main(args: Array<String>) {

    val someBool = true
    println(someBool ifTrue "TRUE" then "FALSE")
}