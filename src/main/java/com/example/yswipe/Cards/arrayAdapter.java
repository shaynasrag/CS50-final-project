package com.example.yswipe.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yswipe.R;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards> {

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items) {
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        cards cardItem = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        }
        TextView name = convertView.findViewById(R.id.name);
        TextView resCollege = convertView.findViewById(R.id.resCollege);
        TextView year = convertView.findViewById(R.id.year);
        ImageView image = convertView.findViewById(R.id.image);


        name.setText(cardItem.getName());
        resCollege.setText(cardItem.getResCollege());
        year.setText(cardItem.getYear());

        switch (cardItem.getProfilePicUrl()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher_foreground).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(getContext()).load(cardItem.getProfilePicUrl()).into(image);
                break;
        }

        return convertView;

        }
}
