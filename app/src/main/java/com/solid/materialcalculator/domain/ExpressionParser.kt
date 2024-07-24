package com.solid.materialcalculator.domain

class ExpressionParser(
    val calculation: String
) {
    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var i = 0
        while (i < calculation.length){
            val currChar = calculation[i]
            when{
                currChar in operationSymbols -> {
                    result.add(ExpressionPart.Op(operationFromSymbol(currChar)))
                }
                currChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                currChar in "()" -> {
                    parseParentheses(currChar, result)
                }
            }
            i++
        }

       return result
    }

    private fun parseParentheses(currChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parentheses(
                when(currChar){
                    '(' -> {ParenthesesType.Opening}
                    ')' -> {ParenthesesType.Closing}
                    else -> {throw IllegalArgumentException("Invalid parentheses type")}
                }
            )
        )
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while(i < calculation.length && calculation[i] in "0123456789."){
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return i
    }
}