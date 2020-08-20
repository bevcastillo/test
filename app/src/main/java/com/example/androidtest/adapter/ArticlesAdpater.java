package com.example.androidtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.R;
import com.example.androidtest.model.articles.Articles;
import com.example.androidtest.model.articles.Datum;

import java.util.List;

public class ArticlesAdpater extends RecyclerView.Adapter<ArticlesAdpater.ViewHolder> {

    private Context context;
    private List<Articles> listdata;
    private RequestOptions options;


    public ArticlesAdpater(Context context, List<Articles> listdata) {
        this.context = context;
        this.listdata = listdata;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Articles articles = listdata.get(position);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        ImageView ivThumbnail;
        TextView tvTitle, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            ivThumbnail = itemView.findViewById(R.id.iv_article_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
