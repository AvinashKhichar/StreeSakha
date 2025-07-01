package com.example.streesakha.dataStorage

import com.example.streesakha.dataStorage.ICalculationsHelper
import com.example.streesakha.dataStorage.IPeriodDatabaseHelper
import com.example.streesakha.dataStorage.IPeriodPrediction
import java.time.LocalDate

class PeriodPrediction(
    private val dbHelper: IPeriodDatabaseHelper,
    private val calcHelper: ICalculationsHelper,
) : IPeriodPrediction {

    override fun getPredictedPeriodDate(): LocalDate? {
        val periodCount = dbHelper.getPeriodCount()
        if (periodCount < 2) {
            return null
        }

        return calcHelper.calculateNextPeriod()
    }
}