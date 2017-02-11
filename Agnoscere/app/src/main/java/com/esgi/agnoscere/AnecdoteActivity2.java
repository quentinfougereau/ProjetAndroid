package com.esgi.agnoscere;

import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.esgi.agnoscere.anecdoteview.CommentAdapter;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.Comment;
import com.esgi.agnoscere.xmlparser.XMLParser;
import com.google.android.gms.plus.PlusShare;


import org.jdom2.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AnecdoteActivity2 extends AppCompatActivity implements View.OnClickListener{

    private ListView mListView;
    private Anecdote mAnecdote;
    private Document mDocument;
    private EditText mEditText;
    private String mAuthor;
    private Button youtubeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote2);

        mListView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        mAnecdote = (Anecdote) intent.getSerializableExtra("anecdote");
        mDocument = (Document) intent.getSerializableExtra("document");
        mAuthor = intent.getStringExtra("user");

        Button iKnewItButton = (Button) findViewById(R.id.jlsd);
        Button iDidntKnowit = (Button) findViewById(R.id.jmcmb);
        Button sendComment = (Button) findViewById(R.id.send_comment_button);
        Button shareButton = (Button) findViewById(R.id.share_button);
        youtubeButton = (Button) findViewById(R.id.youtube_button);

        mEditText = (EditText) findViewById(R.id.comment_EditText);

        iKnewItButton.setOnClickListener(this);
        iDidntKnowit.setOnClickListener(this);
        mEditText.setOnClickListener(this);
        sendComment.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        youtubeButton.setOnClickListener(this);

        if(mAnecdote.getImagelink().length() == 0) {
            ImageView imageView = (ImageView) findViewById(R.id.image_view);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.height = 0;
            imageView.setLayoutParams(params);
        }

        setCommentList();
        setAnecdoteTextView();
        setAnecdoteAuthor();
        setAnecdoteIknewit();
        setmAnecdoteIDidntKnowit();
        setAnecdoteTitle();
        updateUI();

        new DownloadImageTask((ImageView) findViewById(R.id.image_view))
                .execute(mAnecdote.getImagelink());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jlsd:
                iKnewIt();
                break;
            case R.id.jmcmb:
                iDidntKnowIt();
                break;
            case R.id.comment_EditText:
                mEditText.setText("");
                break;
            case R.id.send_comment_button:
                writeComment();
                break;
            case R.id.share_button:
                googlePlusShare();
                break;
            case R.id.youtube_button:
                launchYoutube();
        }

    }

    private void launchYoutube() {
        Intent intent = new Intent(this,YoutubeActivity.class);
        intent.putExtra("youtube",mAnecdote.getVideoid());
        startActivity(intent);
    }

    private void googlePlusShare()
    {
        // Launch the Google+ share dialog with attribution to your app.
        Intent shareIntent = new PlusShare.Builder(this)
                .setType("text/plain")
                .setText(mAnecdote.getContent())
                .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                .getIntent();

        startActivityForResult(shareIntent, 0);
    }

    private void writeComment() {
        XMLParser.postComment(getBaseContext(),mDocument,Integer.parseInt(mAnecdote.getId()),mAuthor,mEditText.getText().toString());
        updateAnecdote();
    }

    public void updateAnecdote() {
        try {
            mDocument = XMLParser.loadXMLDocument(new FileInputStream(new File(getFilesDir(),"xmlfile.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Anecdote> anecdoteArray = XMLParser.parseXML(mDocument, "");
        mAnecdote = anecdoteArray.get(Integer.parseInt(mAnecdote.getId())-1);
        setCommentList();
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



    private void updateUI() {
        if (mAnecdote.getVideoid().length() == 0) {
            youtubeButton.setVisibility(View.GONE);
        } else {
            youtubeButton.setVisibility(View.VISIBLE);
        }
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
                Log.i("ERROR",urldisplay);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, CreateAnecdoteActivity.class);
                intent.putExtra("user",getIntent().getStringExtra("user"));
                startActivity(intent);
                return true;
            case R.id.menu_edit_anecdote:
                String user = getIntent().getStringExtra("user");
                if (!user.equals("Anonymous")) {
                    Intent intent_2 = new Intent(this, EditAnecdoteActivity.class);
                    Anecdote anecdote = (Anecdote) getIntent().getSerializableExtra("anecdote");
                    intent_2.putExtra("anecdote", anecdote);
                    startActivity(intent_2);
                } else {
                    Toast.makeText(this, "Vous ne pouver pas éditer en Anonymous", Toast.LENGTH_LONG).show();
                }
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
