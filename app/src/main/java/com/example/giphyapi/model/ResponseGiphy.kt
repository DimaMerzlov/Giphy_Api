package com.example.giphyapi.model

data class ResponseGiphy(
//    val bitly_gif_url: String,
//    val bitly_url: String,
//    val content_url: String,
//    val embed_url: String,
    val id: String,
    val images: ImagesGiphy,
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
