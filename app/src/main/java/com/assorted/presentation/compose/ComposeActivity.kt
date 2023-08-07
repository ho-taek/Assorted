package com.assorted.presentation.compose

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
import com.assorted.presentation.compose.ui.theme.AssortedTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PreviewMessageCard()
        }
    }
}


@Composable
fun MessageCard(name : String){
    Text(text = "Hello $name")
}

@Preview
@Composable
fun PreviewMessageCard(){
    MessageCard(name = "Android")
}