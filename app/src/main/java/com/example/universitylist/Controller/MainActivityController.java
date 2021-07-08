package com.example.universitylist.Controller;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.universitylist.Model.Model;
import com.example.universitylist.Api.RetrofitClient;
import com.example.universitylist.Room.AppDatabase;
import com.example.universitylist.View.MainActivityView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityController implements IMainActivityController{

    MainActivityView mainActivityView;

    public MainActivityController(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void getPosts() {
        Call<List<Model>> call= RetrofitClient.getInstance().getMyApi().getUniversityList();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Model>> call, @NonNull Response<List<Model>> response) {
                List<Model> list=response.body();
                mainActivityView.onGettingPosts(list);
            }
            @Override
            public void onFailure(@NonNull Call<List<Model>> call, @NonNull Throwable t) {

            }
        });
    }
    @Override
    public void insertPostsToRoom(Context context, List<Model> modelList) {
        AppDatabase appDatabase=AppDatabase.getInstance(context);
        for (Model model:
             modelList) {
            appDatabase.databaseDao().InsertUniversity(model);
        };
    }
}