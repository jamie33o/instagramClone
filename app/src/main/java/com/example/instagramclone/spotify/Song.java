package com.example.instagramclone.spotify;

public class Song {
    private String name;
    private String artistName;

    private String albumName;
    private String image;

    public Song(String name, String artistName, String albumName, String images) {
        this.name = name;
        this.artistName = artistName;
        this.albumName = albumName;
        this.image = images;
    }

    public String getName() {
        return name;
    }
    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }
    public String getImage(){return image;}
}
