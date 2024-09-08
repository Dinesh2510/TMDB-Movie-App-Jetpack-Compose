package com.app.movieapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.app.movieapp.BuildConfig
import com.app.movieapp.R
import com.app.movieapp.graph.MovieAppScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val appVersion = MutableLiveData<String>(BuildConfig.VERSION_NAME)
    var progress by remember { mutableStateOf(0f) }
    val animationDuration = 3000 // 3 seconds
    val splashDuration = 3000L // 3 seconds should same for animationDuration
    val scale = remember {
        Animatable(0f)
    }
    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        progress = 1f
        delay(splashDuration)
        navController.navigate(MovieAppScreen.MOVIE_HOME.route) {
            popUpTo(MovieAppScreen.SPLASH.route) { inclusive = true }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .align(Alignment.CenterHorizontally) // Align horizontally to center
        )
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedProgressBar(progress = progress, animationDuration = animationDuration)
    }


}

@Composable
fun AnimatedProgressBar(progress: Float, animationDuration: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = animationDuration)
    )
    LinearProgressIndicator(
        progress = { animatedProgress },
        color = colorResource(id = R.color.purple_500),//progress Color
        trackColor = MaterialTheme.colorScheme.primary,//
        modifier = Modifier
            .height(20.dp)
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
    )
}