package com.esgi.agnoscere;

import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;


import com.esgi.agnoscere.anecdoteview.CommentAdapter;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.Comment;
import com.esgi.agnoscere.xmlparser.XMLParser;


import org.jdom2.Document;

import java.io.InputStream;
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

        Intent intent = getIntent();
        mAnecdote = (Anecdote) intent.getSerializableExtra("anecdote");

        setCommentList();
        setAnecdoteTextView();
        setAnecdoteAuthor();
        setAnecdoteIknewit();
        setmAnecdoteIDidntKnowit();
        setAnecdoteTitle();

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

    private void setAnecdoteIknewit()
    {
        TextView anecdoteText = (TextView) findViewById(R.id.textView_jlsd);
        anecdoteText.setText(String.valueOf(mAnecdote.getIknewvote()));
    }

    private void setmAnecdoteIDidntKnowit()
    {
        TextView anecdoteText = (TextView) findViewById(R.id.textView_jmcmb);
        anecdoteText.setText(String.valueOf(mAnecdote.getIdidntknowvote()));
    }

    private void setAnecdoteTitle()
    {
        TextView anecdoteText = (TextView) findViewById(R.id.anecdote_title);
        anecdoteText.setText(String.valueOf(mAnecdote.getTitle()));
    }


    public void iKnewIt()
    {
        XMLParser.iKnewIt(getBaseContext(),mDocument, Integer.parseInt(mAnecdote.getId()));
    }

    public void iDidntKnowIt()
    {
        XMLParser.iDidntKnowIt(getBaseContext(),mDocument, Integer.parseInt(mAnecdote.getId()));
    }


    // AsyckTask to download image (url given )
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        //constructor
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        // laoding picture and put it into bitmap
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        //after downloading
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }




}
