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
                    val incomingMessage = MessengerContract.SayHello.parseRequestMessagePayload(msg.data)
                    Sandwich.serve(applicationContext, "Client says hello", incomingMessage)
                }

                MessengerContract.WHAT_ADD_TWO_NUMBERS -> {
                    try {
                        val twoIntegersContainer = MessengerContract.AddTwoIntegers.parseRequestMessagePayload(msg.data)

                        Sandwich.serve(applicationContext, "Client sends two integers",
                                "Adding ${twoIntegersContainer.first} and ${twoIntegersContainer.second}")

                        val resultMessage = MessengerContract.AddTwoIntegers.buildResponseMessage(
                                twoIntegersContainer.first + twoIntegersContainer.second)

                        msg.replyTo.send(resultMessage)

                    } catch (e: MessengerContract.InvalidPayloadException) {
                        Sandwich.serve(applicationContext, "Error", e.message)
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
