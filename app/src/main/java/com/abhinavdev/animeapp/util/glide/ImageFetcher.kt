package com.abhinavdev.animeapp.util.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageFetcher(private val model: ImageModel) : DataFetcher<InputStream> {
    private var stream: InputStream? = null
    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        var connection: HttpURLConnection? = null

        try {
            val imageUrl = URL(model.url)
            connection = imageUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = BufferedInputStream(connection.inputStream)
                val byteArray = readInputStream(stream!!)
                val imageStream = ByteArrayInputStream(byteArray)
                callback.onDataReady(imageStream)
            } else {
                callback.onLoadFailed(IOException("Failed to fetch image. Response code: ${connection.responseCode}"))
            }
        } catch (e: IOException) {
            callback.onLoadFailed(e)
        } finally {
            connection?.disconnect()
        }
    }

    private fun readInputStream(inputStream: InputStream): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int

        while (inputStream.read(buffer).also { length = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, length)
        }

        return byteArrayOutputStream.toByteArray()
    }

    override fun cleanup() {
        // already cleaned up in loadData and ByteArrayInputStream will be GC'd
        if (stream != null) {
            try {
                stream?.close()
            } catch (ignore: IOException) {
                // can't do much about it
            }
        }
    }

    override fun cancel() {
        // cannot cancel
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }
}