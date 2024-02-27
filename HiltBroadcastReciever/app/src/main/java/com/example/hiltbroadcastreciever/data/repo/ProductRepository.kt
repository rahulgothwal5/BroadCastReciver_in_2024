package com.example.hiltbroadcastreciever.data.repo

import com.example.hiltbroadcastreciever.data.model.Product
import com.example.hiltbroadcastreciever.data.remote.FakerAPI
import com.example.hiltbroadcastreciever.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val fakerAPI: FakerAPI,
) {
    suspend fun getProducts(): Flow<Result<List<Product>?>> {
        return flow {
            try {
                val result = fakerAPI.getProducts()
                if (result.isSuccessful && result.body() != null) {
                    emit(Result.Success(result.body()))
                } else {
                    emit(Result.Error(Exception("Failed to fetch topics: ${result.code()}")))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}
