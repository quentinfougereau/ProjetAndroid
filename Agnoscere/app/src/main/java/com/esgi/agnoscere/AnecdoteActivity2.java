package com.esgi.agnoscere;

import android.content.Intent;

import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.agnoscere.anecdoteview.CommentAdapter;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.Comment;
import com.esgi.agnoscere.xmlparser.XMLParser;



import org.jdom2.Document;

import java.util.List;

public class AnecdoteActivity2 extends AppCompatActivity{

    private ListView mListView;
    private Anecdote mAnecdote;
    private Document mDocument;
    private Button mIKnewItButton;
    private Button miDidntKnowit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote2);

        mListView = (ListView) findViewById(R.id.listView);

        Intent Intent = getIntent();
        mAnecdote = (Anecdote) Intent.getSerializableExtra("anecdote");
        mDocument = (Document) Intent.getSerializableExtra("document");

        mIKnewItButton = (Button) findViewById(R.id.jlsd);
        miDidntKnowit = (Button) findViewById(R.id.jmcmb);

        mIKnewItButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iKnewIt();
            }
        });

        miDidntKnowit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iDidntKnowIt();
            }
        });

        setCommentList();
        setAnecdoteTextView();
        setAnecdoteAuthor();

    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        Anecdote anecdote = (Anecdote) getIntent().getSerializableExtra("anecdote");
        System.out.println(anecdote.getAutor());
        System.out.println(anecdote.getIdidntknowvote());
    }


    private void setAnecdoteAuthor() {
        TextView anecdoteAuthor = (TextView) findViewById(R.id.author_text);
        anecdoteAuthor.setText(mAnecdote.getAutor());

    }

    private void setCommentList() {

        List<Comment> commentaire = mAnecdote.getcomments();
        CommentAdapter adapter = new CommentAdapter(AnecdoteActivity2.this, commentaire);
        mListView.setAdapter(adapter);

    }

    private void setAnecdoteTextView()
    {
        TextView anecdoteText = (TextView) findViewById(R.id.anecdote_text);
        anecdoteText.setText(mAnecdote.getContent());
    }


    public void iKnewIt()
    {
        XMLParser.iKnewIt(mDocument, Integer.parseInt(mAnecdote.getId()));
    }

    public void iDidntKnowIt()
    {
        XMLParser.iDidntKnowIt(mDocument, Integer.parseInt(mAnecdote.getId()));
    }
}
