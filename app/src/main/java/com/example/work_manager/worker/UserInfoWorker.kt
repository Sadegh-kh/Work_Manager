package com.example.work_manager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class UserInfoWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val info = doMyJop()
            Result.success(
                workDataOf(
                    "info" to info
                )
            )
        }catch (ex:Exception){
            Result.failure()
        }
    }

    private fun doMyJop(): String {

        val name = inputData.getString("name")
        val familyName=inputData.getString("familyName")
        val zipCode=inputData.getString("zipCode")

        val information= "$name $familyName $zipCode"

        return information
    }
}