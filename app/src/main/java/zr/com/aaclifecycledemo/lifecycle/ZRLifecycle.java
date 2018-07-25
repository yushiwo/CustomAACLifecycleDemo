package zr.com.aaclifecycledemo.lifecycle;

import android.support.annotation.MainThread;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public abstract class ZRLifecycle {

    public final static int ON_CREATE = 1;
    public final static int ON_START = 2;
    public final static int ON_RESUME = 3;
    public final static int ON_PAUSE = 4;
    public final static int ON_STOP = 5;
    public final static int ON_RESTART = 6;
    public final static int ON_DESTROY = 7;

    @MainThread
    public abstract void addObserver(ZRLifecycleObserver observer);

    @MainThread
    public abstract void removeObserver(ZRLifecycleObserver observer);
}
