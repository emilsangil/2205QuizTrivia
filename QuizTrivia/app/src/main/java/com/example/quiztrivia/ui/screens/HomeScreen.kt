package com.example.quiztrivia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiztrivia.R


@Composable
fun HomeScreen (onStartClicked: () -> Unit){
    Box(
        modifier = Modifier.fillMaxSize(),

        ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(400.dp)
                    .padding(16.dp) // Add some padding to the logo
            )
            Spacer(Modifier.height(150.dp)) // Spacing between logo and button

            val hexColor = "#F72585"
            val color = Color(android.graphics.Color.parseColor(hexColor))

            Button(
                onClick = onStartClicked,
                modifier = Modifier
                    .width(223.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .shadow(elevation = 40.dp)
                ,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = Color.White
                )
            ) {
                Text(text = "START GAME",fontSize = 20.sp)
            }
            Spacer(Modifier.weight(1f)) // Spacing for bottom button
        }
    }
}
