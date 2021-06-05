package ru.netology.nmedia.repository

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.lang.RuntimeException


class PostRepositoryImpl : PostRepository {
    override fun getAllAsync(callback: PostRepository.GetResultCallback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }

    override fun saveAsync(post: Post, callback: PostRepository.GetResultCallback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post>{
            var retryCounter = 10
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                    try {
                        callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                } else{
                    --retryCounter
                    if (retryCounter >= 0){
                        PostsApi.retrofitService.save(post).enqueue(this)
                    } else {
                        callback.onError(RuntimeException("bad connection"))
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.GetResultCallback<Boolean>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit>{
            var retryCounter = 10
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.isSuccessful)
                } else {
                    --retryCounter
                    if (retryCounter >= 0){
                        PostsApi.retrofitService.removeById(id).enqueue(this)
                    } else {
                        callback.onError(RuntimeException("bad connection"))
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }
    private fun dislikePost(id: Long, callback: PostRepository.GetResultCallback<Post>){
        PostsApi.retrofitService.dislikeById(id).enqueue(object : Callback<Post> {
                var retryCounter = 10
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    try {
                        if (response.isSuccessful) {
                            val body = response.body() ?: throw RuntimeException("body is null")
                            callback.onSuccess(body)
                        } else {
                            --retryCounter
                            if (retryCounter >= 0){
                                PostsApi.retrofitService.dislikeById(id).enqueue(this)
                            } else {
                                callback.onError(RuntimeException("bad connection"))
                            }
                        }
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }
            })
        }

    private fun likePost(id: Long, callback: PostRepository.GetResultCallback<Post>){
        PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
                var retryCounter = 10
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    try {
                        if (response.isSuccessful) {
                            val body = response.body() ?: throw RuntimeException("body is null")
                            callback.onSuccess(body)
                        } else {
                            --retryCounter
                            if (retryCounter >= 0){
                                PostsApi.retrofitService.likeById(id).enqueue(this)
                            } else {
                                callback.onError(RuntimeException("bad connection"))
                            }
                        }
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }
            })
        }

    override fun likeByIdAsync(id: Long, callback: PostRepository.GetResultCallback<Post>) {
        PostsApi.retrofitService.getById(id).enqueue(object : Callback<Post> {
                var retryCounter = 10
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    try {
                        if (response.isSuccessful) {
                            val body = response.body() ?: throw RuntimeException("body is null")
                            if (body.likedByMe) {
                                dislikePost(id, callback)
                            } else {
                                likePost(id, callback)
                            }
                        }  else {
                            --retryCounter
                            if (retryCounter >= 0){
                                PostsApi.retrofitService.getById(id).enqueue(this)
                            } else {
                                callback.onError(RuntimeException("bad connection"))
                            }
                        }

                        } catch (e: Exception) {
                            callback.onError(e)
                        }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }
            })
        }
}
