package com.example.teamleadapp.data.remote.models

enum class Status {
    /**
     * Показать лоадер
     */
    SHOW_LOADING,

    /**
     * Скрыть лоадер
     *
     * если нет других идущих запросов
     */
    HIDE_LOADING
}