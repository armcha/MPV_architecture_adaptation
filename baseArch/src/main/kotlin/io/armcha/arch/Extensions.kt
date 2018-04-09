package io.armcha.arch

class Predicate(private val predicate: Boolean) {

    infix fun հակառակԴեպքում(action: () -> Unit): Predicate {
        if (!predicate)
            action()
        return Predicate(predicate)
    }

    infix fun հակառակԴեպքում(predicate: Predicate) = predicate
}

fun եթե(predicate: Boolean, action: () -> Unit): Predicate {
    if (predicate)
        action()
    return Predicate(predicate)
}

fun a() {

    val պայման = true
    val երկրորդՊայման = true
    val հաջորդՊայման = true

    եթե(պայման) {
        առաջինՄեթհոդ()
    } հակառակԴեպքում եթե(երկրորդՊայման) {
        երկրորդՄեթհոդ()
    } հակառակԴեպքում եթե(հաջորդՊայման) {
        երրորդՄեթհոդ()
    } հակառակԴեպքում {
        չորրորդՄեթհոդ()
    }
}

fun առաջինՄեթհոդ() {

}

fun երկրորդՄեթհոդ() {

}

fun երրորդՄեթհոդ() {

}

fun չորրորդՄեթհոդ() {

}
