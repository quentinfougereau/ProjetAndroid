package com.esgi.agnoscere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.agnoscere.anecdotefeed.AnecdoteFeedActivity;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EditAnecdoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anecdote);

        final Anecdote anecdote = (Anecdote) getIntent().getSerializableExtra("anecdote");
        final int anecdote_id = Integer.parseInt(anecdote.getId());

        //Get views
        EditText anecdote_title = (EditText) findViewById(R.id.anecdote_title);
        EditText anecdote_category = (EditText) findViewById(R.id.anecdote_category);
        EditText anecdote_text = (EditText) findViewById(R.id.anecdote_text);
        EditText video_id_text = (EditText) findViewById(R.id.video_id_text);
        EditText image_link_text = (EditText) findViewById(R.id.image_link_text);

        //Fill views
        anecdote_title.setText(anecdote.getTitle());
        anecdote_category.setText(anecdote.getCategory());
        anecdote_text.setText(anecdote.getContents());
        video_id_text.setText(anecdote.getVideoid());
        image_link_text.setText(anecdote.getImagelink());

        Button button = (Button) findViewById(R.id.edit_anecdote_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAnecdote(anecdote_id);
            }
        });

    }

    private void editAnecdote(int id) {
        EditText anecdote_text = (EditText) findViewById(R.id.anecdote_text);


        Document xmlDocument = null;
        try {
            xmlDocument = XMLParser.loadXMLDocument(new FileInputStream(new File(getFilesDir(),"xmlfile.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XMLParser.editAnecdote(this, xmlDocument, id, anecdote_text.getText().toString());

        Intent intent = new Intent(this, AnecdoteFeedActivity.class);
        startActivity(intent);

    }


}
