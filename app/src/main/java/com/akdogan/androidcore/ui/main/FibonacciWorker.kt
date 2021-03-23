package com.akdogan.androidcore.ui.main

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

private const val NOTIFICATION_ID = 12345

class FibonacciWorker(
    appContext: Context,
    workParams: WorkerParameters
) : CoroutineWorker(appContext, workParams){


    override suspend fun doWork(): Result {
        sendNotification(
            appContext = applicationContext,
            title = "Calculation in progress",
            content = "",
            progress = true,
            ongoing = true,
            id = NOTIFICATION_ID
        )
        val lastNumber = calculateFibonacci()
        sendNotification(
            appContext = applicationContext,
            title = "Calculation is finished",
            progress = false,
            content = "The last calculated Number in the Fibonacci Sequence is $lastNumber",
            id = NOTIFICATION_ID

        )
        return Result.success()
    }

}

suspend fun calculateFibonacci(): Int{

    var a = 0
    var b = 1
    do {
        delay(150)
        val c = a + b
        a = b
        b = c
        Log.i("FIBONACCI", "$c")
    } while (checkContinue(a, b))
    return b
}

fun checkContinue(a: Int, b: Int) = Int.MAX_VALUE - a >= b
