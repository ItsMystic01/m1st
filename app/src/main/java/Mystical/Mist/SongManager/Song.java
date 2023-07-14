package Mystical.Mist.SongManager;

import java.util.Arrays;

public class Song {

    private String songName;
    private String songAuthor;
    private String songLyricsAndChords;
    private byte[] songImageCover;
    private int songImageOrientation;

    public Song(String songName, String songAuthor, String songLyricsAndChords, byte[] songImageCover, int songImageOrientation) {
        this.songName = songName;
        this.songAuthor = songAuthor;
        this.songLyricsAndChords = songLyricsAndChords;
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

    public String getSongLyricsAndChords() { return songLyricsAndChords; }

    public void setSongLyricsAndChords(String songLyricsAndChords) { this.songLyricsAndChords = songLyricsAndChords; }

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

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", songAuthor='" + songAuthor + '\'' +
                ", songLyricsAndChords='" + songLyricsAndChords + '\'' +
                ", songImageCover='" + Arrays.toString(songImageCover) + '\'' +
                ", songImageOrientation='" + songImageOrientation +
                '}';
    }
}
