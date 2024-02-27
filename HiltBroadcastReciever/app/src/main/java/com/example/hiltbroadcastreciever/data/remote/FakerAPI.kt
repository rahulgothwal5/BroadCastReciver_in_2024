package com.example.hiltbroadcastreciever.data.remote

import com.example.hiltbroadcastreciever.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface FakerAPI {

    @GET("/products")
    suspend fun getProducts() : Response<List<Product>>
}