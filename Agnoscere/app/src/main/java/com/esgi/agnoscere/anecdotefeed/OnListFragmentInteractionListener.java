package com.esgi.agnoscere.anecdotefeed;

import com.esgi.agnoscere.anecdotefeed.dummy.DummyContent;
import com.esgi.agnoscere.xmlparser.Anecdote;

/**
 * Created by quentin on 28/01/17.
 */

public interface OnListFragmentInteractionListener {
    //void onListFragmentInteraction(DummyContent.DummyItem mItem);
    void onListFragmentInteraction(Anecdote mItem);
}
