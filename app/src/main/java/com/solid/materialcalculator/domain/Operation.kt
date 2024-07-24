package com.solid.materialcalculator.domain

enum class Operation(val symbol: Char) {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('x'),
    DIVIDE('/'),
    MODULUS('%')
}

val operationSymbols = Operation.entries.map{it.symbol}.joinToString ( "" )

fun operationFromSymbol(symbol: Char): Operation {
    return Operation.entries.find { it.symbol == symbol } ?: throw IllegalArgumentException("Invalid symbol")
}