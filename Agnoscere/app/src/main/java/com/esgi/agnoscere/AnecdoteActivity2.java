package com.esgi.agnoscere;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.agnoscere.anecdoteview.CommentAdapter;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.Comment;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.util.List;

public class AnecdoteActivity2 extends AppCompatActivity {

    ListView mListView;
    Anecdote mAnecdote;
    Intent mIntent;
    Document mDocument;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote2);

        mListView = (ListView) findViewById(R.id.listView);
        mIntent = getIntent();
        mAnecdote = (Anecdote) mIntent.getSerializableExtra("anecdote");
        mDocument = (Document) mIntent.getSerializableExtra("document");

        setCommentList();
        setAnecdoteTextView();
        setAnecdoteAuthor();

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

    public void iKnewIt(View view)
    {
        XMLParser.iKnewIt(mDocument, Integer.parseInt(mAnecdote.getId()));
    }

    public void iDidntKnewIt()
    {
        XMLParser.iDidntKnowIt(mDocument, Integer.parseInt(mAnecdote.getId()));
    }
}
