package com.example.sewakameraapp01.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "camera_table")
data class Camera(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val item: String,
    val time: String
) : Parcelable