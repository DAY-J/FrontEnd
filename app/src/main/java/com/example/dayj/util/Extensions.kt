package com.example.dayj.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source

object Extensions {
    private const val INTEGER_REGEX = "[^0-9]"

    val UPPER_TIRAMISU_STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES
    )
    val UNDER_TIRAMISU_STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun Int.checkIsUpperSdkVersion(): Boolean {
        return Build.VERSION.SDK_INT >= this
    }

    fun storagePermissions(): Array<String> {
        return if (Build.VERSION_CODES.TIRAMISU.checkIsUpperSdkVersion()) UPPER_TIRAMISU_STORAGE_PERMISSIONS else UNDER_TIRAMISU_STORAGE_PERMISSIONS
    }

    fun String.toTextPlainRequestBody(): RequestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())
    fun String.toApplicationJsonRequestBody(): RequestBody = this.toRequestBody("application/json".toMediaTypeOrNull())

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        }
    }

    @SuppressLint("Range")
    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }

    fun String?.extractInteger(): String {
        this?.trim()
        return this?.replace(INTEGER_REGEX.toRegex(), "").toString()
    }
}