package com.solid.materialcalculator.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    action: CalculatorUiAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                when (action.highlightLevel) {
                    HighlightLevel.Neutral -> MaterialTheme.colorScheme.surfaceVariant
                    HighlightLevel.SemiHighlighted -> MaterialTheme.colorScheme.inverseSurface
                    HighlightLevel.Highlighted -> MaterialTheme.colorScheme.primary
                    HighlightLevel.StronglyHighlighted -> MaterialTheme.colorScheme.tertiary
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (action.text != null) {
            Text(
                text = action.text,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                color = when (action.highlightLevel) {
                    HighlightLevel.Highlighted -> MaterialTheme.colorScheme.onTertiary
                    HighlightLevel.Neutral -> MaterialTheme.colorScheme.onSurfaceVariant
                    HighlightLevel.SemiHighlighted -> MaterialTheme.colorScheme.inverseOnSurface
                    HighlightLevel.StronglyHighlighted -> MaterialTheme.colorScheme.onPrimary
                }
            )
        }else{
            action.content()
        }
    }
}