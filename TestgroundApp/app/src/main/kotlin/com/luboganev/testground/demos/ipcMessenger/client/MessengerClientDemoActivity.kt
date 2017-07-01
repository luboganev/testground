package com.luboganev.testground.demos.ipcMessenger.client

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.luboganev.testground.R
import com.luboganev.testground.ipcshared.TwoIntegersContainer
import com.luboganev.testground.ipcshared.messenger.MessengerContract


class MessengerClientDemoActivity: AppCompatActivity() {

    val messageText by lazy { findViewById(R.id.messageText) as EditText }
    val sendMessengerButton by lazy { findViewById(R.id.sendMessenge) as Button }
    val sendTwoIntegersButton by lazy { findViewById(R.id.sendTwoIntegers) as Button }

    private var boundToService: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messengerclientdemo)
        sendMessengerButton.setOnClickListener {
            if (boundToService) {
                val text = messageText.text?.toString()
                if (!text.isNullOrEmpty()) {
                    sendHelloMessage(text.toString())
                }
            }
        }
        sendTwoIntegersButton.setOnClickListener {
            if (boundToService) {
                sendAddTwoIntegersMessage()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindToService()
    }

    override fun onStop() {
        super.onStop()
        unbindFromService()
    }

    private fun bindToService() {
        if (!boundToService) {
            bindService(MessengerContract.messengerServiceBindIntent, messengerServiceConnection, BIND_AUTO_CREATE)
        }
    }

    private fun unbindFromService() {
        if (boundToService) {
            unbindService(messengerServiceConnection)
            boundToService = false
            serviceCallsMessenger = null
        }
    }

    private var serviceCallsMessenger: Messenger? = null

    private val messengerServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            boundToService = false
            serviceCallsMessenger = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceCallsMessenger = Messenger(service)
            boundToService = true
        }
    }

    private fun sendHelloMessage(messageText: String) {
        if (boundToService) {
            val oneWayMessage = MessengerContract.SayHello.generateMessage(messageText)
            serviceCallsMessenger?.send(oneWayMessage)
        }
    }

    private fun sendAddTwoIntegersMessage() {
        if (boundToService) {
            val payload = TwoIntegersContainer(1300, 37)
            val requestResponseMessage = MessengerContract.AddTwoIntegers.generateRequestMessage(payload, callbackMessenger)
            serviceCallsMessenger?.send(requestResponseMessage)
        }
    }

    val callbackMessenger by lazy { Messenger(CallbackHandler(applicationContext)) }

    private class CallbackHandler(val applicationContext: Context) : Handler() {
        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                MessengerContract.WHAT_ADD_TWO_NUMBERS_RESULT -> {
                    val result = MessengerContract.AddTwoIntegers.parseResponseMessageData(msg.data)
                    Toast.makeText(applicationContext, "Got result of integer addition from service:  $result", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(applicationContext, "Error: service returned unexpected WHAT: ${msg?.what}", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}
