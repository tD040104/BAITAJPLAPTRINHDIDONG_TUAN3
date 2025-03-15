package com.example.bai1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai1.ui.theme.Bai1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai1Theme {
                var showComponentsList by remember { mutableStateOf(false) }

                if (showComponentsList) {
                    UIComponentsListScreen()
                } else {
                    JetpackComposeScreen(onButtonClick = {
                        showComponentsList = true
                    })
                }
            }
        }
    }
}

@Composable
fun JetpackComposeScreen(onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier
                .size(300.dp) .padding(top = 120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(80.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Jetpack Compose",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Jetpack Compose is a modern UI toolkit for\n" +
                        "building native Android applications using\n" +
                        "a declarative programming approach.",
                style = TextStyle(fontSize = 16.sp),
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(180.dp))

        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "I'm ready", color = Color.White)
        }
    }
}

val lightBlue = Color(0xFFB3E5FC)
val colorLabel = Color(0xFF4169E1)

@Composable
fun UIComponentsListScreen() {
    val showTextDetail = remember { mutableStateOf(false) }

    if (!showTextDetail.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "UI Components List",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorLabel
                ),
                modifier = Modifier.padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomText(content = "Display")

            UIComponentCard("Text", "Displays text", onClick = { showTextDetail.value = true })
            UIComponentCard("Image", "Displays an image", onClick = { showTextDetail.value = true })

            CustomText(content = "Input")

            UIComponentCard("TextField", "Input field for text", onClick = { showTextDetail.value = true })
            UIComponentCard("PasswordField", "Input field for passwords", onClick = { showTextDetail.value = true })

            CustomText(content = "Layout")

            UIComponentCard("Column", "Arranges elements vertically", onClick = { showTextDetail.value = true })
            UIComponentCard("Row", "Arranges elements horizontally", onClick = { showTextDetail.value = true })
        }
    } else {
        TextDetailScreen(onBackClick = {
            showTextDetail.value = false // Quay lại trang danh sách
        })
    }
}

@Composable
fun UIComponentCard(title: String, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(lightBlue, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = description)
    }
}

@Composable
fun CustomText(content: String) {
    Text(
        text = content,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
        modifier = Modifier.padding(top = 26.dp)
    )
}

@Composable
fun TextDetailScreen(onBackClick: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Nút Back
        Row(
//
        ) {
            Text(
                text = "<<",
                style =  TextStyle(
                    fontSize = 24.sp,
                    color = colorLabel
                ),
                modifier = Modifier.clickable {
                    onBackClick() // Quay lại trang trước
                }
            )

//            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Text Detail",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorLabel
                ),
                modifier = Modifier.weight(1f) .padding(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        val annotatedText = buildAnnotatedString {
            pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
            append("Nguyễn")
            pop()

            append(" Thành")

            pushStyle(SpanStyle(color = Color(0xFFFF5722), fontWeight = FontWeight.Bold))
            append(" Đức")
            pop()

            pushStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
            append(" is the rich =))")
            pop()
        }

        // Hiển thị văn bản đã được "đóng gói" trong annotatedText
        Text(
            text = annotatedText,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJetpackComposeScreen() {
    JetpackComposeScreen(onButtonClick = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewUIComponentsListScreen() {
    UIComponentsListScreen()
}
