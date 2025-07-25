package com.example.streesakha.statistics

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.streesakha.dataStorage.ICalculationsHelper
import com.example.streesakha.dataStorage.IOvulationPrediction
import com.example.streesakha.dataStorage.IPeriodDatabaseHelper
import com.example.streesakha.dataStorage.IPeriodPrediction
import com.example.streesakha.extensions.formatToOneDecimalPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.example.streesakha.R

class StatisticsViewModel(
    @SuppressLint("StaticFieldLeak") private val appContext: Context,
    private val periodDatabaseHelper: IPeriodDatabaseHelper,
    private val calcHelper: ICalculationsHelper,
    private val ovulationPrediction: IOvulationPrediction,
    private val periodPrediction: IPeriodPrediction,
) : ViewModel() {

    private val dash = "-"
    private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    private val _viewState = MutableStateFlow(
        ViewState()
    )
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    data class ViewState(
        val trackedPeriods: String? = null,
        val averageCycleLength: String? = null,
        val averagePeriodLength: String? = null,
        val averageLutealLength: String? = null,
        val follicleGrowthDays: String? = null,
        val ovulationPredictionDate: String? = null,
        val periodPredictionDate: String? = null,
        val ovulationCount: String? = null,
    )

    fun refreshData() {
        _viewState.update {
            it.copy(
                trackedPeriods = periodDatabaseHelper.getPeriodCount().toString(),
                averageCycleLength = formatDays(calcHelper.averageCycleLength().formatToOneDecimalPoint()),
                averagePeriodLength = formatDays(calcHelper.averagePeriodLength().formatToOneDecimalPoint()),
                averageLutealLength =formatDays(calcHelper.averageLutealLength().formatToOneDecimalPoint()),
                follicleGrowthDays = calcHelper.averageFollicalGrowthInDays().formatToOneDecimalPoint(),
                ovulationPredictionDate = ovulationPrediction.getPredictedOvulationDate()?.format(dateFormatter) ?: dash,
                periodPredictionDate = periodPrediction.getPredictedPeriodDate()?.format(dateFormatter) ?: dash,
                ovulationCount = periodDatabaseHelper.getOvulationCount().toString()
            )
        }
    }

    private fun formatDays(text: String): String {
        val days = appContext.getString(R.string.days)
        return "$text $days"
    }
}
