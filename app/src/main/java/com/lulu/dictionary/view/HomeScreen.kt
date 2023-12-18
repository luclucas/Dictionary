package com.lulu.dictionary.view


import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.lulu.dictionary.viewmodel.DictionaryViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: DictionaryViewModel, navController: NavController){
    var word by rememberSaveable {
        mutableStateOf("")
    }



    Column(
        Modifier
            .padding(30.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
       TextField(value = word, onValueChange = {
           word = it
       }, label = { Text(text = "Word")}, colors = TextFieldDefaults.textFieldColors(
         containerColor = Color.Gray
       )
       )

        Button(modifier = Modifier.padding(80.dp) ,onClick = {
            runBlocking {

                viewModel.getWord(word)
                navController.navigate("response/$word")
            }
        }) {
            Text(text = "Submit")
        }




    }
}

@Composable
fun Response(viewModel: DictionaryViewModel, navController: NavController, word: String, lifecycleOwner: LifecycleOwner) {
    Log.d("lulutag", word)


    val words by viewModel.wordLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getWord(word)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {

        if (words != null) {
            val list = words!!.first()

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = " ",
                Modifier
                    .size(38.dp)
                    .clickable {
                        navController.navigate("home")
                    })

            Text(
                text = list.word,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Text(text = list.phonetic)

            list.meanings.forEach {
                var cont = 1
                Text(
                    text = it.partOfSpeech,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                it.definitions.forEach { def ->
                    Text(text = "$cont. ${def.definition}")
                    cont++
                }
            }
        }
    }
}
