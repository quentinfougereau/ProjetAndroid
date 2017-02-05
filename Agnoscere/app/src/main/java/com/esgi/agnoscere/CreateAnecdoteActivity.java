package com.esgi.agnoscere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.IOException;
import java.util.ArrayList;

public class CreateAnecdoteActivity extends AppCompatActivity {

    private Intent intent;
    private String mAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anecdote);


        mAuthor = intent.getStringExtra("user");

        Button postButton = (Button) findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postAnnecdote();
            }
        });
    }

    private void postAnnecdote() {

        Document xmlDocument = null;
        try {
            xmlDocument = XMLParser.loadXMLDocument(this.getAssets().open("xmlfile.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditText titleEditText = (EditText) findViewById(R.id.anecdote_title);
        EditText categorieEditText = (EditText) findViewById(R.id.categorie_editText);
        EditText anecdoteEditText = (EditText) findViewById(R.id.anecdote_text);
        EditText imageLinkEditText = (EditText) findViewById(R.id.image_link_text);
        EditText videoEditText = (EditText) findViewById(R.id.video_id_text);


        XMLParser.postAnecdote(getBaseContext(),xmlDocument,mAuthor,
                                titleEditText.getText().toString(),
                                categorieEditText.getText().toString(),
                                anecdoteEditText.getText().toString(),
                                videoEditText.getText().toString(),
                                imageLinkEditText.getText().toString(),
                                new ArrayList<String>());
    }

}
