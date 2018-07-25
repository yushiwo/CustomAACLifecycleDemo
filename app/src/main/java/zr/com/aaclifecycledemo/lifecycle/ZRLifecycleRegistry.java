package zr.com.aaclifecycledemo.lifecycle;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public class ZRLifecycleRegistry extends ZRLifecycle {

    private final ZRLifecycleOwner mLifecycleOwner;

    private HashMap<ZRLifecycleObserver, ObserverWithState> mObserverSet =
            new HashMap<>();

    public ZRLifecycleRegistry(@NonNull ZRLifecycleOwner provider) {
        mLifecycleOwner = provider;
    }

    @Override
    public void addObserver(ZRLifecycleObserver observer) {
        ObserverWithState observerWithState = new ObserverWithState(observer);
        mObserverSet.put(observer, observerWithState);
    }

    @Override
    public void removeObserver(ZRLifecycleObserver observer) {
        mObserverSet.remove(observer);
    }


    public void handleLifecycleEvent(int status) {
        for (Map.Entry<ZRLifecycleObserver, ObserverWithState> entry : mObserverSet.entrySet()) {
            entry.getValue().sync(status);
        }
    }

    class ObserverWithState {
        private ZRLifecycleObserver mCallback;

        ObserverWithState(ZRLifecycleObserver observer) {
            mCallback = observer;
        }

        void sync(int status) {
            switch (status) {
                case ZRLifecycle.ON_CREATE:
                    mCallback.onCreate();
                    break;
                case ZRLifecycle.ON_START:
                    mCallback.onStart();
                    break;
                case ZRLifecycle.ON_RESUME:
                    mCallback.onResume();
                    break;
                case ZRLifecycle.ON_PAUSE:
                    mCallback.onPause();
                    break;
                case ZRLifecycle.ON_STOP:
                    mCallback.onStop();
                    break;
                case ZRLifecycle.ON_DESTROY:
                    mCallback.onDestroy();
                    break;
                case ZRLifecycle.ON_RESTART:
                    mCallback.onRestart();
                    break;

                default:

                    break;
            }
        }
    }

}
