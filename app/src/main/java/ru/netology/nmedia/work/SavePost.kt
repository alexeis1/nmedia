package ru.netology.nmedia.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.netology.nmedia.repository.PostIdNotFound
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

class SavePostWorker @Inject constructor(
    @ApplicationContext appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    @Inject
    lateinit var repository: PostRepository

    companion object {
        const val postKey = "post"
    }

    override suspend fun doWork(): Result {
        val id = inputData.getLong(postKey, 0L)
        if (id == 0L) {
            return Result.failure()
        }
        return try {
            repository.processWork(id)
            Result.success()
        }catch (e: PostIdNotFound) {
            Result.failure()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
