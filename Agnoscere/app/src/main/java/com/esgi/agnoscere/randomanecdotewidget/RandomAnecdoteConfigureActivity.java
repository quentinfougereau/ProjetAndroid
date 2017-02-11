package com.esgi.agnoscere.randomanecdotewidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esgi.agnoscere.R;

public class RandomAnecdoteConfigureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_anecdote_configure);

        Button button = (Button) findViewById(R.id.refresh_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
