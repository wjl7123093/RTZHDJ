package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyTaskContract;
import com.mytv.rtzhdj.mvp.model.MyTaskModel;


@Module
public class MyTaskModule {
    private MyTaskContract.View view;

    /**
     * 构建MyTaskModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyTaskModule(MyTaskContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyTaskContract.View provideMyTaskView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyTaskContract.Model provideMyTaskModel(MyTaskModel model) {
        return model;
    }
}