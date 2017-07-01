package com.luboganev.testground.ipcshared.messenger

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Messenger
import com.luboganev.testground.ipcshared.TwoIntegersContainer


class MessengerContract {

    companion object {
        val messengerServiceBindIntent: Intent
            get() {
                val intent = Intent()
                val packageName = "com.luboganev.testground"
                intent.component = ComponentName(packageName,
                        "$packageName.demos.ipcMessenger.service.MessengerService")
                return intent
            }

        val WHAT_SAY_HELLO = 1
        val WHAT_ADD_TWO_NUMBERS = 2
        val WHAT_ADD_TWO_NUMBERS_RESULT = 3
    }

    class SayHello {
        companion object {
            val KEY_MESSAGE = "message"

            fun generateMessage(messageText: String) : Message {
                val message = Message.obtain(null, WHAT_SAY_HELLO, 0, 0)
                message.data = generateMessageData(messageText)
                return message
            }

            private fun generateMessageData(messageText: String): Bundle {
                val data = Bundle()
                data.putString(KEY_MESSAGE, messageText)
                return data
            }

            fun parseSayHelloMessageData(data: Bundle?): String? {
                return data?.getString(MessengerContract.SayHello.KEY_MESSAGE, null)
            }
        }
    }

    class AddTwoIntegers {
        companion object {
            val KEY_NUMBERS_CONTAINER = "numbers_container"
            val KEY_NUMBERS_ADDITION_RESULT = "numbers_addition_result"

            fun generateRequestMessage(payload: TwoIntegersContainer, replyTo: Messenger) : Message {
                val message = Message.obtain(null, WHAT_ADD_TWO_NUMBERS, 0, 0)
                message.data = generateRequestMessageData(payload)
                message.replyTo = replyTo
                return message
            }

            private fun generateRequestMessageData(payload: TwoIntegersContainer): Bundle {
                val data = Bundle()
                data.putParcelable(KEY_NUMBERS_CONTAINER, payload)
                return data
            }

            fun parseRequestMessageData(data: Bundle?): TwoIntegersContainer? {
                if (data == null) {
                    return null
                }

                if(data.containsKey(MessengerContract.AddTwoIntegers.KEY_NUMBERS_CONTAINER)) {
                    return data.getParcelable(MessengerContract.AddTwoIntegers.KEY_NUMBERS_CONTAINER)
                } else {
                    return null
                }
            }

            fun generateResponseMessage(additionResult: Int) : Message {
                val message = Message.obtain(null, WHAT_ADD_TWO_NUMBERS_RESULT, 0, 0)
                message.data = generateResponseMessageData(additionResult)
                return message
            }

            private fun generateResponseMessageData(additionResult: Int): Bundle {
                val data = Bundle()
                data.putInt(KEY_NUMBERS_ADDITION_RESULT, additionResult)
                return data
            }

            fun parseResponseMessageData(data: Bundle?): Int? {
                if (data == null) {
                    return null
                }

                if(data.containsKey(MessengerContract.AddTwoIntegers.KEY_NUMBERS_ADDITION_RESULT)) {
                    return data.getInt(MessengerContract.AddTwoIntegers.KEY_NUMBERS_ADDITION_RESULT)
                } else {
                    return null
                }
            }
        }
    }
}
