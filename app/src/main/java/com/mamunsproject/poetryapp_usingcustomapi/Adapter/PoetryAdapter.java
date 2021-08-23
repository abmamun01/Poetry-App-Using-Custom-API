package com.mamunsproject.poetryapp_usingcustomapi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiClient;
import com.mamunsproject.poetryapp_usingcustomapi.Api.ApiInterface;
import com.mamunsproject.poetryapp_usingcustomapi.Model.PoetryModel;
import com.mamunsproject.poetryapp_usingcustomapi.R;
import com.mamunsproject.poetryapp_usingcustomapi.Response.DeleteResponse;
import com.mamunsproject.poetryapp_usingcustomapi.UpdatePoetry;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryHolder> {

    Context context;
    List<PoetryModel> poetryModels;
    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @NotNull
    @Override
    public PoetryHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_design, parent, false);
        return new PoetryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull PoetryAdapter.PoetryHolder holder, int position) {

        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.date_time.setText(poetryModels.get(position).getDate_time());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePoetry(poetryModels.get(position).getId() + "", position);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }


    public static class PoetryHolder extends RecyclerView.ViewHolder {
        TextView poetName, poetry, date_time;
        Button updateBtn, deleteBtn;


        public PoetryHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            poetName = itemView.findViewById(R.id.textview_poetName);
            poetry = itemView.findViewById(R.id.textview_poetData);
            date_time = itemView.findViewById(R.id.textview_poetDateandTime);
            updateBtn = itemView.findViewById(R.id.update_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

        }
    }

    private void deletePoetry(String id, int position) {
        apiInterface.deletePoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {
                    if (response != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (response.body().getStatus().equals("1")) {
                            poetryModels.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}
