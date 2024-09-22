package com.example.dayj.ui.lounge.domain

enum class LoungeTagEnum(val tagText: String, val dto: String) {
    ALL(tagText = "전체", "ALL"),
    HEALTH(tagText = "건강", "HEALTH"),
    STUDY(tagText = "공부", "STUDY"),
    INVESTMENT(tagText = "재테크", "INVESTMENT"),
    READING(tagText = "독서","READING"),
    HOBBY(tagText = "취미", "HOBBY"),
    HABIT(tagText = "생활습관", "LIFESTYLE"),
    CAREER(tagText = "커리어", "CAREER");

    companion object {
        fun findTagUsingDto(dto: String): LoungeTagEnum {
            return entries.find { it.dto == dto } ?: HEALTH
        }
    }
}