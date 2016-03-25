package fr.istic.m2gla.mmm.pimpmypint;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.istic.m2gla.mmm.pimpmypint.firebase.model.Beer;

public class SearchBeerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    /* A reference to the Firebase */
    private Firebase mFirebaseRef;

    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_beer);

        loadList();

        searchView = (SearchView) findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Uri allContacts = BeerListContentProvider.CONTENT_URI;
                Cursor c = getContentResolver().query(allContacts, null, query, null, null);

                BeerCursorAdapter adapter3 = new BeerCursorAdapter(getApplicationContext(), c, 0);

                listView.setAdapter(adapter3);

                //Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Uri allContacts = BeerListContentProvider.CONTENT_URI;
                Cursor c = getContentResolver().query(allContacts, null, newText, null, null);

                BeerCursorAdapter adapter3 = new BeerCursorAdapter(getApplicationContext(), c, 0);

                listView.setAdapter(adapter3);

                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    public void loadList(){
        listView = (ListView) findViewById(R.id.listView);

        Uri allBeers = BeerListContentProvider.CONTENT_URI;

        String filter = null;
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            filter = intent.getStringExtra(SearchManager.QUERY);
        }
        Cursor c = getContentResolver().query(allBeers, null, filter, null, null);

        BeerCursorAdapter adapter3 = new BeerCursorAdapter(this, c, 0);

        listView.setAdapter(adapter3);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c =((Cursor) listView.getItemAtPosition(position));
        /*int i = c.getColumnIndex(BeerListContentProvider.BREWERY);
        Toast.makeText(getApplicationContext(), c.getString(i), Toast.LENGTH_SHORT).show();
        */
        /**/

        Intent intent = new Intent(this, BeerInfoActivity.class);

        int i = c.getColumnIndex(BeerListContentProvider._ID);
        intent.putExtra("id", c.getString(i));
        startActivity(intent);
         /**/
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("activity paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("activity resumed");
        loadList();
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
}
