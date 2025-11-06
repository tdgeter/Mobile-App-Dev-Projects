package com.example.diceroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroll.ui.theme.DiceRollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollTheme {
                DiceRollScreen()
            }
        }
    }
}

class Dice (val numberOfSides: Int) {
    fun roll(): Int {
        return (1..numberOfSides).random()
    }
}

@Composable
fun DiceRollScreen() {
    var diceOneImage by remember { mutableStateOf(R.drawable.dice1) }
    var diceTwoImage by remember { mutableStateOf(R.drawable.dice1) }
    var previousRolls by remember { mutableStateOf(listOf<Int>()) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom

        )
        {
            Row {
                Image(
                    painter = painterResource(id = diceOneImage),
                    contentDescription = "Dice Image"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = diceTwoImage),
                    contentDescription = "Dice Image"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val dice1 = Dice(6)
                val dice2 = Dice(6)
                val roll1 = dice1.roll()
                val roll2 = dice2.roll()

                when (roll1) {
                    1 -> diceOneImage = R.drawable.dice1
                    2 -> diceOneImage = R.drawable.dice2
                    3 -> diceOneImage = R.drawable.dice3
                    4 -> diceOneImage = R.drawable.dice4
                    5 -> diceOneImage = R.drawable.dice5
                    6 -> diceOneImage = R.drawable.dice6
                }

                when (roll2) {
                    1 -> diceTwoImage = R.drawable.dice1
                    2 -> diceTwoImage = R.drawable.dice2
                    3 -> diceTwoImage = R.drawable.dice3
                    4 -> diceTwoImage = R.drawable.dice4
                    5 -> diceTwoImage = R.drawable.dice5
                    6 -> diceTwoImage = R.drawable.dice6
                }

                previousRolls = previousRolls + (roll1 + roll2)
            })
            {
                Text(text = "Roll Dice")
            }
        }
        Column(modifier = Modifier.weight(1f))
        {
            Text(text = "Previous Rolls:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            LazyColumn()
            {
                items(previousRolls.reversed()) { currentRoll ->
                    Text(text = currentRoll.toString(),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


