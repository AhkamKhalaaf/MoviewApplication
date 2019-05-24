package com.example.moviewapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviewapplication.view.DetailsmovieActivity;
import com.example.moviewapplication.R;
import com.example.moviewapplication.databinding.MovieitemlayoutBinding;
import com.example.moviewapplication.models.Apiresponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MoviewSearchAdapter  extends RecyclerView.Adapter<MoviewSearchAdapter.Myviewholder> {
    List<Apiresponse.OneMovie>list;
    Context context;

    public MoviewSearchAdapter(List<Apiresponse.OneMovie> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieitemlayoutBinding movieitemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.movieitemlayout,parent,false);
        return new Myviewholder(movieitemlayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final Apiresponse.OneMovie oneMovie = list.get(position);
         holder.movieitemlayoutBinding.setMydata(oneMovie);
         holder.movieitemlayoutBinding.motherlayoutitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsmovieActivity.class);
   /// send movie data as parcable
                intent.putExtra("data",oneMovie);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        MovieitemlayoutBinding movieitemlayoutBinding ;
        public Myviewholder(MovieitemlayoutBinding movieitemlayoutBinding) {
            super(movieitemlayoutBinding.getRoot());
            this.movieitemlayoutBinding = movieitemlayoutBinding;
        }
    }
}
