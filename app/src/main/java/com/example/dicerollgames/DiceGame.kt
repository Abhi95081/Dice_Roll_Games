package com.example.dicerollgames

import android.media.MediaPlayer
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun DiceGame(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val diceImages = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    var image by remember { mutableIntStateOf(diceImages[0]) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var isClockwise by remember { mutableStateOf(true) }

    // Apply animation for rotation
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing), label = ""
    )

    // Handle button click event for rolling the dice
    fun onRoll() {
        // Play sound
        val mediaPlayer = MediaPlayer.create(context, R.raw.dice_roll)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { it.release() }

        // Alternate rotation direction
        rotation += if (isClockwise) 360f else -360f
        isClockwise = !isClockwise  // Toggle direction

        // Update dice image
        image = diceImages.random()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "🎲 Roll the Dice 🎲",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Dice Image with Rotation Animation
        Image(
            painter = painterResource(image),
            contentDescription = "Dice showing a number",
            modifier = Modifier
                .size(140.dp)
                .rotate(animatedRotation) // Apply rotation animation
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Roll Button
        Button(
            onClick = { onRoll() },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Roll the Dice",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
            )
        }
    }
}
