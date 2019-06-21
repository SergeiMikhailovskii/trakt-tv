package com.mikhailovskii.trakttv.data.entity

class Optional<M>(private val optional: M?) {

    val isEmpty: Boolean
        get() = this.optional == null

    fun get(): M? {
        return optional
    }

}
