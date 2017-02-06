package com.esgi.agnoscere.anecdotefeed;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.esgi.agnoscere.AnecdoteActivity2;
import com.esgi.agnoscere.R;
import com.esgi.agnoscere.anecdotefeed.dummy.DummyContent;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.IOException;
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

        AnecdoteItemFragment anecdoteItemFragment = new AnecdoteItemFragment();
        nb_anecdote = getAnecdotes().size();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, anecdoteItemFragment);
        fragmentTransaction.commit();


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
        System.out.println("LOG : onResume");
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
            xmlDocument = XMLParser.loadXMLDocument(getAssets().open("xmlfile.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Anecdote> anecdoteArray = XMLParser.parseXML(xmlDocument, "");
        return anecdoteArray;
    }
}
