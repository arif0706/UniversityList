package com.example.universitylist.Controller;

import android.content.Context;

import com.example.universitylist.Model.Model;

import java.util.List;

public interface IMainActivityController {
    void getPosts();
    void insertPostsToRoom(Context context, List<Model> modelList);

}
