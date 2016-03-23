package fr.istic.m2gla.mmm.pimpmypint;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class TestActivity extends AppCompatActivity {

    Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mFirebaseRef = ((PimpMyPintApplication) this.getApplication()).getmFirebaseRef();

        TextView textView = (TextView) findViewById(R.id.test_id);
        if (textView != null) {
            textView.setText(getIntent().getExtras().getString("email"));
        }
    }
}
