package com.example.streesakha

import android.app.AlarmManager
import android.app.Application
import android.system.Os.bind
import com.example.streesakha.calendar.CalendarViewModel
import com.example.streesakha.dataStorage.CalculationsHelper
import com.example.streesakha.dataStorage.ExportImport
import com.example.streesakha.dataStorage.ICalculationsHelper
import com.example.streesakha.dataStorage.IExportImport
import com.example.streesakha.dataStorage.IOvulationPrediction
import com.example.streesakha.dataStorage.IPeriodDatabaseHelper
import com.example.streesakha.dataStorage.IPeriodPrediction
import com.example.streesakha.dataStorage.OvulationPrediction
import com.example.streesakha.dataStorage.PeriodDatabaseHelper
import com.example.streesakha.dataStorage.PeriodPrediction
import com.example.streesakha.dataStorage.notifications.AndroidNotificationScheduler
import com.example.streesakha.dataStorage.notifications.IAndroidNotificationScheduler
import com.example.streesakha.dataStorage.notifications.INotificationScheduler
import com.example.streesakha.dataStorage.notifications.NotificationScheduler
import com.example.streesakha.statistics.StatisticsViewModel
import com.example.streesakha.settings.SettingsViewModel
import com.example.streesakha.symptoms.ManageSymptomsViewModel
import com.example.streesakha.utils.DefaultDispatcherProvider
import com.example.streesakha.utils.IDispatcherProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class App : Application() {

    // Koin dependency injection definitions
    private val appModule = module {
        single<IPeriodDatabaseHelper> { PeriodDatabaseHelper(get(), get()) }
        single<ICalculationsHelper> { CalculationsHelper(get()) }
        single<IOvulationPrediction> { OvulationPrediction(get(), get(), get()) }
        single<IPeriodPrediction> { PeriodPrediction(get(), get()) }
        single<IExportImport> { ExportImport(get(), get()) }
        single<INotificationScheduler> { NotificationScheduler(get(), get(), get(), get(), get()) }
        single<IDispatcherProvider> { DefaultDispatcherProvider() }
        single<IAndroidNotificationScheduler> { AndroidNotificationScheduler(get(), get()) }
        single { androidContext().getSystemService(ALARM_SERVICE) as AlarmManager }

        viewModel { CalendarViewModel(get(), get(), get(), get()) }
        viewModel { ManageSymptomsViewModel(get()) }
        viewModel { SettingsViewModel(get(), get(), get(), get()) }
        viewModel { StatisticsViewModel(get(), get(), get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}