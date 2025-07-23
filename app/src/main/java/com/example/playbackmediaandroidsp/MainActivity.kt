package com.example.playbackmediaandroidsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.playbackmediaandroidsp.ui.theme.PlaybackMediaAndroidSpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaybackMediaAndroidSpTheme {
                VideoPlayer()
            }
        }
    }
}

@Composable
fun VideoPlayer() {
    val context = LocalContext.current

    val exoPlayer = remember {
        val videoUri = "android.resource://${context.packageName}/${R.raw.video_sample}".toUri()
        val mediaItem = MediaItem.fromUri(videoUri)

        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VideoPlayerPreview() {
    PlaybackMediaAndroidSpTheme {
        VideoPlayer()
    }
}