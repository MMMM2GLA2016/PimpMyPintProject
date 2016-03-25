package fr.istic.m2gla.mmm.pimpmypint;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m2gla.mmm.pimpmypint.firebase.model.Beer;

public class BeerListContentProvider extends ContentProvider {

    public static List<Beer> beerList = new ArrayList<>();

    public static String _ID ="_ID";
    public static String NAME ="nom";
    public static String BREWERY ="brasserie";
    public static String ALCOHOL ="alcool";

    static final String AUTHORITY = "beerlistcontentprovider";

    public static final String PROVIDER_NAME = "beerlistcontentprovider";

    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME);

    private Firebase mFirebaseRef;

    @Override
    public boolean onCreate() {
        /* Create the Firebase ref that is used for all authentication with Firebase */

        Firebase.setAndroidContext(this.getContext());
        mFirebaseRef = new Firebase(this.getContext().getResources().getString(R.string.firebase_url) + "/Beers");

        beerList = new ArrayList<>();

        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("-> " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Beer beer = postSnapshot.getValue(Beer.class);
                    System.out.println("----------> " + beer.getBrasserie() + " " + beer.getNom());
                    beerList.add(beer);
                }

            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
        return true;
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        MatrixCursor c = new MatrixCursor(new String[] {
                _ID,
                NAME,
                BREWERY,
                ALCOHOL
        });

        int row_index = 0;

        List<Beer> tmp = new ArrayList<>();

        if (selection != null) {
            for (int i = 0; i< beerList.size(); i++ ) {
                if (beerList.get(i).getNom().contains(selection) ||
                        beerList.get(i).getBrasserie().contains(selection)) {
                    tmp.add(beerList.get(i));
                }
            }
        } else {
            tmp = beerList;
        }

        // Add x-axis data
        for (int i=0; i< tmp.size(); i++) {
            c.newRow()
                    .add(row_index )
                    .add(tmp.get(row_index).getNom() )
                    .add(tmp.get(row_index).getBrasserie() )   // Only create data for the first series.
                    .add(tmp.get(row_index).getAlcool() );

            row_index++;
        }
        return c;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // create a new MIME type "com.example.books" for the values which a returned
        return ContentResolver.CURSOR_DIR_BASE_TYPE + '/' + "fr.istic.m2gla.mmm.pimpmypint.beer";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
