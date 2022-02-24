package pe.devpicon.android.codelab.pomodoro.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.devpicon.android.codelab.pomodoro.RouteNavigator
import pe.devpicon.android.codelab.pomodoro.login.LoginNavigator
import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator
import pe.devpicon.android.codelab.pomodoro.timer.TimerNavigator
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoginNavigator(): LoginNavigator = RouteNavigator

    @Singleton
    @Provides
    fun provideRouteNavigator(): RouteNavigator = RouteNavigator

    @Singleton
    @Provides
    fun provideTaskNavigator(): TaskNavigator = RouteNavigator

    @Singleton
    @Provides
    fun provideTimerNavigator(): TimerNavigator = RouteNavigator
}

