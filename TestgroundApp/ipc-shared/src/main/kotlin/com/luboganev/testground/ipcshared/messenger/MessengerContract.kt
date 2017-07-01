package com.luboganev.testground.ipcshared.messenger

import android.content.ComponentName
import android.content.Intent


class MessengerContract {

    companion object Constants {

        val WHAT_SAY_HELLO = 1
        val KEY_MESSAGE = "message"

        val messengerServiceBindIntent: Intent
            get() {
                val intent = Intent()
                val packageName = "com.luboganev.testground"
                intent.component = ComponentName(packageName,
                        "$packageName.demos.ipcMessenger.service.MessengerService")
                return intent
            }

    }
}
