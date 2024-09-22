package com.example.dayj.network.api.adapter

import com.example.dayj.network.api.response.StatisticsDate
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class StatisticsDateJsonAdapter {
    @FromJson
    fun fromJson(jsonList: List<Map<String, Int>>): List<StatisticsDate> {
        return jsonList.map { map ->
            val entry = map.entries.first()
            StatisticsDate(entry.key, entry.value)
        }
    }

    @ToJson
    fun toJson(dailyDataList: List<StatisticsDate>): List<Map<String, Int>> {
        return dailyDataList.map { dailyData ->
            mapOf(dailyData.date to dailyData.value)
        }
    }
}