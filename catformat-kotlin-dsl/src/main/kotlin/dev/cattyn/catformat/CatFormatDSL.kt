package dev.cattyn.catformat

import dev.cattyn.catformat.entry.FormatEntry
import dev.cattyn.catformat.stylist.Stylist
import java.awt.Color
import java.util.function.Supplier

// the most useless shit ever

class Config<T>(private val format: CatFormat<T>) {
    var stylist: Stylist<*>
        get() = format.entries().stylist()
        set(value) = format.entries().stylist(value)

    infix fun String.by(color: Supplier<Int>) {
        format.entries().add(FormatEntry(this, color))
    }

    infix fun String.by(color: Int) {
        by { color }
    }

    infix fun String.by(color: Color) {
        by(color.hashCode())
    }
}

fun <T> CatFormat<T>.config(unit: Config<T>.() -> Unit): CatFormat<T> {
    Config(this).unit()
    return this
}