package com.example.unacademy.Activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.WindowDecorActionBar
import com.example.unacademy.R
import com.example.unacademy.databinding.ActivityExoPlayerBinding
import com.example.unacademy.databinding.ActivityNavBarBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class ExoPlayer : AppCompatActivity() {
    private lateinit var binding:ActivityExoPlayerBinding
    lateinit var simpleExoPlayer: ExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Make Activity full screen

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        simpleExoPlayer=ExoPlayer.Builder(applicationContext).build()
        binding.playerView.player=simpleExoPlayer
        var mediaItem=MediaItem.fromUri(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"))
        simpleExoPlayer.addMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }
}