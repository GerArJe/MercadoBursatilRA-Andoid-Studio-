package com.gerarje.mercadobursatilra.model;


public class YouTubeVideos {

    String videoUrl;
    String tituloVideo;

    public YouTubeVideos() {
    }

    public YouTubeVideos(String videoUrl, String tituloVideo) {
        this.videoUrl = videoUrl;
        this.tituloVideo=tituloVideo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTituloVideo() {
        return tituloVideo;
    }
}
