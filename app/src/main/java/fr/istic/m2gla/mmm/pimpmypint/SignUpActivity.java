package fr.istic.m2gla.mmm.pimpmypint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Firebase mFirebaseRef;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseRef = ((PimpMyPintApplication) this.getApplication()).getmFirebaseRef();
    }

    public void actionSignUp(View view) {

        AutoCompleteTextView emailTV = (AutoCompleteTextView) findViewById(R.id.email_sign_up);
        email = (emailTV != null ? emailTV.getText().toString() : null);

        TextView passwordTV = (TextView) findViewById(R.id.password_sign_up);
        password = (passwordTV != null ? passwordTV.getText().toString() : null);
        if (email != null && password != null)
            mFirebaseRef.createUser(email, password, new ValueResultHandler());

    }

    private class ValueResultHandler implements Firebase.ValueResultHandler<Map<String, Object>> {

        @Override
        public void onSuccess(Map<String, Object> result) {
            System.out.println("Successfully created user account with uid: " + result.get("uid"));

            Intent returnIntent = new Intent();

            returnIntent.putExtra("email", email);
            returnIntent.putExtra("password", password);

            setResult(RESULT_OK, returnIntent);
            finish();

        }
        @Override
        public void onError(FirebaseError firebaseError) {
            // there was an error
        }
    }
}
