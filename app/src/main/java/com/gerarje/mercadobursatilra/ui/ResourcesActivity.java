package com.gerarje.mercadobursatilra.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Vector;

import com.gerarje.mercadobursatilra.R;
import com.gerarje.mercadobursatilra.adapter.VideoAdapter;
import com.gerarje.mercadobursatilra.model.YouTubeVideos;

public class ResourcesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        youtubeVideos.add( new YouTubeVideos(
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/0-0HoTJmWmQ\" frameborder=\"0\" Allowfullscreen></iframe>" ,
                "Introducción al mercado Bursátil") );
        youtubeVideos.add( new YouTubeVideos(
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/P5x4AN6vkKc\" frameborder=\"0\" allowfullscreen></iframe>",
                "\nMercado Colombiano"));
        youtubeVideos.add( new YouTubeVideos(
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/z7J8Q8Pv7Zw\" frameborder=\"0\" allowfullscreen></iframe>",
                "\nAnálisis Técnico") );


        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }


    public void goToMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }



}
