package com.example.giphyapi.model

import java.io.File

data class ResponseGiphy(
//    val bitly_gif_url: String,
//    val bitly_url: String,
//    val content_url: String,
//    val embed_url: String,
    val id: String? = null,
    val images: ImagesGiphy? = null,
    val file: File? = null,

//    val url: String,
) {
    data class ImagesGiphy(
        val original: ImagesOriginal
    ) {
        data class ImagesOriginal(
            val url: String,
        )
    }
}
