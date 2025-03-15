package com.example.bai2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai2.ui.theme.Bai2Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai2Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showSplashScreen = false
    }

    if (showSplashScreen) {
        SplashScreen()
    } else {
        var screenIndex by remember { mutableStateOf(1) }
        Crossfade(targetState = screenIndex) { currentScreen ->
            when (currentScreen) {
                1 -> FirstScreen(
                    onNextClick = { screenIndex = 2 },
                    onSkipClick = { screenIndex = 4 })

                2 -> SecondScreen(
                    onNextClick = { screenIndex = 3 },
                    onPreviousClick = { screenIndex = 1 },
                    onSkipClick = { screenIndex = 4 })

                3 -> ThirdScreen(
                    onNextClick = { screenIndex = 4 },
                    onPreviousClick = { screenIndex = 2 },
                    onSkipClick = { screenIndex = 4 })

                4 -> HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ màn hình
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Đoạn text đầu tiên
        Text(
            text = "Coming",
            style = TextStyle(
                color = Color(0xFF3399FF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "SOOOOOOOOOOON!",
            style = TextStyle(
                color = Color(0xFF3399FF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


val commonBoxModifier = Modifier
    .size(8.dp)
    .background(Color.LightGray, shape = CircleShape)

val uncommonBoxModifier = Modifier
    .size(8.dp)
    .background(Color(0xFF3399FF), shape = CircleShape)

val boxContainer = Modifier
    .fillMaxSize()
    .background(Color.White)
    .padding(16.dp)

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logouth),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "UTH SmartTasks",
            style = TextStyle(
                color = Color(0xFF3399FF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun PageIndicator(currentPage: Int, totalPages: Int) {
    Row {
        for (i in 1..totalPages) {
            Box(
                modifier = if (i == currentPage) uncommonBoxModifier else commonBoxModifier
            )
            if (i != totalPages) Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun SkipButton(onSkipClick: () -> Unit) {
    TextButton(onClick = onSkipClick) {
        Text(text = "Skip", color = Color(0xFF3399FF))
    }
}

@Composable
fun NavigationButtons(
    onPreviousClick: (() -> Unit)? = null,
    onNextClick: () -> Unit,
    nextText: String = "Next"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (onPreviousClick != null) {
            Button(
                onClick = onPreviousClick,
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3399FF))
            ) {
                Text(text = "Back", color = Color.White, fontSize = 16.sp)
            }
        }
        Button(
            onClick = onNextClick,
            modifier = Modifier.weight(4f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3399FF))
        ) {
            Text(text = nextText, color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun GenericScreen(
    currentPage: Int,
    totalPages: Int,
    title: String,
    description: String,
    imageRes: Int,
    onNextClick: () -> Unit,
    onPreviousClick: (() -> Unit)? = null,
    onSkipClick: () -> Unit,
    nextText: String = "Next"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // Đảm bảo sắp xếp đều khoảng cách giữa các phần tử
        ) {
            // Phần trên cùng với Page Indicator và Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PageIndicator(currentPage, totalPages)
                SkipButton(onSkipClick = onSkipClick)
            }

            // Phần giữa với hình ảnh và văn bản
            Column(
                modifier = Modifier
                    .weight(1f) // Để chiếm phần còn lại của màn hình
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = description,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Phần dưới cùng với nút Back và Next
            NavigationButtons(
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick,
                nextText = nextText
            )
        }
    }
}

@Composable
fun FirstScreen(onNextClick: () -> Unit, onSkipClick: () -> Unit) {
    GenericScreen(
        currentPage = 1,
        totalPages = 3,
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first ",
        imageRes = R.drawable.screen1,
        onNextClick = onNextClick,
        onSkipClick = onSkipClick
    )
}

@Composable
fun SecondScreen(onNextClick: () -> Unit, onPreviousClick: () -> Unit, onSkipClick: () -> Unit) {
    GenericScreen(
        currentPage = 2,
        totalPages = 3,
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve",
        imageRes = R.drawable.screen2,
        onNextClick = onNextClick,
        onPreviousClick = onPreviousClick,
        onSkipClick = onSkipClick
    )
}

@Composable
fun ThirdScreen(onNextClick: () -> Unit, onPreviousClick: () -> Unit, onSkipClick: () -> Unit) {
    GenericScreen(
        currentPage = 3,
        totalPages = 3,
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set",
        imageRes = R.drawable.screen3,
        onNextClick = onNextClick,
        onPreviousClick = onPreviousClick,
        onSkipClick = onSkipClick,
        nextText = "Get Started"
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewJetpackComposeScreen() {
    Bai2Theme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeScreen1() {
    Bai2Theme {
        FirstScreen(onNextClick = {}, onSkipClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeScreen2() {
    Bai2Theme {
        SecondScreen(onNextClick = {}, onPreviousClick = {}, onSkipClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeScreen3() {
    Bai2Theme {
        ThirdScreen(onNextClick = {}, onPreviousClick = {}, onSkipClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreen1() {
    Bai2Theme {
        HomeScreen()
    }
}
