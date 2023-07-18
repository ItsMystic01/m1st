package Mystical.Mist.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import Mystical.Mist.Functionalities.IntentsFunctionality;
import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class AddSongActivity extends AppCompatActivity {

    Button pickImageButton, addSongButton;
    private EditText songName, songAuthor, songLyricsAndChords;
    private TextView pickImageTextName;
    private final int REQUEST_CODE = 1000;
    private byte[] imageBytes;
    private int orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_song);

        songName = findViewById(R.id.songName);
        songAuthor = findViewById(R.id.songAuthor);
        songLyricsAndChords = findViewById(R.id.lyricsAndChords);
        pickImageButton = findViewById(R.id.addImageButton);
        pickImageTextName = findViewById(R.id.image_picked);
        addSongButton = findViewById(R.id.addSongButton);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        pickImageButton.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityIfNeeded(galleryIntent, REQUEST_CODE);
        });

        addSongButton.setOnClickListener(view -> {

            if(songName.getText().toString().trim().isEmpty() || songAuthor.getText().toString().trim().isEmpty() ||
                    songLyricsAndChords.getText().toString().trim().isEmpty() || imageBytes.length == 0) {
                Toast.makeText(this, "Fill all empty fields", Toast.LENGTH_LONG).show();
                return;
            }

            try (SQLiteManager sqLiteManager = new SQLiteManager(AddSongActivity.this)) {
                sqLiteManager.addSongToSongList("lyrics_and_chords", songName.getText().toString(), songAuthor.getText().toString(), songLyricsAndChords.getText().toString(), imageBytes, orientation);
            }
            songName.getText().clear();
            songAuthor.getText().clear();
            songLyricsAndChords.getText().clear();
            pickImageTextName.setText("");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            String imageName = getFileNameFromUri(data.getData());
            pickImageTextName.setText(imageName);
            try {
                Bitmap imageInBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageInBitmap, 1080, 1080, true);

                ExifInterface exifInterface = new ExifInterface(getPathFromUri(data.getData()));
                orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                );

                IntentsFunctionality intentsFunctionality = new IntentsFunctionality();
                Bitmap rotatedBitmap = intentsFunctionality.rotateBitmapByOrientation(resizedBitmap, orientation);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                imageBytes = outputStream.toByteArray();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex);
                }
                cursor.close();
            }
        } else if (uri.getScheme().equals("file")) {
            fileName = new File(uri.getPath()).getName();
        }
        return fileName;
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return null;
    }

}
