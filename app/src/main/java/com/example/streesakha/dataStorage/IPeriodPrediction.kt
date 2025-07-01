package com.example.streesakha.dataStorage

import java.time.LocalDate

interface IPeriodPrediction {
    fun getPredictedPeriodDate(): LocalDate?
}
