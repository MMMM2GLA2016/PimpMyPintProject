package fr.istic.m2gla.mmm.pimpmypint;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BeerCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public BeerCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.item_beer_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if(cursor.getPosition()%2==1) {
            view.setBackgroundColor(Color.parseColor("#b4c7f0"));
        }
        else {
            view.setBackgroundColor(Color.parseColor("#dfe7f9"));
        }

//        view.findViewById(R.id.listView);

        TextView name = (TextView) view.findViewById(R.id.name);
        String completeName = cursor.getString(cursor.getColumnIndex(BeerListContentProvider.BREWERY)) + " " + cursor.getString(cursor.getColumnIndex(BeerListContentProvider.NAME));
        name.setText(completeName);

        TextView alcohol = (TextView) view.findViewById(R.id.alcohol);
        alcohol.setText(cursor.getString(cursor.getColumnIndex(BeerListContentProvider.ALCOHOL)));
    }
}
