package ru.netology.nmedia.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

class RefreshPostsWorker @Inject constructor(
    @ApplicationContext appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    @Inject
    lateinit var repository: PostRepository

    companion object {
        const val name = "ru.netology.work.RefreshPostsWorker"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            repository.getAll()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}