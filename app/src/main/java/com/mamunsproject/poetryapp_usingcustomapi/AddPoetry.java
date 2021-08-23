package com.mamunsproject.poetryapp_usingcustomapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiClient;
import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiInterface;
import com.mamunsproject.poetryapp_usingcustomapi.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetName, poetryData;
    Button submitBtn;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        init();
        clickListener();


    }

    private void clickListener() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = poetryData.getText().toString();
                String poetryNameString = poetName.getText().toString();

                if (poetryDataString.trim().equals("")) {

                    poetryData.setError("Field is Empty!");


                } else {
                    if (poetryNameString.trim().equals("")) {

                        poetName.setError("Field is Empty!");

                    } else {
                        callApi(poetryDataString,poetryNameString);
                    }
                }
            }
        });
    }


    private void init() {
        toolbar = findViewById(R.id.add_poetry_toolbar);
        poetName = findViewById(R.id.add_poet_name_editext);
        poetryData = findViewById(R.id.add_poetry_data_editext);
        submitBtn = findViewById(R.id.submit_data_btn);

        setSupportActionBar(toolbar);

        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
    }


    private void callApi(String poetryData,String poetName){

        apiInterface.addPoetry( poetryData, poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {

                    if (response.body().getStatus().equals("1")){

                        Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(getApplicationContext(), "Failed To Add!", Toast.LENGTH_SHORT).show();

                    }
                    Log.e("DDD", "Resposne"+response.body().getStatus() );
                }catch (Exception e){
                    Log.e("TAGDDS", "onFailure: "+ e.getLocalizedMessage());

                }


            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {


                Log.e("TAGD", "onFailure: "+ t.getLocalizedMessage());
            }
        });

    }
}