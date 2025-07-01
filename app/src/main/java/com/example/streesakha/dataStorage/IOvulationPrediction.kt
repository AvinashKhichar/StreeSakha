package com.example.streesakha.dataStorage

import java.time.LocalDate

interface IOvulationPrediction {
    fun getPredictedOvulationDate(): LocalDate?
}
