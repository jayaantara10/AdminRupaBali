package id.jayaantara.adminrupabali.common

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import id.jayaantara.adminrupabali.BuildConfig.BASE_URL
import id.jayaantara.adminrupabali.R
import id.jayaantara.adminrupabali.common.timeManager.timeStamp
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

object InternetUtils {

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}

object permissionUtils{
    fun checkAndRequestPermission(
        context: Context,
        permission: String,
        launcher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        } else {
            launcher.launch(permission)
        }
    }
}

object contentLinkBuilder{

    fun adminVerifyData(filename: String): String{
        val accessFileUrl = "admin/verification/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun adminProfileData(filename: String): String{
        val accessFileUrl = "admin/profile-picture/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun userVerifyData(filename: String): String{
        val accessFileUrl = "data/user/verification/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun userProfileData(filename: String): String{
        val accessFileUrl = "data/user/profile/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun fineArtTypeData(filename: String): String{
        val accessFileUrl = "data/fineArtType/image/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun artworkTypeData(filename: String): String{
        val accessFileUrl = "data/artworkType/image/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun artworkData(filename: String): String{
        val accessFileUrl = "data/artwork/document/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun achievementData(filename: String): String{
        val accessFileUrl = "data/artworkAchievement/document/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun artworkDetailData(filename: String): String{
        val accessFileUrl = "data/artworkDetail/image/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun eventData(filename: String): String{
        val accessFileUrl = "data/event/image/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }

    fun newsData(filename: String): String{
        val accessFileUrl = "data/news/image/"
        return "${BASE_URL}${accessFileUrl}${filename}"
    }
}

object timeManager {
    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())
}

object fileManager {

    fun createCustomTempFileJpg(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun createCustomTempFilePdf(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File.createTempFile(timeStamp, ".pdf", storageDir)
    }

    fun createFileJpg(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }

    fun createFilePdf(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.pdf")
    }

    fun rotateFile(file: File, isBackCamera: Boolean = false) {
        val matrix = Matrix()
        val bitmap = BitmapFactory.decodeFile(file.path)
        val rotation = if (isBackCamera) 90f else -90f
        matrix.postRotate(rotation)
        if (!isBackCamera) {
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        }
        val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
    }

    fun uriToFileImage(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFileJpg(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun uriToFileDocument(selectedDocument: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFilePdf(context)

        val inputStream = contentResolver.openInputStream(selectedDocument) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(5120)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }
}