package com.shinhan.test;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.shinhan.test.MainActivity.KEY_AGE;
import static com.shinhan.test.MainActivity.KEY_NAME;

/**
 * Created by IC-INTPC-087106 on 2017-03-25.
 */

class MyCursorAdapter extends CursorAdapter
{

    @SuppressWarnings("deprecation")
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById( R.id.tv_name );
        TextView tvAge = (TextView) view.findViewById( R.id.tv_age );

        String name = cursor.getString( cursor.getColumnIndex( KEY_NAME ) );
        String age = cursor.getString( cursor.getColumnIndex( KEY_AGE ) );

        Log.d("스트링 확인", name + ", " + age);

        tvName.setText( name );
        tvAge.setText( age );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_item, parent, false );
        return v;
    }

}
