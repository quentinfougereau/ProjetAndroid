package com.esgi.agnoscere.anecdotefeed;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.esgi.agnoscere.AnecdoteActivity2;
import com.esgi.agnoscere.CreateAnecdoteActivity;
import com.esgi.agnoscere.R;
import com.esgi.agnoscere.anecdotefeed.dummy.DummyContent;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class AnecdoteFeedActivity extends AppCompatActivity implements AnecdoteFeedFragment.OnFragmentInteractionListener, OnListFragmentInteractionListener {


    public int nb_anecdote = 0;
    private Document xmlDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("LOG : onCreate");
        setContentView(R.layout.activity_anecdote_feed);
        copyByte();
        AnecdoteItemFragment anecdoteItemFragment = new AnecdoteItemFragment();
        nb_anecdote = getAnecdotes().size();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, anecdoteItemFragment);
        fragmentTransaction.commit();


    }
        public void copyByte() {
            File f= new File(getFilesDir(),"xmlfile.xml");
            if(!f.exists()){
                BufferedReader in = null;
                BufferedWriter out = null;

            try {
                Log.i("in", "copyByte: in");
                in =new BufferedReader(new InputStreamReader(getAssets().open("xmlfile.xml")));
                out = new BufferedWriter(new FileWriter(f));

                int octet;
                String line="";
                // La méthode read renvoie -1 dès qu'il n'y a plus rien à lire
                while ((line = in.readLine())!=null) {
                    out.write(line);
                }
               in.close();
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Anecdote mItem) {
        Toast.makeText(this, mItem.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AnecdoteActivity2.class);
        intent.putExtra("document",xmlDocument);
        intent.putExtra("anecdote", mItem);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        /*
        try {
            intent.putExtra("docXml", (Serializable) getAssets().open("xmlfile.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (newAnecdoteHasBeenAdded()) {
            System.out.println("A new anecdote has been added");
            AnecdoteItemFragment anecdoteItemFragment = new AnecdoteItemFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, anecdoteItemFragment);
            fragmentTransaction.commit();
        }
    }

    public boolean newAnecdoteHasBeenAdded() {

        ArrayList<Anecdote> anecdotes = getAnecdotes();
        if (anecdotes.size() > nb_anecdote) {
            nb_anecdote = anecdotes.size();
            return true;
        }
        return false;
    }

    public ArrayList<Anecdote> getAnecdotes() {
        try {
            xmlDocument = XMLParser.loadXMLDocument(new FileInputStream(new File(getFilesDir(),"xmlfile.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Anecdote> anecdoteArray = XMLParser.parseXML(xmlDocument, "");
        return anecdoteArray;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
