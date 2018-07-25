package zr.com.aaclifecycledemo.lifecycle;

import android.app.Fragment;
import android.os.Bundle;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public class ZRReportFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dispatch(ZRLifecycle.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatch(ZRLifecycle.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatch(ZRLifecycle.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        dispatch(ZRLifecycle.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatch(ZRLifecycle.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatch(ZRLifecycle.ON_DESTROY);
    }

    private void dispatch(int status) {
        if (getActivity() instanceof ZRLifecycleRegistryOwner) {
            ((ZRLifecycleRegistryOwner) getActivity()).getLifecycle().handleLifecycleEvent(status);
        }
    }
}
