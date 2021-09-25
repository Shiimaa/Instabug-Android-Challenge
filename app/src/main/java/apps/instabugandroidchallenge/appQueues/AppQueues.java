package apps.instabugandroidchallenge.appQueues;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class AppQueues {
    private static HandlerThread dbHandlerThread;
    private static Handler dbHandler;

    private static HandlerThread networkHandlerThread;
    private static Handler networkHandler;

    private static Handler UiHandler;

    public static void postToDbHandler(Runnable runnable) {
        if (dbHandlerThread == null) {
            dbHandlerThread = new HandlerThread("DBHandlerThread");
            dbHandlerThread.start();
            dbHandler = new Handler(dbHandlerThread.getLooper());
        }

        dbHandler.post(runnable);
    }

    public static void postToNetworkHandler(Runnable runnable) {
        if (networkHandlerThread == null) {
            networkHandlerThread = new HandlerThread("NetworkHandlerThread");
            networkHandlerThread.start();
            networkHandler = new Handler(networkHandlerThread.getLooper());
        }

        networkHandler.post(runnable);
    }

    public static void postToUiHandler(Runnable runnable) {
        if (UiHandler == null) {
            UiHandler = new Handler(Looper.getMainLooper());
        }

        UiHandler.post(runnable);
    }
}
