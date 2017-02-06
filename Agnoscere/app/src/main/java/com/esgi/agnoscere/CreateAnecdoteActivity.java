package com.esgi.agnoscere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CreateAnecdoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private String mAuthor;
    EditText titleEditText;
    EditText categorieEditText;
    EditText anecdoteEditText;
    EditText imageLinkEditText;
    EditText videoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anecdote);

        intent = getIntent();
        mAuthor = intent.getStringExtra("user");

        Button postButton = (Button) findViewById(R.id.post_button);
        titleEditText = (EditText) findViewById(R.id.anecdote_title);
        categorieEditText = (EditText) findViewById(R.id.categorie_editText);
        anecdoteEditText = (EditText) findViewById(R.id.anecdote_text);
        imageLinkEditText = (EditText) findViewById(R.id.image_link_text);
        videoEditText = (EditText) findViewById(R.id.video_id_text);

        postButton.setOnClickListener(this);
        titleEditText.setOnClickListener(this);
        categorieEditText.setOnClickListener(this);
        anecdoteEditText.setOnClickListener(this);
        imageLinkEditText.setOnClickListener(this);
        videoEditText.setOnClickListener(this);

    }

    private void postAnnecdote() {

        Document xmlDocument = null;
        try {
            xmlDocument = XMLParser.loadXMLDocument(new FileInputStream(new File(getFilesDir(),"xmlfile.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        XMLParser.postAnecdote(getBaseContext(),xmlDocument,mAuthor,
                                titleEditText.getText().toString(),
                                categorieEditText.getText().toString(),
                                anecdoteEditText.getText().toString(),
                                videoEditText.getText().toString(),
                                imageLinkEditText.getText().toString(),
                                new ArrayList<String>());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_button:
                postAnnecdote();
                break;
            case R.id.anecdote_title:
                setToNullEditText(titleEditText);
                break;
            case R.id.categorie_editText:
                setToNullEditText(categorieEditText);
                break;
            case R.id.anecdote_text:
                setToNullEditText(anecdoteEditText);
                break;
            case R.id.image_link_text:
                setToNullEditText(imageLinkEditText);
                break;
            case R.id.video_id_text:
                setToNullEditText(videoEditText);
                break;
        }
    }

    public void setToNullEditText(EditText editText)
    {
        editText.setText("");
    }

}
