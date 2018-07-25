package zr.com.aaclifecycledemo;

import android.app.Application;
import zr.com.aaclifecycledemo.lifecycle.ZRLifecycleDispatcher;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZRLifecycleDispatcher.init(this);
    }
}
