package com.luboganev.testground.demos.ipcMessenger.client

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.luboganev.testground.R
import com.luboganev.testground.ipcshared.messenger.MessengerContract


class MessengerClientDemoActivity: AppCompatActivity() {

    val messageText by lazy { findViewById(R.id.messageText) as EditText }
    val sendMessengeButton by lazy { findViewById(R.id.sendMessenge) as Button }

    private var boundToService: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messengerclientdemo)
        sendMessengeButton.setOnClickListener {
            if (boundToService) {
                val text = messageText.text?.toString()
                if (!text.isNullOrEmpty()) {
                    sendMessage(text.toString())
                }
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
            messenger = null
        }
    }

    private var messenger: Messenger? = null

    private val messengerServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            boundToService = false
            messenger = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            messenger = Messenger(service)
            boundToService = true
        }
    }

    private fun sendMessage(messageText: String) {
        if (boundToService) {
            val message = Message.obtain(null, MessengerContract.WHAT_SAY_HELLO, 0, 0)
            message.data = generateMessageData(messageText)
            messenger?.send(message)
        }
    }

    private fun generateMessageData(messageText: String): Bundle {
        val data = Bundle()
        data.putString(MessengerContract.KEY_MESSAGE, messageText)
        return data
    }
}
