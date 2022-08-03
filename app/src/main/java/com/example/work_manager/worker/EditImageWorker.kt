package com.example.work_manager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class EditImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            doMyJop()
            Result.success()
        }catch (ex:Exception){
            Result.failure()
        }
    }

    private fun doMyJop() {
//        for(i in 0..10000000){
//            Log.v("testWorker","Number is $i")
//        }
    }
}