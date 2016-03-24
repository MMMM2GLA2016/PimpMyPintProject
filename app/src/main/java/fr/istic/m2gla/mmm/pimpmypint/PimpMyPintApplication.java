package fr.istic.m2gla.mmm.pimpmypint;
import android.app.Application;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class PimpMyPintApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));

    }

    private Firebase mFirebaseRef;
    private AuthData authData;

    public Firebase getmFirebaseRef() {
        return mFirebaseRef;
    }

    public void setmFirebaseRef(Firebase mFirebaseRef) {
        this.mFirebaseRef = mFirebaseRef;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }
}
