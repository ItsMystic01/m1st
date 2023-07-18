package Mystical.Mist.SongManager;

import java.util.Arrays;

public class Song {

    private String songName;
    private String songAuthor;
    private String songContent;
    private byte[] songImageCover;
    private int songImageOrientation;

    public Song(String songName, String songAuthor, String songContent, byte[] songImageCover, int songImageOrientation) {
        this.songName = songName;
        this.songAuthor = songAuthor;
        this.songContent = songContent;
        this.songImageCover = songImageCover;
        this.songImageOrientation = songImageOrientation;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }

    public String getSongContent() { return songContent; }

    public void setSongContent(String songContent) { this.songContent = songContent; }

    public byte[] getSongImageCover() {
        return songImageCover;
    }

    public void setSongImageCover(byte[] songImageCover) {
        this.songImageCover = songImageCover;
    }

    public int getSongImageOrientation() {
        return songImageOrientation;
    }

    public void setSongImageOrientation(int songImageOrientation) {
        this.songImageOrientation = songImageOrientation;
    }
}
