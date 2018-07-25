package zr.com.aaclifecycledemo.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ReportFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public class ZRLifecycleDispatcher {

    private static final String REPORT_FRAGMENT_TAG = "com.zr.lifecycle"
            + ".LifecycleDispatcher.report_fragment_tag";

    private static AtomicBoolean sInitialized = new AtomicBoolean(false);

    public static void init(Context context) {
        if (sInitialized.getAndSet(true)) {
            return;
        }
        ((Application) context.getApplicationContext())
                .registerActivityLifecycleCallbacks(new DispatcherActivityCallback());
    }

    static ReportFragment get(Activity activity) {
        return (ReportFragment) activity.getFragmentManager().findFragmentByTag(
                REPORT_FRAGMENT_TAG);
    }


    @SuppressWarnings("WeakerAccess")
    @VisibleForTesting
    static class DispatcherActivityCallback extends ZREmptyActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            android.app.FragmentManager manager = activity.getFragmentManager();
            if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
                manager.beginTransaction().add(new ZRReportFragment(), REPORT_FRAGMENT_TAG).commit();
                // Hopefully, we are the first to make a transaction.
                manager.executePendingTransactions();
            }
        }
    }


}
