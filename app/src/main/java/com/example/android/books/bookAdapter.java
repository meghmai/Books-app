package com.example.android.books;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class bookAdapter extends ArrayAdapter<Word> {
    public bookAdapter(@NonNull Context context, @NonNull List<Word> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word currentword = getItem(position);
        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher);
        publisher.setText(currentword.getMpublisher());
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentword.getMtitle());
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentword.getMauthor());
        return listItemView;
    }
}
