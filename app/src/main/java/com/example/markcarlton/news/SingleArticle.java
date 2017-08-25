package com.example.markcarlton.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

public class SingleArticle extends AppCompatActivity {
    private TextView Author, Description;
    private ImageView ImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_article);

        Author = (TextView) findViewById(R.id.authorarticle);
        Description = (TextView) findViewById(R.id.descriptionarticle);
        ImgUrl = (ImageView) findViewById(R.id.imgarticle);

        String itemsasstring = getIntent().getStringExtra("articleslist");
        Gson gson = new Gson();
        Type type = new TypeToken<Article>(){}.getType();
        Article article = gson.fromJson(itemsasstring,type);

        String author = article.getAuthor();
        String desc = article.getDescription();
        String urlimag = article.getUrlToImage();

        Author.setText("By: " + author);
        Description.setText(desc);
        Picasso.with(getApplicationContext()).load(urlimag).into(ImgUrl);
    }
}
