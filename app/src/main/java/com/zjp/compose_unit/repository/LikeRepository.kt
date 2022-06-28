package com.zjp.compose_unit.repository

import com.zjp.compose_unit.Result
import com.zjp.compose_unit.database.LocalDB
import com.zjp.core_database.ComposeDatabase
import com.zjp.core_database.model.LikeWidget

class LikeRepository(private val db: ComposeDatabase = LocalDB.getDatabase()) {

    fun getAllLike(): Result<List<LikeWidget>> {
        return try {
            var list = LocalDB.getDatabase().likeDao().getAll()
            Result.Success(list)
        } catch (e: Exception) {
            Result.Error(Exception("获取收藏数据失败"))
        }
    }

    fun toggleLike(composeId: Int): Boolean {
        val likeWidgets = getLikeStatus(composeId)
        return if (likeWidgets.isEmpty()) {
            var result = add(composeId)
            result >= 0
        } else {
            var result = delete(likeWidgets.first())
            result <= 0
        }
    }


    fun getLikeStatus(widgetId: Int): List<LikeWidget> {
        return db.likeDao().getAllById(widgetId)
    }

    private fun delete(widget: LikeWidget): Int {
        return db.likeDao().delete(widget)
    }


    private fun add(widgetId: Int): Long {
        return db.likeDao().insertAll(LikeWidget(widgetId = widgetId, id = 0))
    }


}