package com.lulu.dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lulu.dictionary.repository.DictionaryRepository
import com.lulu.dictionary.service.DictionaryInstance
import com.lulu.dictionary.service.DictionaryService
import com.lulu.dictionary.ui.theme.DictionaryTheme
import com.lulu.dictionary.view.HomeScreen
import com.lulu.dictionary.view.Response
import com.lulu.dictionary.viewmodel.DictionaryViewModel
import com.lulu.dictionary.viewmodel.DictionaryViewModelFactory

class MainActivity : ComponentActivity() {

    lateinit var viewModel: DictionaryViewModel
    val service = DictionaryInstance.getDictionaryInstance().create(DictionaryService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DictionaryRepository(service)
        val factory = DictionaryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DictionaryViewModel::class.java]

        setContent {

            val navController = rememberNavController()
            DictionaryTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "home"){
                    composable("home"){
                        HomeScreen(viewModel, navController)
                    }

                    composable("response/{word}", arguments = listOf(navArgument("word"){type = NavType.StringType})){
                        Response(viewModel, navController, it.arguments?.getString("word")!!, this@MainActivity)
                    }
                }
            }
        }
    }


}
