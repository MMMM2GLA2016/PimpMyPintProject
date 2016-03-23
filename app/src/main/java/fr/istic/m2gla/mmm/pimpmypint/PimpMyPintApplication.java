package fr.istic.m2gla.mmm.pimpmypint;
import android.app.Application;

import com.firebase.client.Firebase;

public class PimpMyPintApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
    }

    private Firebase mFirebaseRef;

    public Firebase getmFirebaseRef() {
        return mFirebaseRef;
    }

    public void setmFirebaseRef(Firebase mFirebaseRef) {
        this.mFirebaseRef = mFirebaseRef;
    }
}
