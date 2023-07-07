package Mystical.Mist.SongManager;

public class Song {

    private String songName;
    private String songAuthor;

    public Song(String songName, String songAuthor) {
        this.songName = songName;
        this.songAuthor = songAuthor;
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

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", songAuthor='" + songAuthor + '\'' +
                '}';
    }
}
