package com.raka.trendinggitwithdaggerhilt.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raka.myapplication.data.model.License
import com.raka.myapplication.data.model.Owner
@Entity(tableName = "repositorylist")
data class ItemsLocal(
    @ColumnInfo(name = "roomId")
    @PrimaryKey(autoGenerate = true)
    val roomId: Long = 0,
    @ColumnInfo(name = "avatar_url")
    val avatar_url:String,
    @ColumnInfo(name = "html_url")
    val html_url:String,
    @ColumnInfo(name = "full_name")
    val full_name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "forks_count")
    val forks_count: Int,
    @ColumnInfo(name = "stargazers_count")
    val stargazers_count: Int,
    @ColumnInfo(name = "open_issues_count")
    val open_issues_count: Int
)