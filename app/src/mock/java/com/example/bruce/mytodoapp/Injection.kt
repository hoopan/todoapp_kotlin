package com.example.bruce.mytodoapp

import android.content.Context
import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.FakeTasksRemoteDataSource
import com.example.bruce.mytodoapp.data.source.TasksRepository
import com.example.bruce.mytodoapp.data.source.local.TasksLocalDataSource

/**
 * Created by bruce on 17-12-15.
 */
public object Injection {

    fun provideTasksRepository(@NonNull context: Context): TasksRepository {
        checkNotNull(context)
        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(), TasksLocalDataSource.getInstance(context))
    }
//    public static TasksRepository provideTasksRepository(@NonNull Context context) {
//        checkNotNull(context);
//        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
//                TasksLocalDataSource.getInstance(context));
//    }
}