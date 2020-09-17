package com.wljy.mvvmlibrary.base;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.wljy.mvvmlibrary.annotation.Event;
import com.wljy.mvvmlibrary.annotation.Scope;
import com.wljy.mvvmlibrary.bean.LiveBean;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;


/**
 * @author lzh
 */
public class AbsViewModel extends AndroidViewModel {

    public AbsViewModel(@NonNull Application application) {
        super(application);
    }

    protected LifecycleOwner lifecycleOwner;
    private MyHandler myHandler;

    protected <T extends LifecycleOwner> void registerSubscriber(final T lifecycleOwner) {
        myHandler = new MyHandler(this);
        this.lifecycleOwner = lifecycleOwner;
        ((LifecycleOwner) lifecycleOwner).getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    myHandler.removeCallbacksAndMessages(null);
                }
            }
        });
    }

    class MyHandler extends Handler {
        public WeakReference<AbsViewModel> weakReference;

        public MyHandler(AbsViewModel viewModel) {
            weakReference = new WeakReference<>(viewModel);
        }

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            LiveBean liveBean = (LiveBean) msg.obj;
            final Class<? extends AbsViewModel> nowClass = AbsViewModel.this.getClass();
            Method[] methods = weakReference.get().lifecycleOwner.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Event event = method.getAnnotation(Event.class);
                Scope scope = method.getAnnotation(Scope.class);
                if (scope != null) {
                    Class<? extends AbsViewModel> defClass = scope.value();
                    if (defClass != nowClass) {
                        continue;
                    }
                }
                if (event != null) {
                    String[] keys = event.key();
                    for (String key : keys) {
                        try {
                            if(key.equals(liveBean.getKey())) {
                                method.invoke(weakReference.get().lifecycleOwner, key, liveBean.getData());
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException("error", e);
                        }
                    }
                }
            }
        }
    }

    public void postData(String eventKey, Object value) {
        if(lifecycleOwner.getLifecycle().getCurrentState().ordinal() > Lifecycle.State.DESTROYED.ordinal()) {
            LiveBean liveBean = new LiveBean(eventKey, value);
            Message message = myHandler.obtainMessage();
            message.obj = liveBean;
            myHandler.sendMessage(message);
        }
    }

    public void postData(String eventKey) {
        if(lifecycleOwner.getLifecycle().getCurrentState().ordinal() > Lifecycle.State.DESTROYED.ordinal()) {
            LiveBean liveBean = new LiveBean(eventKey, null);
            Message message = myHandler.obtainMessage();
            message.obj = liveBean;
            myHandler.sendMessage(message);
        }
    }

}
