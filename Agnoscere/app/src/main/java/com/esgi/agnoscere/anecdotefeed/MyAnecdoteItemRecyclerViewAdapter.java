package com.esgi.agnoscere.anecdotefeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esgi.agnoscere.R;
import com.esgi.agnoscere.anecdotefeed.OnListFragmentInteractionListener;
import com.esgi.agnoscere.anecdotefeed.dummy.DummyContent.DummyItem;
import com.esgi.agnoscere.xmlparser.Anecdote;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAnecdoteItemRecyclerViewAdapter extends RecyclerView.Adapter<MyAnecdoteItemRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private final ArrayList<Anecdote> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAnecdoteItemRecyclerViewAdapter(ArrayList<Anecdote> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anecdote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getId());
        //holder.mContentView.setText(mValues.get(position).content);
        holder.mContentView.setText(mValues.get(position).getContents());
        holder.mAuthorView.setText(mValues.get(position).getAutor());
        holder.mDateView.setText(mValues.get(position).getDate());
        holder.mJmcmb.setText(Integer.toString(mValues.get(position).getIdidntknowvote()));
        holder.mJlsd.setText(Integer.toString(mValues.get(position).getIknewvote()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mAuthorView;
        public final TextView mDateView;
        public final TextView mJmcmb;
        public final TextView mJlsd;
        //public DummyItem mItem;
        public Anecdote mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mAuthorView = (TextView) view.findViewById(R.id.author);
            mDateView = (TextView) view.findViewById(R.id.date);
            mJmcmb = (TextView) view.findViewById(R.id.jmcmb);
            mJlsd = (TextView) view.findViewById(R.id.jlsd);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
