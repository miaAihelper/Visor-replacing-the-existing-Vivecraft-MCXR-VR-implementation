package pojlib.util;

import android.content.Context;
import android.util.Log;

import pojlib.visor.VisorBridge;

public class VLoader {
    private static final String TAG = "VLoader";

    static {
        try {
            System.loadLibrary("vloader");
            Log.i(TAG, "Loaded native vloader library.");
        } catch (UnsatisfiedLinkError e) {
            Log.w(TAG, "Native vloader library unavailable; runtime replacement remains active in Java layer.");
        }
    }

    public static void setAndroidInitInfo(Context ctx) {
        if (ctx == null) {
            return;
        }

        VisorBridge.setRuntime("visor");
        VisorBridge.setRuntimeMode("replacement");
        VisorBridge.initialize(ctx);
    }
}
