package Mystical.Mist.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import Mystical.Mist.Functionalities.Field;
import Mystical.Mist.Functionalities.IntentsFunctionality;
import Mystical.Mist.R;
import Mystical.Mist.SQLiteManager.SQLiteManager;

public class ModifySongActivity extends AppCompatActivity {

    Button pickImageButton, updateSongButton, lyricsButton, lyricsAndChordsButton, chordsButton;
    MaterialButtonToggleGroup toggleButton;
    TextInputLayout songNameTextInputLayout, songAuthorTextInputLayout, songContentsTextInputLayout;
    private EditText songName, songAuthor, songLyricsAndChords;
    private TextView pickImageTextName;
    private final int REQUEST_CODE = 1000;
    private byte[] imageBytes;
    private int orientation;
    private String type = "lyrics_and_chords";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_song);

        songName = findViewById(R.id.txtSongName);
        songAuthor = findViewById(R.id.txtSongAuthor);
        songLyricsAndChords = findViewById(R.id.txtLyricsAndChords);
        pickImageButton = findViewById(R.id.addImageButton);
        pickImageTextName = findViewById(R.id.image_picked);
        updateSongButton = findViewById(R.id.updateSong);
        songNameTextInputLayout = findViewById(R.id.songNameTextField);
        songAuthorTextInputLayout = findViewById(R.id.songAuthorTextField);
        songContentsTextInputLayout = findViewById(R.id.songContentsTextInputLayout);

        songName.setOnFocusChangeListener((view, hasFocus) -> setTextFieldsAlgorithm(view, hasFocus, songName, songNameTextInputLayout));
        songAuthor.setOnFocusChangeListener((view, hasFocus) -> setTextFieldsAlgorithm(view, hasFocus, songAuthor, songAuthorTextInputLayout));
        songLyricsAndChords.setOnFocusChangeListener((view, hasFocus) -> setTextFieldsAlgorithm(view, hasFocus, songLyricsAndChords, songContentsTextInputLayout));

        toggleButton.addOnButtonCheckedListener(((group, checkedId, isChecked) -> {
            if (checkedId == (lyricsButton.getId())) {
                updateType("lyrics", "Lyrics");
            } else if (checkedId == lyricsAndChordsButton.getId()) {
                updateType("lyrics_and_chords", "Lyrics & Chords");
            } else if (checkedId == chordsButton.getId()) {
                updateType("chords", "Chords");
            }
        }));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        pickImageButton.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityIfNeeded(galleryIntent, REQUEST_CODE);
        });

        updateSongButton.setOnClickListener(view -> {

            if (Field.checkInputs(songName, songAuthor, songLyricsAndChords) || imageBytes.length == 0) {
                Toast.makeText(this, "Fill all empty fields", Toast.LENGTH_LONG).show();
                return;
            }

            try (SQLiteManager sqLiteManager = new SQLiteManager(ModifySongActivity.this)) {
                sqLiteManager.addSongToSongList(type, Field.getText(songName), Field.getText(songAuthor), Field.getText(songLyricsAndChords), imageBytes, orientation);
            }

            Field.clearTexts(songName, songAuthor, songLyricsAndChords);
            pickImageTextName.setText("");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
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

    public void setTextFieldsAlgorithm(View view, boolean hasFocus, EditText textField, TextInputLayout layout) {
        if (!hasFocus && Field.checkInputs(textField)) {
            setStyleOnNoFocusAndEmpty(view, layout);
            return;
        } else if (!hasFocus) {
            setStyleOnNoFocus(view, layout);
            return;
        }
        setStyleOnFocus(view, layout);
    }

    public void setStyleOnNoFocusAndEmpty(View view, TextInputLayout layout) {
        layout.setBoxBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.cream));
        layout.setError("Fill out this field!");
        layout.setStartIconTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.battleship_gray)));
    }

    public void setStyleOnNoFocus(View view, TextInputLayout layout) {
        layout.setStartIconTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.battleship_gray)));
    }

    public void setStyleOnFocus(View view, TextInputLayout layout) {
        layout.setBoxBackgroundColor(Color.TRANSPARENT);
        layout.setStartIconTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.white)));
        layout.setError(null);
    }

    public void updateType(String type, String layoutType) {
        this.type = type;
        songContentsTextInputLayout.setHint(layoutType);
    }

}
