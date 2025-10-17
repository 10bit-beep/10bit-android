package com.example.a10bit_android.feature.attendance

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag

class NfcHandler(
    private val activity: Activity,
    private val onTagRead: (String) -> Unit) {

    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity)

    fun enable() {
        val pendingIntent = PendingIntent.getActivity(
            activity, 0,
            Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    fun disable() {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    fun handleIntent(intent: Intent) {
        val tag: Tag? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        }

        tag?.let {
            val tagId = it.id.joinToString("") { byte -> "%02X".format(byte) }
            onTagRead(tagId)
        }
    }
}