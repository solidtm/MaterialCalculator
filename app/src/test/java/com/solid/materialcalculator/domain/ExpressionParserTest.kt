package com.solid.materialcalculator.domain

import org.junit.Assert.*
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class ExpressionParserTest{

    private lateinit var parser: ExpressionParser
    @Test
    fun `Simple expression is properly parsed`() {
        parser = ExpressionParser("3+5-3x4/3")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parentheses is properly parsed`(){
        parser = ExpressionParser("4-(4x5)")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )

        assertThat(actual).isEqualTo(expected)
    }

    //Homework: Extend the test by adding more complex ones such as:
    //1. Test for expressions with decimal numbers
    //2. Test for expression with multiple nested parentheses
}