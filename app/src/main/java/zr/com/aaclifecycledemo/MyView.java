package zr.com.aaclifecycledemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import zr.com.aaclifecycledemo.lifecycle.ZRLifecycleObserver;

/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public class MyView extends View implements ZRLifecycleObserver {

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onCreate() {
        System.out.println("MyView onCreate");
    }

    @Override
    public void onStart() {
        System.out.println("MyView onStart");
    }

    @Override
    public void onResume() {
        System.out.println("MyView onResume");
    }

    @Override
    public void onPause() {
        System.out.println("MyView onPause");
    }

    @Override
    public void onStop() {
        System.out.println("MyView onStop");
    }

    @Override
    public void onDestroy() {
        System.out.println("MyView onDestroy");
    }

    @Override
    public void onRestart() {
        System.out.println("MyView onRestart");
    }
}
