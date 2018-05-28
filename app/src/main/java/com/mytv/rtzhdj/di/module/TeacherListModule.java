package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.mvp.contract.TeacherListContract;
import com.mytv.rtzhdj.mvp.model.TeacherListModel;

import dagger.Module;
import dagger.Provides;


@Module
public class TeacherListModule {
    private TeacherListContract.View view;

    /**
     * 构建TeacherListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TeacherListModule(TeacherListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TeacherListContract.View provideTeacherListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TeacherListContract.Model provideTeacherListModel(TeacherListModel model) {
        return model;
    }
}