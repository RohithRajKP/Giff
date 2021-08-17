package com.freshworks.giff.model

data class GiffModel(
   // @SerializedName("data")
    val data: MutableList<Data>,
    val meta: Meta,
    val pagination: Pagination,
//    val urlimage :Images ,
//    val url: Original =urlimage.original,
//    val imgurl: String =url.url// url.indexOf(7).toString()
)