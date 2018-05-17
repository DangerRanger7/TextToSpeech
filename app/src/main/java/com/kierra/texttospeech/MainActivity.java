package com.kierra.texttospeech;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    //variables
    TextToSpeech textToSpeech;
    int result;
    EditText editText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize editText
        editText = findViewById(R.id.text_editText);

        //speak button
        findViewById(R.id.speak_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplicationContext(), "Feature is not supported by your device",
                            Toast.LENGTH_LONG).show();
                }else{
                    text = editText.getText().toString();
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        //stop button
        findViewById(R.id.stop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textToSpeech != null){
                    textToSpeech.stop();
                }

            }
        });

        //textToSpeech section -->> checks if device supports it
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    //sets language
                    result = textToSpeech.setLanguage(Locale.US);
                }else{
                    Toast.makeText(getApplicationContext(), "Feature is not supported by your device",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
