package com.luboganev.testground.demos.ipcMessenger.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.luboganev.testground.ipcshared.messenger.MessengerContract

class MessengerService : Service() {

    class IncomingHandler(val applicationContext: Context) : Handler() {

        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                MessengerContract.WHAT_SAY_HELLO -> {
                    val incomingMessage = MessengerContract.SayHello.parseSayHelloMessageData(msg.data)
                    Sandwich.serve(applicationContext, "Client says hello", incomingMessage)
                }
                MessengerContract.WHAT_ADD_TWO_NUMBERS -> {
                    val incomingRequest = MessengerContract.AddTwoIntegers.parseRequestMessageData(msg.data)
                    if (incomingRequest != null) {
                        Sandwich.serve(applicationContext, "Client sends two integers",
                            "Adding ${incomingRequest?.first} and ${incomingRequest?.second}")

                        val resultMessage = MessengerContract.AddTwoIntegers.generateResponseMessage(incomingRequest.first + incomingRequest.second)
                        msg.replyTo.send(resultMessage)
                    } else {
                        Sandwich.serve(applicationContext, "Error",
                                "Payload corrupted. TwoIntegersContainer expected")
                    }
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
