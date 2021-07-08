package com.example.universitylist.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universitylist.Model.Model;
import com.example.universitylist.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

public class UniversityDetails extends AppCompatActivity {

    MaterialToolbar toolbar;

    TextView university_name;
    TextView state_name;
    TextView web_page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_details);

        initWidgets();

        Model model=new Gson().fromJson(getIntent().getStringExtra("university_model"),Model.class);

        university_name.setText(model.getName());
        if(model.getState_province()!=null)
            state_name.setText(model.getState_province());
        else
            state_name.setText("N/A");
        web_page.setText(model.getWeb_pages()[0]);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    void initWidgets(){
        toolbar=findViewById(R.id.toolbar);
        university_name=findViewById(R.id.university_name);
        state_name=findViewById(R.id.state_name);
        web_page=findViewById(R.id.web_page);


    }
}