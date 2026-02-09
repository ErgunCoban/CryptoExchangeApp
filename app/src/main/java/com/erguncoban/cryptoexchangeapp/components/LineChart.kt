package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed

@Composable
fun LineChart(
    data: List<Pair<Long, Float>>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val isProfit = data.last().second >= data.first().second
    val graphColor = if (isProfit) MarketGreen else MarketRed

    val spacing = 0f
    val transparentGraphColor = remember(graphColor) {
        graphColor.copy(alpha = 0.5f)
    }

    val upperValue = remember(data) { (data.maxOfOrNull { it.second }?.plus(1)) ?: 0f }
    val lowerValue = remember(data) { (data.minOfOrNull { it.second }?.minus(1)) ?: 0f }

    Canvas(modifier = modifier) {
        val space_perWidth = size.width / (data.size - 1)
        val height = size.height

        val strokePath = Path().apply {
            data.forEachIndexed { index, item ->
                val x1 = spacing + index * space_perWidth
                val y1 = height - ((item.second - lowerValue) / (upperValue - lowerValue)) * height

                if (index == 0) {
                    moveTo(x1, y1)
                } else {
                    lineTo(x1, y1)
                }
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(size.width, size.height)
                lineTo(spacing, size.height)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height
            )
        )

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(), // Çizgi kalınlığı
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        )
    }
}