package com.esgi.agnoscere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    // à changer c'était juste pour mes tests
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                onLaunchTestCommentActivity();
            }
        });


    }

    private void onLaunchTestCommentActivity() {

        Document xmlDocument = null;
        try {
            xmlDocument = XMLParser.loadXMLDocument(getAssets().open("xmlfile.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Anecdote> annecdote = XMLParser.parseXML(xmlDocument, "");
        Intent intent =  new Intent(this, AnecdoteActivity2.class);

        intent.putExtra("document",xmlDocument);
        intent.putExtra("anecdote", annecdote.get(0));
        startActivity(intent);
    }
}
