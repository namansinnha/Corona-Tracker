package com.developer.coreandroidx.coronavirus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.developer.coreandroidx.coronavirus.Activity.CountriesListActivity;
import com.developer.coreandroidx.coronavirus.Model.CountryModel;
import com.developer.coreandroidx.coronavirus.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListFiltered;

    public Adapter(Context context, List<CountryModel> countryModelsList) {
        super(context, R.layout.list_custom_item, countryModelsList);

        this.context = context;
        this.countryModelList = countryModelsList;
        this.countryModelListFiltered = countryModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, null, true);
        TextView countryName = view.findViewById(R.id.country_name);
        ImageView countryFlag = view.findViewById(R.id.flag);

        countryName.setText(countryModelListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelListFiltered.get(position).getFlag()).into(countryFlag);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint ==null || constraint.length() ==0) {

                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;

                } else {

                    List<CountryModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CountryModel itemModel:countryModelList){

                        if (itemModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultModel.add(itemModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }

                }

                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelListFiltered = (List<CountryModel>) results.values;
                CountriesListActivity.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };

        return filter;

    }
}
