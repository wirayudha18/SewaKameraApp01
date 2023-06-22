package com.example.sewakameraapp01.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "kamera_table")
data class Kamera(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val barang: String,
    val Durasi: String
) : Parcelable