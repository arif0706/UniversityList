package com.example.universitylist.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universitylist.Adapters.MainActivityAdapter;
import com.example.universitylist.Controller.MainActivityController;
import com.example.universitylist.Model.Model;
import com.example.universitylist.Network.InternetReceiver;
import com.example.universitylist.R;
import com.example.universitylist.Room.AppDatabase;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements MainActivityView, MainActivityAdapter.CardClickListener,InternetReceiver.getConnection {

    RecyclerView recyclerViewData;

    MainActivityController controller;
    MainActivityAdapter adapter;

    LinearProgressIndicator progressIndicator;

    InternetReceiver internetReceiver;

    LinearLayout internet_layout;
    TextView internet_check;
    TextView empty_message;

    boolean isDataRetrieved=false;

    AppDatabase appDatabase;

    boolean internet_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();

        controller=new MainActivityController(this);
        progressIndicator.setVisibility(View.VISIBLE);

        internetReceiver=new InternetReceiver(this);
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver,intentFilter);

        appDatabase=AppDatabase.getInstance(this);


        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));

    }

    void initWidgets(){
        recyclerViewData=findViewById(R.id.recycler_view_data);
        progressIndicator=findViewById(R.id.progress_horizontal);
        internet_layout=findViewById(R.id.internet_layout);
        internet_check=findViewById(R.id.internet_check);

        empty_message=findViewById(R.id.empty_message);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);

       SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               if(adapter!=null)
                    adapter.getFilter().filter(newText);
               return false;
           }
       });
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGettingPosts(List<Model> modelList) {
        Set set = modelList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Model::getName))));
        modelList.clear();
        modelList.addAll(set);
        Collections.sort(modelList,Model.sortByName);

        List<Model> temp=new ArrayList<>();
        for(int i=0;i<20;i++){
            temp.add(modelList.get(i));
        }
        controller.insertPostsToRoom(this,temp);


        setRecyclerViewData(modelList);

    }

    @Override
    public void onCardClick(Model university_model) {
        Intent intent=new Intent(this,UniversityDetails.class);
        intent.putExtra("university_model",new Gson().toJson(university_model));
        startActivity(intent);
    }
    void setRecyclerViewData(List<Model> modelList){

        progressIndicator.setVisibility(View.GONE);

        adapter=new MainActivityAdapter(this,modelList,this);
        recyclerViewData.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    @Override
    public void getNoConnectionValue(String text) {
        progressIndicator.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(findViewById(R.id.main_layout),new AutoTransition());
        TransitionManager.beginDelayedTransition(internet_layout,new AutoTransition());

        internet_layout.setVisibility(View.VISIBLE);
        internet_layout.setBackgroundColor(Color.RED);
        internet_check.setTextColor(Color.WHITE);
        internet_check.setText(text);

        if(!isDataRetrieved){
                List<Model> models=appDatabase.databaseDao().getUniversities();
                if(models.isEmpty()){
                    empty_message.setVisibility(View.VISIBLE);
                }else
                    setRecyclerViewData(models);

        }

        internet_connection=false;

    }

    @Override
    public void getYesConnectionValue(String online, String text) {
        progressIndicator.setVisibility(View.VISIBLE);
        empty_message.setVisibility(View.GONE);
        internet_layout.setBackgroundColor(Color.parseColor("#4b8b3b"));
        internet_check.setTextColor(Color.WHITE);
        internet_check.setText(online);

        Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time=1500;
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(internet_layout,new Slide(Gravity.BOTTOM));
                        internet_layout.setVisibility(View.GONE);
                    }
                });

            }
        }).start();


        isDataRetrieved=true;
        controller.getPosts();

        internet_connection=true;
    }
}