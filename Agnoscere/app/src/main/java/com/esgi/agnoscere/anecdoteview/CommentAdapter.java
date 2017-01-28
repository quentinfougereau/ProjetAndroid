package com.esgi.agnoscere.anecdoteview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esgi.agnoscere.R;
import com.esgi.agnoscere.xmlparser.Comment;

import java.util.List;

/**
 * Created by kokoghlanian on 28/01/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> commentList) {
        super(context,0 ,commentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout,parent, false);
        }

        AnecdoteViewHolder viewHolder = (AnecdoteViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new AnecdoteViewHolder();
            viewHolder.author = (TextView) convertView.findViewById(R.id.author);
            viewHolder.text = (TextView) convertView.findViewById(R.id.comment);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Comment comment = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.author.setText(comment.getAutor());
        viewHolder.text.setText(comment.getContent());
        viewHolder.date.setText(comment.getDate());

        return convertView;
    }
}
