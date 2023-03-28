package com.example.quiztrivia.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiztrivia.R
import com.example.quiztrivia.model.Trivia
import com.example.quiztrivia.network.ApiClient
import kotlin.random.Random


@Composable
fun TriviaGame() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )

        val hexColor = "#F72585"
        val color = Color(android.graphics.Color.parseColor(hexColor))

        val trivia = remember { mutableStateOf(emptyList<Trivia>()) }
        val isLoading = remember { mutableStateOf(true) }
        val currentTrivia = remember { mutableStateOf<Trivia?>(null) }
        val guess = remember { mutableStateOf("") }
        val answer = remember { mutableStateOf("") }
        val result = remember { mutableStateOf("") }
        val score = remember { mutableStateOf(0) }
        val level = remember { mutableStateOf(1) }
        val isStarting = remember { mutableStateOf(false) }
        val options = remember { mutableStateOf(mutableListOf<String>()) }



        LaunchedEffect(key1 = Unit) {
            val triviaResponse = ApiClient.apiService.getTrivia()
            val triviaList = triviaResponse.results
            trivia.value = triviaList
            isLoading.value = false
            currentTrivia.value = trivia.value[Random.nextInt(0, trivia.value.size)]
            options.value = currentTrivia.value!!.incorrectAnswers.toMutableList()
            options.value.add(currentTrivia.value!!.correctAnswer)
            options.value.shuffle()
            answer.value = currentTrivia.value!!.correctAnswer
        }

        if (isStarting.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator()
                } else {

                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "Score: ${score.value}",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Spacer(Modifier.height(50.dp))
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .height(100.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = true
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Category",
                                style = MaterialTheme.typography.h5,
                                color = color,
                            )
                            Text(
                                text = currentTrivia.value!!.category,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = true
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Question",
                                style = MaterialTheme.typography.h5,
                                color = color,
                            )
                            Text(
                                text = currentTrivia.value!!.question,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                fontSize = 17.sp
                            )
                        }
                    }
                    Text(
                        text = result.value,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    var selectedOption = remember { mutableStateOf("")}

                    for (option in options.value) {
                        var isSelected = option == selectedOption.value
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    selectedOption.value = option
                                    guess.value = option

                                },
                                modifier = Modifier.padding(3.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (isSelected) Color.Cyan else Color.White,
                                    contentColor = color
                                )
                            ) {
                                Text(text = option, fontSize = (23.sp))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                    ) {
                        Button(
                            onClick = {
                                if (guess.value.equals(
                                        currentTrivia.value!!.correctAnswer,
                                        ignoreCase = true
                                    )
                                ) {
                                    result.value = "Correct!"
                                    score.value += 1
                                    guess.value = ""
                                    currentTrivia.value =
                                        trivia.value[Random.nextInt(0, trivia.value.size)]
                                    options.value =
                                        currentTrivia.value!!.incorrectAnswers.toMutableList()
                                    options.value.add(currentTrivia.value!!.correctAnswer)
                                    options.value.shuffle()
                                } else {
                                    result.value = "incorrect Try Again!"
                                    guess.value = ""
                                }
                            }

                        ) {
                            Text(text = "Submit")
                        }

                    }
                }
            }
        }
        else {
            HomeScreen(onStartClicked = { isStarting.value = true })
        }
    }
}