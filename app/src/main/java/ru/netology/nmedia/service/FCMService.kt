package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

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

    data class PushData(
        val recipientId : Int?,
        val content : String
    )

    override fun onMessageReceived(message: RemoteMessage) {
        val recipientId = AppAuth.getInstance().getRecipientId()
        val msg : PushData =
            gson.fromJson(message.data["content"], PushData::class.java)

        val newRecipientId = msg.recipientId
        if (newRecipientId == null) {
            handleMessage(msg.content)
        } else when{
            newRecipientId == recipientId -> handleMessage(msg.content)
            newRecipientId == 0 && newRecipientId != recipientId ->
                AppAuth.getInstance().sendPushToken()
            newRecipientId != 0 && newRecipientId != recipientId ->{
                AppAuth.getInstance().saveRecipientId(newRecipientId)
            }

        }

        println(message.data["content"])
    }

    private fun handleMessage(content: String) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    override fun onNewToken(token: String) {
        AppAuth.getInstance().sendPushToken(token)
    }
}

enum class Action {
    LIKE,
}

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)

