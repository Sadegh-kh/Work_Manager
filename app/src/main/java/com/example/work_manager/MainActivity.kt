package com.example.work_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.work_manager.worker.NotificationWorker
import com.example.work_manager.worker.UserInfoWorker

class MainActivity : AppCompatActivity() {
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)

        //PeriodicWorkRequest or OneTimeWorkRequest
        val notificationWorker = OneTimeWorkRequest.from(NotificationWorker::class.java)


        val constraints=Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val userInfoWorker = OneTimeWorkRequestBuilder<UserInfoWorker>()
            .setInputData(
                workDataOf(
                    // We can use a Pair() for key value or
                    "name" to "Sadegh",
                    "familyName" to "Khoshbayan",
                    "zipCode" to "9781621321"
                )
            )
            //for same worker
            .addTag("userWorker")
            .setConstraints(constraints)
            .build()

        workManager.enqueue(userInfoWorker)

        workManager
            .getWorkInfosByTagLiveData("userWorker")
            .observe(this) {
                val userInfoWorker = it[0]
                val information = userInfoWorker.outputData.getString("info") ?: "null"
                if (userInfoWorker.state==WorkInfo.State.SUCCEEDED){
                    Log.v("testOutputData", information)
                }
            }
    }
}

