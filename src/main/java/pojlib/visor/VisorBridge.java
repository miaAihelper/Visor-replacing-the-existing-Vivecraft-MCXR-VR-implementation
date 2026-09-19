package pojlib.visor;

import android.content.Context;
import android.util.Log;

import java.io.File;

public final class VisorBridge {
    private static final String TAG = "VisorBridge";
    private static final String LIB_OPENXR = "openxr_loader";
    private static final String PROPERTY_RUNTIME = "pojlib.vr.runtime";
    private static final String PROPERTY_MODE = "pojlib.vr.mode";
    private static volatile boolean initialized = false;

    private VisorBridge() {
    }

    public static synchronized void initialize(Context context) {
        if (initialized) {
            return;
        }

        try {
            System.loadLibrary(LIB_OPENXR);
            Log.i(TAG, "Loaded OpenXR loader from native libraries.");
        } catch (UnsatisfiedLinkError e) {
            Log.w(TAG, "OpenXR loader is not available in this runtime: " + e.getMessage());
        }

        String runtime = System.getProperty(PROPERTY_RUNTIME, "visor");
        String mode = System.getProperty(PROPERTY_MODE, "normal");

        Context appContext = context.getApplicationContext();
        File dir = appContext.getFilesDir();
        if (dir != null) {
            Log.i(TAG, "VR runtime initialized with mode=" + mode + ", runtime=" + runtime + ", filesDir=" + dir.getAbsolutePath());
        }

        initialized = true;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setRuntimeMode(String mode) {
        System.setProperty(PROPERTY_MODE, mode == null ? "normal" : mode);
    }

    public static void setRuntime(String runtime) {
        System.setProperty(PROPERTY_RUNTIME, runtime == null ? "visor" : runtime);
    }
}
