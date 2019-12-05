package com.robotsme.app.location.presenter;

import com.robotsme.app.location.model.BaseModel;
import com.robotsme.app.location.view.IBaseView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> {

    protected WeakReference<V> mReference;
    protected M mModel;

    public BasePresenter(V mView) {
        mReference = new WeakReference<>(mView);
        mModel = createModel();
    }

    public V getView () {
        if (mReference != null) {
            return mReference.get();
        }
        return null;
    }

    public void destroy() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }

    public abstract M createModel();
}
