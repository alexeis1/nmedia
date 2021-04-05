package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.dto.Post

class NewPostResultContract: ActivityResultContract<Post,String?>() {
    override fun createIntent(context: Context, input: Post?): Intent =
        Intent(context, NewPostActivity::class.java).
            putExtra("post", input)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK){
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        }else{
            null
        }
}