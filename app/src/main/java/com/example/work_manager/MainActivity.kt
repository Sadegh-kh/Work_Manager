package com.example.work_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.work_manager.worker.NotificationWorker

class MainActivity : AppCompatActivity() {
    lateinit var workManager:WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager=WorkManager.getInstance(this)

        //PeriodicWorkRequest or OneTimeWorkRequest
        val notificationWorker=OneTimeWorkRequest.from(NotificationWorker::class.java)
        workManager.enqueue(notificationWorker)
    }
}