package com.luboganev.testground.ipcshared.messenger

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Messenger
import com.luboganev.testground.ipcshared.TwoIntegersContainer


class MessengerContract {

    class InvalidPayloadException(message: String) : RuntimeException(message)

    companion object {
        private const val SERVICE_PACKAGE_NAME = "com.luboganev.testground"
        private const val SERVICE_CLASS_NAME = "$SERVICE_PACKAGE_NAME.demos.ipcMessenger.service.MessengerService"

        val serviceBindIntent: Intent
            get() {
                return Intent()
                        .setComponent(
                            ComponentName(SERVICE_PACKAGE_NAME, SERVICE_CLASS_NAME)
                        )
            }

        const val WHAT_SAY_HELLO = 1
        const val WHAT_ADD_TWO_NUMBERS = 2
        const val WHAT_ADD_TWO_NUMBERS_RESULT = 3
    }

    class SayHello {
        companion object {
            private val PAYLOAD_KEY_MESSAGE = "message"

            fun buildRequestMessage(messageText: String) : Message {
                val message = Message.obtain(null, WHAT_SAY_HELLO, 0, 0)
                message.data = wrapRequestMessagePayload(messageText)
                return message
            }

            private fun wrapRequestMessagePayload(messageText: String): Bundle {
                val payload = Bundle()
                payload.putString(PAYLOAD_KEY_MESSAGE, messageText)
                return payload
            }

            fun parseRequestMessagePayload(payload: Bundle?): String {
                if (payload != null && payload.containsKey(PAYLOAD_KEY_MESSAGE)) {
                    return payload.getString(MessengerContract.SayHello.PAYLOAD_KEY_MESSAGE)
                } else {
                    throw InvalidPayloadException("Payload of SayHello request is missing")
                }
            }
        }
    }

    class AddTwoIntegers {
        companion object {
            private val PAYLOAD_KEY_NUMBERS_CONTAINER = "numbers_container"
            private val PAYLOAD_KEY_NUMBERS_ADDITION_RESULT = "numbers_addition_result"

            fun buildRequestMessage(twoIntegers: TwoIntegersContainer, replyTo: Messenger) : Message {
                val message = Message.obtain(null, WHAT_ADD_TWO_NUMBERS, 0, 0)
                message.data = wrapRequestMessagePayload(twoIntegers)
                message.replyTo = replyTo
                return message
            }

            private fun wrapRequestMessagePayload(twoIntegers: TwoIntegersContainer): Bundle {
                val payload = Bundle()
                payload.putParcelable(PAYLOAD_KEY_NUMBERS_CONTAINER, twoIntegers)
                return payload
            }

            fun parseRequestMessagePayload(payload: Bundle?): TwoIntegersContainer {
                if (payload != null && payload.containsKey(PAYLOAD_KEY_NUMBERS_CONTAINER)) {
                    return payload.getParcelable(PAYLOAD_KEY_NUMBERS_CONTAINER);
                } else {
                    throw InvalidPayloadException("Payload of AddTwoIntegers request is missing")
                }
            }

            fun buildResponseMessage(additionResult: Int) : Message {
                val message = Message.obtain(null, WHAT_ADD_TWO_NUMBERS_RESULT, 0, 0)
                message.data = wrapResponseMessagePayload(additionResult)
                return message
            }

            private fun wrapResponseMessagePayload(additionResult: Int): Bundle {
                val payload = Bundle()
                payload.putInt(PAYLOAD_KEY_NUMBERS_ADDITION_RESULT, additionResult)
                return payload
            }

            fun parseResponseMessagePayload(payload: Bundle?): Int {
                if(payload != null && payload.containsKey(PAYLOAD_KEY_NUMBERS_ADDITION_RESULT)) {
                    return payload.getInt(MessengerContract.AddTwoIntegers.PAYLOAD_KEY_NUMBERS_ADDITION_RESULT)
                } else {
                    throw InvalidPayloadException("Payload of AddTwoIntegers response is missing")
                }
            }
        }
    }
}
