package zr.com.aaclifecycledemo;

import android.app.Activity;
import android.os.Bundle;
import zr.com.aaclifecycledemo.lifecycle.ZRLifecycleRegistry;
import zr.com.aaclifecycledemo.lifecycle.ZRLifecycleRegistryOwner;

public class MainActivity extends Activity implements ZRLifecycleRegistryOwner {

    private ZRLifecycleRegistry lifecycleRegistry = new ZRLifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView myView = findViewById(R.id.view_test);

        getLifecycle().addObserver(myView);

    }

    @Override
    public ZRLifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
