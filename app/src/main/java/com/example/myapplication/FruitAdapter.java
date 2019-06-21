package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FruitAdapter extends ArrayAdapter<MainActivity.Fruit> {
    private Context context;
    private int resourceId;
    private List<MainActivity.Fruit> items, tempItems, suggestions;
    public FruitAdapter(@NonNull Context context, int resourceId, ArrayList<MainActivity.Fruit> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            MainActivity.Fruit fruit = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.textView);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(fruit.image);
            name.setText(fruit.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public MainActivity.Fruit getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }

    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            MainActivity.Fruit fruit = (MainActivity.Fruit) resultValue;
            return fruit.name;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (MainActivity.Fruit fruit: tempItems) {
                    if (fruit.name.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(fruit);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<MainActivity.Fruit> tempValues = (ArrayList<MainActivity.Fruit>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (MainActivity.Fruit fruitObj : tempValues) {
                    add(fruitObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}