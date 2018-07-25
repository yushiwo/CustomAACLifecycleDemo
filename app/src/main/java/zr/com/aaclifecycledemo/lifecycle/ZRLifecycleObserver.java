package zr.com.aaclifecycledemo.lifecycle;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public interface ZRLifecycleObserver {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onRestart();
}
