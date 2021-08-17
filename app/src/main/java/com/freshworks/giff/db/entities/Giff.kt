package com.freshworks.giff.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
note : for avoiding type converions and large set of data, we can use this single data class s entity class
id is unique key,
other details are getting from Data class "Data"
boolen column determies whether an item is favorite or not.

Local db working with this entity class
 */

@Entity
data class Giff(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val url: String,
    var isFavorite: Boolean
)