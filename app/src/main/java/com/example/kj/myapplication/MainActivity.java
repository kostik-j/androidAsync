package com.example.kj.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kj.myapplication.core.ApiRequestManager;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventBus;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn_int)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_str)).setOnClickListener(this);
    }

    private ArrayList<Integer> ids = new ArrayList();
    @Override
    public void onClick(View v) {
        ApiRequestManager manager = ((MyApplication) getApplication()).getApiRequestManager();
        if(v.getId() == R.id.btn_int) {
            manager.loadTestInt();
        }

        if(v.getId() == R.id.btn_str) {
            manager.loadTestString();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiRequestManager manager = ((MyApplication) getApplication()).getApiRequestManager();

        ids.add(manager.onLoadTestInt(new Callback<Integer>() {
            @Override
            public void execute(Integer i) {
                Toast.makeText(MainActivity.this, "Integer: " + i.toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        ids.add(manager.onLoadTestString(new Callback<String>() {
            @Override
            public void execute(String i) {
                Toast.makeText(MainActivity.this, "String: " + i, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void onPause() {
        EventBus eventBus = ((MyApplication) getApplication()).getEventBus();
        for (Integer i: ids) {
            eventBus.unsubcribe(i);
        }
        ids.clear();
        super.onPause();
    }
}
