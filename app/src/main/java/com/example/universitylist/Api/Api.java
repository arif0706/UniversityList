package com.example.universitylist.Api;

import com.example.universitylist.Model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL="http://universities.hipolabs.com/";

    @GET("/search?country=India")
    Call<List<Model>> getUniversityList();
}
