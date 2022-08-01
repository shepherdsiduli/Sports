package co.shepherd.sports.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.shepherd.sports.databinding.ActivityMediaPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding
    private lateinit var videoUrl: String
    private lateinit var title: String
    private lateinit var exoPlayer: ExoPlayer

    companion object {
        const val EXTRA_VIDEO_URL = "EXTRA_VIDEO_URL"
        const val EXTRA_VIDEO_TITLE = "EXTRA_VIDEO_TITLE"

        @JvmStatic
        fun getStartIntent(context: Context, url: String, title: String?): Intent {
            val intent = Intent(context, MediaPlayerActivity::class.java)
            intent.putExtra(EXTRA_VIDEO_URL, url)
            intent.putExtra(EXTRA_VIDEO_TITLE, title)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exoPlayer = ExoPlayer.Builder(this).build()
        videoUrl = intent.getStringExtra(EXTRA_VIDEO_URL) ?: ""
        title = intent.getStringExtra(EXTRA_VIDEO_TITLE) ?: ""
        prepareVideo(videoUrl)

        binding.playerToolbar.subtitle = title
        binding.playerToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun prepareVideo(url: String) {
        binding.exoPlayerView.player = exoPlayer
        val mediaItem: MediaItem = MediaItem.fromUri(url)
        exoPlayer.addMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    override fun onBackPressed() {
        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
            exoPlayer.release()
        }
        super.onBackPressed()
    }
}