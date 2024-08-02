package com.solid.materialcalculator.domain

import kotlin.math.exp

class ExpressionWriter {

    var expression = ""

    fun processAction(action: CalculatorAction){
        when(action){
            CalculatorAction.Clear -> {
                expression = ""
            }
            CalculatorAction.Decimal -> {
                if (canEnterDecimal()){
                    expression += "."
                }
            }
            CalculatorAction.Delete -> {
                expression = expression.dropLast(1)
            }
            is CalculatorAction.Number -> {
                expression += action.number
            }
            is CalculatorAction.Op -> {
                if (canEnterOperation(action.operation)){
                    expression += action.operation.symbol
                }
            }
            CalculatorAction.Parentheses -> {
                processParentheses()
            }

            CalculatorAction.Calculate -> {
                val parser = ExpressionParser(prepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression = evaluator.evaluate().toString()
            }
        }
    }

    //+3-5 allowed
    //+--3+5 allowed
    //+*3*5 not allowed

    private fun canEnterOperation(operation: Operation): Boolean{
        if (operation in listOf(Operation.ADD, Operation.SUBTRACT)){
            return expression.isEmpty() || expression.last() in "$operationSymbols()0123456789"
        }

        return expression.isNotEmpty() || expression.last() in ")0123456789"
    }

    private fun canEnterDecimal(): Boolean{
        if(expression.isEmpty() || expression.last() in "$operationSymbols.()"){
            return false
        }

        //4+5.56.56 -> this is what we are negating here
        return !expression.takeLastWhile {
            it in "0123456789."
        }.contains(".")
    }

    private fun processParentheses(){
        val openingCount = expression.count{it == '('}
        val closingCount = expression.count{it == ')'}

        expression += when{
            expression.isEmpty() || expression.last() in "$operationSymbols(" -> "("
            expression.last() in "0123456789)" && openingCount == closingCount -> return
            else -> ")"
        }
    }

    private fun prepareForCalculation(): String{
        val newExpression = expression.takeLastWhile {
            it in "$operationSymbols(."
        }

        if (newExpression.isEmpty()){
            return "0"
        }

        return newExpression
    }
}