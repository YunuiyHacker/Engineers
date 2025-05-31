package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LinkAnnotationText(modifier: Modifier, startText: String, linkText: String, endText: String) {
    val annotatedLinkString: AnnotatedString = remember {
        buildAnnotatedString {

            val style = SpanStyle(
                color = Color.Black,
                fontSize = 18.sp,
            )

            val styleCenter = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline
            )

            withStyle(
                style = style
            ) {
                append(startText)
            }

            withLink(LinkAnnotation.Url(url = "https://github.com")) {
                withStyle(
                    style = styleCenter
                ) {
                    append(linkText)
                }
            }

            withStyle(
                style = style
            ) {
                append(endText)
            }
        }
    }

    Column(
        modifier = modifier
    ) {
        Text(annotatedLinkString)
    }
}