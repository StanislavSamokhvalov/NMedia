package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val channelId = "remote"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data["action"]?.let {
            val type = when (message.data["action"]) {
                "LIKE" -> "LIKE"
                "NEWPOST" -> "NEWPOST"
                else -> "OTHER"
            }

            when (Action.values().firstOrNull { it.name == type }) {
                Action.LIKE -> handleLike(Gson().fromJson(message.data["content"], Like::class.java))
                Action.NEWPOST -> handleNewPost(Gson().fromJson(message.data["content"], NewPost::class.java))
                else -> handleOther()
            }
        }
    }


    private fun handleLike(like: Like) {
        val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_near_me_24)
                .setContentTitle(
                        getString(
                                R.string.notification_user_liked,
                                like.userName,
                                like.postAuthor
                        )
                )
                .build()

        NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
    }

    private fun handleNewPost(newPost: NewPost) {
        val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_near_me_24)
                .setContentTitle(getString(
                        R.string.notification_new_post,
                        newPost.postAuthor
                ))
                .setContentText(newPost.postText)
                .setLargeIcon(AppCompatResources.getDrawable(this, R.mipmap.ic_launcher)?.toBitmap())
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(newPost.postText))
                .build()

        NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
    }

    private fun handleOther() {
        val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_near_me_24)
                .setContentTitle(
                        getString(
                                R.string.other_notifications
                        ))
                .build()

        NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
    }

    override fun onNewToken(token: String) {
        println("Token : $token")
    }

}

enum class Action {
    LIKE,
    NEWPOST,
    OTHER
}

data class Like(
        val userId: Int,
        val userName: String,
        val postId: Int,
        val postAuthor: String
)

data class NewPost(
        val postAuthor: String,
        val postText: String
)