package com.luboganev.testground.demos.ipcMessenger.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import com.luboganev.testground.ipcshared.messenger.MessengerContract

class MessengerService : Service() {

    class IncomingHandler(val applicationContext: Context) : Handler() {

        fun parseSayHelloMessageData(data: Bundle?): String {
            return data?.getString(MessengerContract.KEY_MESSAGE, "") ?: ""
        }

        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                MessengerContract.WHAT_SAY_HELLO -> {
                    val incomingMessage = parseSayHelloMessageData(msg?.data)
                    Sandwich.serve(applicationContext, "Client says hello", incomingMessage)
                }
                else -> {
                    Sandwich.serve(applicationContext, "Error", "Client sends message with unknown WHAT ${msg?.what}")
                }
            }
        }
    }

    val messenger by lazy { Messenger(IncomingHandler(applicationContext)) }

    override fun onBind(intent: Intent): IBinder? {
        return messenger.binder
    }
}
