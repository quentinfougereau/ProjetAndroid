package com.esgi.agnoscere.anecdotefeed;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.esgi.agnoscere.R;
import com.esgi.agnoscere.anecdotefeed.dummy.DummyContent;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.util.ArrayList;

public class AnecdoteFeedActivity extends AppCompatActivity implements AnecdoteFeedFragment.OnFragmentInteractionListener, OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote_feed);

        AnecdoteItemFragment anecdoteItemFragment = new AnecdoteItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, anecdoteItemFragment);
        fragmentTransaction.commit();


        /*
        AnecdoteFeedFragment anecdoteFeedFragment = new AnecdoteFeedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, anecdoteFeedFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        */

        /*
        Document xmlDocument = XMLParser
                .loadXMLDocument("xmlfolder/xmlfile.xml");

        ArrayList<Anecdote> anecdoteArray = XMLParser.parseXML(xmlDocument, "");

        for (Anecdote anecdote : anecdoteArray) {
            System.out.println(anecdote.getContents());
        }
        */

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Anecdote mItem) {
        Toast.makeText(this, mItem.getId(), Toast.LENGTH_SHORT).show();
    }
}
