package com.books.ex.apitest.apiclient

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.books.ex.apitest.R
import com.books.ex.apitest.common.logger.LogHelper
import java.io.FileDescriptor
import java.io.IOException

class ApiClientFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sample_action) {
            performFileSearch()
        }
        return true
    }

    fun performFileSearch() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)

        intent.type = "image/*"
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        LogHelper.i(TAG, "Received an \"Activity Result\"")
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null) {
                uri = resultData.getData()
                LogHelper.i(TAG, "Uri: $uri")
                showImage(uri)
            }
        }
    }

    fun showImage(uri: Uri?) {
//        if (uri != null) {
//            val fm = activity!!.supportFragmentManager
//            val imageDialog = ImageDialogFragment()
//            val fragmentArguments = Bundle()
//            fragmentArguments.putParcelable("URI", uri)
//            imageDialog.arguments = fragmentArguments
//            imageDialog.show(fm, "image_dialog")
//        }
    }

//    class ImageDialogFragment : DialogFragment() {
//        private var mDialog: Dialog? = null
//        private var mUri: Uri? = null
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            mUri = arguments!!.getParcelable("URI")
//        }
//
//        private fun getBitmapFromUri(uri: Uri): Bitmap? {
//            var parcelFileDescriptor: ParcelFileDescriptor? = null
//            return try {
//                parcelFileDescriptor = activity!!.contentResolver.openFileDescriptor(uri, "r")
//                val fileDescriptor: FileDescriptor = parcelFileDescriptor.getFileDescriptor()
//                val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//                parcelFileDescriptor.close()
//                image
//            } catch (e: Exception) {
//                LogHelper.e(TAG, "Failed to load image.", e)
//                null
//            } finally {
//                try {
//                    if (parcelFileDescriptor != null) {
//                        parcelFileDescriptor.close()
//                    }
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    LogHelper.e(TAG, "Error closing ParcelFile Descriptor")
//                }
//            }
//        }
//
//        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//            mDialog = super.onCreateDialog(savedInstanceState)
//            mDialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
//            val imageView = ImageView(activity)
//            mDialog!!.setContentView(imageView)
//
//            val imageLoadAsyncTask: AsyncTask<Uri?, Void?, Bitmap?> =
//                object : AsyncTask<Uri?, Void?, Bitmap?>() {
//                    override fun doInBackground(vararg uris: Uri?): Bitmap? {
//                        dumpImageMetaData(uris[0])
//                        return uris[0]?.let { getBitmapFromUri(it) }
//                    }
//
//                    override fun onPostExecute(bitmap: Bitmap?) {
//                        imageView.setImageBitmap(bitmap)
//                    }
//                }
//            imageLoadAsyncTask.execute(mUri)
//            return mDialog as Dialog
//        }
//
//        override fun onStop() {
//            super.onStop()
//            if (dialog != null) {
//                dialog!!.dismiss()
//            }
//        }
//
//        /**
//         * Grabs metadata for a document specified by URI, logs it to the screen.
//         *
//         * @param uri The uri for the document whose metadata should be printed.
//         */
//        fun dumpImageMetaData(uri: Uri?) {
//            // BEGIN_INCLUDE (dump_metadata)
//
//            // The query, since it only applies to a single document, will only return one row.
//            // no need to filter, sort, or select fields, since we want all fields for one
//            // document.
//            val cursor = activity!!.contentResolver
//                .query(uri!!, null, null, null, null, null)
//            try {
//                // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
//                // "if there's anything to look at, look at it" conditionals.
//                if (cursor != null && cursor.moveToFirst()) {
//
//                    // Note it's called "Display Name".  This is provider-specific, and
//                    // might not necessarily be the file name.
//                    val displayName = cursor.getString(
//                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                    )
//                    LogHelper.i(TAG, "Display Name: $displayName")
//                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
//                    // If the size is unknown, the value stored is null.  But since an int can't be
//                    // null in java, the behavior is implementation-specific, which is just a fancy
//                    // term for "unpredictable".  So as a rule, check if it's null before assigning
//                    // to an int.  This will happen often:  The storage API allows for remote
//                    // files, whose size might not be locally known.
//                    var size: String? = null
//                    size = if (!cursor.isNull(sizeIndex)) {
//                        // Technically the column stores an int, but cursor.getString will do the
//                        // conversion automatically.
//                        cursor.getString(sizeIndex)
//                    } else {
//                        "Unknown"
//                    }
//                    LogHelper.i(TAG, "Size: $size")
//                }
//            } finally {
//                cursor?.close()
//            }
//            // END_INCLUDE (dump_metadata)
//        }
//    }

    companion object {
        private const val READ_REQUEST_CODE = 1337
        const val TAG = "ApiClientFragment"
    }
}