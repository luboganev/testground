package com.luboganev.testground.demos.localBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * <p>This class offers a simple messaging center through which various app components can send and receive simple messages
 * containing data in the form of a {@link android.os.Bundle}
 * </p>
 *
 * <p>This class represents a wrapper around the {@link android.support.v4.content.LocalBroadcastManager LocalBroadcastManager}.
 * It encapsulates the specific BroadcastReceiver registration, deregistration, sending and receiving processing needed to fire
 * local broadcast Intents for particular Intent Actions and receive their contents. It offers the simplified interface
 * {@link LocalMessenger.OnReceiveBroadcastListener OnReceiveBroadcastListener} which
 * a listener to a particular broadcast intent action can implement in order to receive the Bundle with extras send with the Intent.
 * </p>
 *
 * <p>This class is implemented as a Singleton and does not keep any strong references to any registered listener in order to prevent
 * possible memory leaks. This wrapper also cleans up any local dependencies related to a registered listener in case its object is
 * collected from the Garbage Collector.
 * </p>
 */
public class LocalMessenger {

    // Private constructor prevents instantiation from other classes
    private LocalMessenger() {
        this.mListeners = new HashMap<String, WeakReference<OnReceiveBroadcastListener>>();
        this.mRegisteredBroadcastReceivers = new HashMap<String, BroadcastReceiver>();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final LocalMessenger INSTANCE = new LocalMessenger();
    }

    public static LocalMessenger getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *  Holds weak references to the broadcast listeners
     */
    private HashMap<String, WeakReference<OnReceiveBroadcastListener>> mListeners;

    /**
     *  Holds references to the registered BroadcastReceivers
     */
    private HashMap<String, BroadcastReceiver> mRegisteredBroadcastReceivers;

    /**
     * Builds a new broadcast receiver which calls the subscriber for a particular action
     * and takes care of unregistering itself and cleaning up the subscribers in case
     * the subscriber object reference is not available anymore.
     */
    private BroadcastReceiver getNewBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Check if this key is in the subscribers at all
                if(!mListeners.containsKey(intent.getAction())) {
                    unregisterBroadcastReceiver(context, intent.getAction());
                    return;
                }

                OnReceiveBroadcastListener listener = mListeners.get(intent.getAction()).get();
                if(listener != null) {
                    listener.onReceiveBroadcast(intent.getExtras());
                } else {
                    // Unregister broadcast and remove subscriber weak reference
                    // from the subscribers if the reference is lost
                    unregisterBroadcastReceiver(context, intent.getAction());
                    mListeners.remove(intent.getAction());
                }
            }
        };
    }

    /**
     *  Unregisters BroadcastReceiver for a particular action
     *
     * @param context
     *      Need context to use LocalBroadcastManager
     * @param action
     *      The Broadcast Intent Action
     */
    private void unregisterBroadcastReceiver(Context context, String action) {
        BroadcastReceiver br = mRegisteredBroadcastReceivers.get(action);
        if(br != null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(br);
            mRegisteredBroadcastReceivers.remove(action);
        }
    }

    /**
     *  A listener Interface which receivers have to implement in order
     *  to be invoked when broadcast is sent
     */
    public interface OnReceiveBroadcastListener {
        void onReceiveBroadcast(Bundle extras);
    }

    /**
     *  Sets a listener object for a particular Broadcast Intent Action
     *
     * @param context
     *      Need context to use LocalBroadcastManager
     * @param action
     *      The Broadcast Intent Action
     * @param listener
     *      The listener to be invoked
     */
    public void setListener(Context context, String action, OnReceiveBroadcastListener listener) {
        mListeners.put(action, new WeakReference<OnReceiveBroadcastListener>(listener));

        if(!mRegisteredBroadcastReceivers.containsKey(action)) {
            BroadcastReceiver br = getNewBroadcastReceiver();
            mRegisteredBroadcastReceivers.put(action, br);

            IntentFilter filter = new IntentFilter(action);
            LocalBroadcastManager.getInstance(context).registerReceiver(br, filter);
        }
    }

    /**
     *  Removes subscriber for a particular action
     *
     * @param context
     *      Need context to use LocalBroadcastManager
     * @param action
     *      The Broadcast Intent Action
     */
    public void removeListener(Context context, String action) {
        mListeners.remove(action);
        unregisterBroadcastReceiver(context,action);
    }

    /**
     *  Sends a local broadcast for a particular Intent Action. If there is any
     *  registered receiver for this Action, this method blocks until the receiver
     *  is executed.
     *
     * @param context
     *      Need context to use LocalBroadcastManager
     * @param action
     *      The Broadcast Intent Action
     * @param extras
     *      A bundle with extras that should be sent
     */
    public void sendBlockingBroadcast(Context context, String action, Bundle extras) {
        if(mRegisteredBroadcastReceivers.containsKey(action)) {
            Intent i = new Intent(action);
            i.putExtras(extras);
            LocalBroadcastManager.getInstance(context).sendBroadcastSync(i);
        }
    }

    /**
     *  Sends a local broadcast for a particular Intent Action.
     *
     * @param context
     *      Need context to use LocalBroadcastManager
     * @param action
     *      The Broadcast Intent Action
     * @param extras
     *      A bundle with extras that should be sent
     */
    public void sendBroadcast(Context context, String action, Bundle extras) {
        if(mRegisteredBroadcastReceivers.containsKey(action)) {
            Intent i = new Intent(action);
            i.putExtras(extras);
            LocalBroadcastManager.getInstance(context).sendBroadcast(i);
        }
    }



}
