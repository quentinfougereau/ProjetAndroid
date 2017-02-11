package com.esgi.agnoscere.randomanecdotewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.esgi.agnoscere.R;
import com.esgi.agnoscere.xmlparser.Anecdote;
import com.esgi.agnoscere.xmlparser.XMLParser;

import org.jdom2.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class RandomAnecdoteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String text) {

        CharSequence widgetText = text;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_anecdote_widget);

        Intent intent = new Intent(context, RandomAnecdoteWidget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);

        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Anecdote randomAnecdote = getRandomAnecdote(context);

        System.out.println("LOG : Update");

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, randomAnecdote.getContents());
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Anecdote randomAnecdote = getRandomAnecdote(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_anecdote_widget);
        views.setTextViewText(R.id.appwidget_text, randomAnecdote.getContents());

        ComponentName thisWidget = new ComponentName(context, RandomAnecdoteWidget.class);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        appWidgetManager.updateAppWidget(thisWidget, views);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public Anecdote getRandomAnecdote(Context context) {

        Document xmlDocument = null;
        try {
            xmlDocument = XMLParser.loadXMLDocument(new FileInputStream(new File(context.getFilesDir(),"xmlfile.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Anecdote> anecdotes = XMLParser.parseXML(xmlDocument, "");
        Random random = new Random();
        int randomInt = random.nextInt(anecdotes.size());
        return anecdotes.get(randomInt);
    }


}

