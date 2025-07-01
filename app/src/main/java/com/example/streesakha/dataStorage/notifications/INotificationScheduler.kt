package com.example.streesakha.dataStorage.notifications

interface INotificationScheduler {
    /**
     * Checks if there is enough data to schedule a period reminder notification, then schedules it.
     */
    suspend fun schedulePeriodNotification()
}
