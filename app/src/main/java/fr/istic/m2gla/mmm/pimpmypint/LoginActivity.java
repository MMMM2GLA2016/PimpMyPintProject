package fr.istic.m2gla.mmm.pimpmypint;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE_SIGN_UP = 0;
    private ProgressDialog mAuthProgressDialog;
    private Firebase mFirebaseRef;

    private TextView passwordTV;
    private AutoCompleteTextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseRef = ((PimpMyPintApplication) this.getApplication()).getmFirebaseRef();

        emailTV= (AutoCompleteTextView) findViewById(R.id.email);
        passwordTV = (TextView) findViewById(R.id.password);

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();

        Firebase.AuthStateListener mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                mAuthProgressDialog.hide();
                setAuthenticatedUser(authData);
            }
        };

        mFirebaseRef.addAuthStateListener(mAuthStateListener);
    }

    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("email", authData.getProviderData().get("email").toString())
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(intent);
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }

    public void actionSignIn (View view) {
        mAuthProgressDialog.show();
        String email= (emailTV != null ? emailTV.getText().toString() : null);

        String password= (passwordTV != null ? passwordTV.getText().toString() : null);

        if (email != null && password != null)
            mFirebaseRef.authWithPassword(email,password,new AuthResultHandler("password"));
        else
            Log.i(TAG, "Error on credentials");

    }

    public void gotoSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SIGN_UP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE_SIGN_UP == requestCode && RESULT_OK == resultCode){
            passwordTV.setText(data.getExtras().getString("password"));
            emailTV.setText(data.getExtras().getString("email"));

            Button buttonSignIn = (Button) findViewById(R.id.action_sign_in_button);
            if (buttonSignIn != null)
                buttonSignIn.performClick();
        }

    }
}
