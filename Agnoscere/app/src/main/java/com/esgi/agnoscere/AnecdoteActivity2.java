package com.esgi.agnoscere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.agnoscere.anecdoteview.CommentAdapter;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.Comment;

import java.util.List;

public class AnecdoteActivity2 extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote2);

        mListView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Anecdote anecdote = (Anecdote) intent.getParcelableExtra("anecdote");

        TextView anecdotetext = (TextView) findViewById(R.id.anecdote_text);
        anecdotetext.setText(anecdote.getContent());

        List<Comment> commentaire = getComment(anecdote);

        CommentAdapter adapter = new CommentAdapter(AnecdoteActivity2.this, commentaire);
        mListView.setAdapter(adapter);
    }

    private List<Comment> getComment(Anecdote anecdote) {

        return anecdote.getcomments();
    }
}
