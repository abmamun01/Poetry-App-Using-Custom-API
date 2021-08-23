package com.mamunsproject.poetryapp_usingcustomapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mamunsproject.poetryapp_usingcustomapi.Adapter.PoetryAdapter;
import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiClient;
import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiInterface;
import com.mamunsproject.poetryapp_usingcustomapi.Model.PoetryModel;
import com.mamunsproject.poetryapp_usingcustomapi.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    List<PoetryModel> poetryModelsList = new ArrayList<>();
    ApiInterface apiInterface;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        initialization();
        clickListener();
        getData();
    }

    private void clickListener() {

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.add_property) {

                    startActivity(new Intent(getApplicationContext(),AddPoetry.class));

                }


                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void initialization() {
        recyclerView = findViewById(R.id.poetry_recyclerview);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);




    }



        private void setAdapter(List<PoetryModel> poetryModelsList) {

        poetryAdapter = new PoetryAdapter(this, poetryModelsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(poetryAdapter);

    }




    private void getData() {


        apiInterface.getPoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {

                try {

                    if (response != null) {
                        assert response.body() != null;
                        if (response.body().getStatus().equals("1")) {

                            Log.d("DKDD", "onResponse: "+response.body().getStatus());
                            setAdapter(response.body().getData());
                            poetryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    poetryAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    Log.e("Failure", e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {

                Log.e("FailureSSS", t.getLocalizedMessage());
            }

        });
    }






}