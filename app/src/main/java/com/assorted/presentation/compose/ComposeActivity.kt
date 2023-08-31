package com.assorted.presentation.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assorted.R
import com.assorted.presentation.compose.ui.theme.AssortedTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AssortedTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("야호", "호이 호이 둘리는"))
                }
            }

        }
    }
}

data class Message(val author : String, val body : String)

@Composable
fun MessageCard(msg : Message){
    Row {
        Image(
            painterResource(id = R.drawable.vibrate),
            contentDescription = "COntact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Text(text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge)

            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                Text(text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.headlineLarge)
            }

        }
    }


}

@Preview
@Composable
fun PreviewMessageCard(){
    AssortedTheme {
        Surface {
            MessageCard(
                msg = Message("야호", "호이 호이 둘리는")
            )
        }
    }

}