package com.learningcurve.clickitcameraapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import com.learningcurve.clickitcameraapp.presentation.ui.navigation.EMPTY_IMAGE_URI
import java.io.ByteArrayOutputStream

fun uriToEncodedString(
    uri: Uri = EMPTY_IMAGE_URI
): String {
    return Uri.encode(uri.toString())
}

fun uriFromEncodedString(
    encodedString: String
): Uri {
    return Uri.parse(encodedString)
}

fun Uri.toBitmap(context: Context): Bitmap? {
    val bitmap = mutableStateOf<Bitmap?>(null)

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        } else {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap.value
}

// extension function to convert bitmap to byte array
fun Bitmap.toByteArray():ByteArray{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,10,this)
        return toByteArray()
    }
}


// extension function to convert byte array to bitmap
fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,size)
}
