package com.solid.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ExpressionEvaluatorTest{

    lateinit var evaluator: ExpressionEvaluator

    @Test
    fun `Simple expression properly evaluated`(){
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(4.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(3.0),
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(-4)
    }

    //+4+5-(3*5)/(13 % 4)
    @Test
    fun `Complex expression properly evaluated`(){
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(4.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Parentheses(ParenthesesType.Opening),
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(5.0),
                ExpressionPart.Parentheses(ParenthesesType.Closing),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Parentheses(ParenthesesType.Opening),
                ExpressionPart.Number(13.0),
                ExpressionPart.Op(Operation.MODULUS),
                ExpressionPart.Number(4.0),
                ExpressionPart.Parentheses(ParenthesesType.Closing),
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(-366.0000000000025)
    }

    @Test
    fun `Expression with decimals properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(4.5),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.5),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.5),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(5.5),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(3.5),
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(4.5)
    }

    @Test
    fun `Simple equation with parentheses properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(4.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Parentheses(ParenthesesType.Opening),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Parentheses(ParenthesesType.Closing),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(4.0),
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(6.5)
    }

    @Test
    fun `Invalid expression throws exception`(){
        try {
            evaluator = ExpressionEvaluator(
                listOf(
                    ExpressionPart.Op(Operation.SUBTRACT),
                    ExpressionPart.Op(Operation.MULTIPLY),
                    ExpressionPart.Number(4.0),
                    ExpressionPart.Op(Operation.ADD),
                    ExpressionPart.Number(5.0),
                    ExpressionPart.Op(Operation.SUBTRACT),
                    ExpressionPart.Number(3.0),
                    ExpressionPart.Op(Operation.MULTIPLY),
                    ExpressionPart.Number(5.0),
                    ExpressionPart.Op(Operation.DIVIDE),
                    ExpressionPart.Number(3.0),
                )
            )
            evaluator.evaluate()
        }catch (ex: RuntimeException){
            assertThat(ex.message).isEqualTo("Invalid part")
        }
    }
}