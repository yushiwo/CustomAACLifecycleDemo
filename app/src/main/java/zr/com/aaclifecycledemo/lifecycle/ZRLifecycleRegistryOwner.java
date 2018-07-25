package zr.com.aaclifecycledemo.lifecycle;


/**
 * @author hzzhengrui
 * @date 2018/07/25
 * @description
 */
public interface ZRLifecycleRegistryOwner extends ZRLifecycleOwner {
    @Override
    ZRLifecycleRegistry getLifecycle();
}
