package Mystical.Mist.SongManager;

public class Song {

    private String songName;
    private String songAuthor;
    private String songContent;
    private byte[] songImageCover;
    private int songImageOrientation;
    private String songType;

    public Song(String songName, String songAuthor, String songContent, byte[] songImageCover, int songImageOrientation, String songType) {
        this.songName = songName;
        this.songAuthor = songAuthor;
        this.songContent = songContent;
        this.songImageCover = songImageCover;
        this.songImageOrientation = songImageOrientation;
        this.songType = songType;
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

    public void setSongContent(String songContent) { this.songContent = songContent; }

    public String getSongContent() { return songContent; }

    public byte[] getSongImageCover() {
        return songImageCover;
    }

    public void setSongImageCover(byte[] songImageCover) {
        this.songImageCover = songImageCover;
    }

    public int getSongImageOrientation() {
        return songImageOrientation;
    }

    public void setSongImageOrientation(int songImageOrientation) { this.songImageOrientation = songImageOrientation; }
    public String getSongType() { return songType; }
    public void setSongType(String songType) { this.songType = songType; }
}
