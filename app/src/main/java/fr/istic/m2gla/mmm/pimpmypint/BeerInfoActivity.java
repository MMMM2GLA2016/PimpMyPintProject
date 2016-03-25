package fr.istic.m2gla.mmm.pimpmypint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import fr.istic.m2gla.mmm.pimpmypint.firebase.model.Beer;

public class BeerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_info);

        Firebase mFirebaseRef = ((PimpMyPintApplication) this.getApplication()).getmFirebaseRef();

        Intent intent = this.getIntent();
        String id = intent.getExtras().get("id").toString();

        Firebase fb = mFirebaseRef.child("Beers").child(id+1);


        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //snapshot.getChildren().
                infoBeer(snapshot.getValue(Beer.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void infoBeer(Beer beer){
        TextView brewery = (TextView) findViewById(R.id.breweryLabel);
        brewery.setText("Marque : " + beer.getBrasserie());
        TextView name = (TextView) findViewById(R.id.nameLabel);
        name.setText("Nom : " + beer.getNom());
        TextView alcohol = (TextView) findViewById(R.id.alcoholLabel);
        alcohol.setText(beer.getAlcool() + "%");
    }
}
