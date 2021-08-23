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

public class UpdatePoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetryDat;
    Button button;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        initialization();
        setupToolbar();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p_data = poetryDat.getText().toString();

                if (p_data.equals("")) {
                    poetryDat.setError("Field is empty!");
                } else {

                    callApi(p_data,poetryId+"");
                }

            }
        });

    }

    private void initialization() {

        toolbar = findViewById(R.id.update_poetry_toolbar);
        poetryDat = findViewById(R.id.update_poetry_data_editext);
        button = findViewById(R.id.submit_data_btn);

        poetryId = getIntent().getIntExtra("p_id", 0);
        poetryDataString = getIntent().getStringExtra("p_data");

        poetryDat.setText(poetryDataString);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callApi(String poetryData,String id){

        apiInterface.updatePoetry(poetryData,id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {

                    if (response.body().getStatus().equals("1")){

                        Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Data Update Failed!", Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e){
                    Log.e("TAGD", "Failed"+e.getLocalizedMessage() );

                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

                Log.e("TAGD", "Failed"+t.getLocalizedMessage() );
            }
        });

    }
}