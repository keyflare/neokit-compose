package com.keyflare.neokit.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.keyflare.neokit.compose.Greeting
import com.keyflare.neokit.sample.ui.theme.NeokitSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeokitSampleTheme {
                Greeting("World!")
            }
        }
    }
}
