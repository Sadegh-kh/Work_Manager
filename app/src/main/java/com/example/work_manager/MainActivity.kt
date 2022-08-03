package com.example.work_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.work_manager.worker.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)

        //PeriodicWorkRequest or OneTimeWorkRequest
        val notificationWorker =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS, 15, TimeUnit.MINUTES)
                .build()


        val constraints = Constraints.Builder()
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
            .setInitialDelay(10, TimeUnit.MINUTES)
            .build()

        workManager
            .getWorkInfosByTagLiveData("userWorker")
            .observe(this) {
                val userInfoWorker = it[0]
                val information = userInfoWorker.outputData.getString("info") ?: "null"
                if (userInfoWorker.state == WorkInfo.State.SUCCEEDED) {
                    Log.v("testOutputData", information)
                }
            }


        //enqueue Workers and working together
        workManager.beginWith(OneTimeWorkRequest.from(DownloadImageWorker::class.java))
            .then(OneTimeWorkRequest.from(EditImageWorker::class.java))
            .then(OneTimeWorkRequest.from(SavingImageToGalleryWorker::class.java))
            .enqueue()

        //made an unique worker
        workManager.enqueueUniqueWork("NotificationWorker",ExistingWorkPolicy.KEEP,
            OneTimeWorkRequest.from(NotificationWorker::class.java))



    }
}

