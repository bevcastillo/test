package com.example.androidtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.R;
import com.example.androidtest.model.articles.Articles;
import com.example.androidtest.model.articles.Datum;
import com.example.androidtest.views.ArticleDetailsActivity;

import java.util.List;

public class ArticlesAdpater extends RecyclerView.Adapter<ArticlesAdpater.ViewHolder> {

    private Context context;
    private List<Datum> listdata;
    private RequestOptions options;


    public ArticlesAdpater(Context context, List<Datum> listdata) {
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

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listdata.get(viewHolder.getAdapterPosition()).getArticleId();
                String title = listdata.get(viewHolder.getAdapterPosition()).getTitle();
                String content = listdata.get(viewHolder.getAdapterPosition()).getContent();
                String date = listdata.get(viewHolder.getAdapterPosition()).getDateCreated().getDateDb();
                String timestamp = listdata.get(viewHolder.getAdapterPosition()).getDateCreated().getTimestamp();
                String thumbnail = listdata.get(viewHolder.getAdapterPosition()).getThumbnail().getThumbPath();
                String fullImage = listdata.get(viewHolder.getAdapterPosition()).getThumbnail().getFullPath();

                //passing data to ViewArticle activity
                Intent intent = new Intent(v.getContext(), ArticleDetailsActivity.class);
                intent.putExtra("article_id", id);
                intent.putExtra("article_title", title);
                intent.putExtra("article_content", content);
                intent.putExtra("article_date", date);
                intent.putExtra("article_tStamp", timestamp);
                intent.putExtra("article_thumbnail", thumbnail);
                intent.putExtra("article_fullImage", fullImage);


                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum datum = listdata.get(position);

        holder.tvTitle.setText(datum.getTitle());
        holder.tvDate.setText(datum.getDateCreated().getDateDb());

        Glide.with(context).load(datum.getThumbnail().getThumbPath()).apply(options).into(holder.ivThumbnail);


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
