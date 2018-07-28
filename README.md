## 如何绑定页面生命周期（一）－基于Android Architecture Components的Lifecycle实现

>Lifecycle是Android Architecture Components(之后简称AAC)的一个组件，用于将系统组件（Activity、Fragment等等）的生命周期分离到Lifecycle类，Lifecycle允许其他类作为观察者，观察组件生命周期的变化。

### 基于AAC实现组件生命周期观察实践
+ 控件实现LifecycleObserver接口，内部通过@OnLifecycleEvent注解声明生命周期事件

```
public class LifecycleObserverDemo implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        System.out.println("onAny:" + event.name());
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        System.out.println("onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        System.out.println("onDestroy");
    }
}
```

+ 在LifecycleRegistryOwner，比如在实现了LifecycleRegistryOwner接口的Activity中。定义LifecycleRegistry实例，并将控件lifecycleRegistry实例中的监听集合中。


```
public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner {
	// 定义LifecycleRegistry实例
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 加入监听集合
        getLifecycle().addObserver(new LifecycleObserverDemo());        
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
```

只需要如上两步，当Activity页面生命周期发生变化时，都会通知到LifecycleObserverDemo。

### 生命周期绑定实现原理

#### 实现原理简介
通过在对指定activity注册无UI的Fragment，实现页面生命周期监听。然后通过Fragment绑定LifecycleRegistry，传递生命周期，回调LifecycleRegistry中LifecycleObserver对象相应的生命周期回调方法。

#### 如何绑定生命周期

#### 如何传递生命周期

>![生命周期传递到LifecycleObserver](https://upload-images.jianshu.io/upload_images/64766-e57813cb987f6d6c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

+ _LifecycleAdapter如何与LifecycleRegistry建立联系
	- ObserverWithState对象构造函数初始化时，通过Lifecycling.getCallback(observer)方法返回_LifecycleAdapter对象

+ _LifecycleAdapter如何生成
	- 基于注解生成class，通过反射生成_LifecycleAdapter对象

+ LifecycleDispatcher初始化
	- 利用 ContentProvider 的特点在应用程序初始化时，向其注入两行代码：

	```
	LifecycleDispatcher.init(getContext());
	ProcessLifecycleOwner.init(getContext());  // 监听整个应用前后台切换
	```

	- 这个ContentProvider从哪里来？查看apk中的AndroidManifest.xml文件，发现多了一个ContentProvider声明：
	
	```
	<provider
        android:name="android.arch.lifecycle.LifecycleRuntimeTrojanProvider"
        android:authorities="${applicationId}.lifecycle-trojan"
        android:exported="false"
        android:multiprocess="true" />
	```
在这个ContentProvider的初始化方法中，实现了LifecycleDispatcher的相应初始化操作。

**与glide对比，基于反射，性能较低；解耦，逻辑更加清晰**

### 核心类介绍
+ LifecycleObserver：接口，标记一个类是可观察的，基于注解实现相应回调方法
+ Lifecycle：抽象类，拥有android生命周期
+ LifecycleRegistry：继承Lifecycle，可以处理多LifecycleObserver
+ LifecycleOwner：接口，持有一个android lifecycle
+ LifecycleRegistryOwner：接口，继承LifecycleOwner，返回LifecycleRegistry
+ LifecycleDispatcher：在Application中hook，观察activity的生命周期并分发
+ LifecycleRuntimeTrojanProvider：LifecycleDispatcher等初始化


### 生命周期管理框架实践

Demo省略了注解相关步骤，需要观察者自己去实现一个ZRLifecycleObserver接口。虽然稍有不同，但是不妨碍理解。

Demo的框架图如下所示：

![框架图](https://note.youdao.com/yws/public/resource/d77305433fb473583ca3af3cbe7f4b27/xmlnote/WEBRESOURCE7961c0f003847bdbdc62b7de561ef3f6/15453)

使用的话也比较简单，主要进行以下一些设置。

+  观察者实现ZRLifecycleObserver接口

```
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
```

+  应用启动时初始化ZRLifecycleDispatcher

```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZRLifecycleDispatcher.init(this);
    }
}
```

+  被观察页面实现ZRLifecycleRegistryOwner，并将要要观察此页面生命周期的观察者对象加入集合

```
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
```

具体工程代码可以从这里获取：[CustomAACLifecycleDemo](https://github.com/yushiwo/CustomAACLifecycleDemo)

### 结束
至此，关于AAC如何绑定页面生命周期的原理讲解结束。在上一篇文章，介绍了Glide绑定生命周期的原理。两种绑定页面生命周期的方式，大家可以对比着看，相信肯定会对绑定页面生命周期有更加深入的了解。


### 参考
[Android Architecture Component -- Lifecycle 浅析](https://www.jianshu.com/p/bd800c5dae30)
