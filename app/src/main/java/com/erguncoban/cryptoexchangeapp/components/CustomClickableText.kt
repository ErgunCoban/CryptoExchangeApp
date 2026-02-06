package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun CustomClickableText(
    fullText: String,
    clickableText: String,
    normalTextColor: Color,
    clickableTextColor: Color,
    clickableTextFontWeight: FontWeight,
    onClick: () -> Unit
){

    val annotatedText = buildAnnotatedString {

        val startIndex = fullText.indexOf(clickableText)

        if (startIndex == -1){
            withStyle(
                SpanStyle(
                    color = normalTextColor
                )
            ){
                append(fullText)
            }
        }else{
            withStyle(
                SpanStyle(
                    color = normalTextColor
                )
            ){
                append(fullText.substring(0, startIndex))
            }

            pushStringAnnotation(
                tag = "clickable",
                annotation = clickableText
            )

            withStyle(
                style = SpanStyle(
                    color = clickableTextColor,
                    fontWeight = clickableTextFontWeight
                )
            ){
                append(clickableText)
            }
            pop()

            append(fullText.substring(startIndex + clickableText.length))
        }

    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "clickable",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onClick()
            }
        }
    )

}