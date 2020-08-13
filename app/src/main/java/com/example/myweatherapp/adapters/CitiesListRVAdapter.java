package com.example.myweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.inputdata.City;
import com.example.myweatherapp.interfaces.IRVonCityClick;

import java.util.List;

public class CitiesListRVAdapter extends RecyclerView.Adapter<CitiesListRVAdapter.ViewHolder> {
    private List<City> cityList;
    private IRVonCityClick irVonCityClick;


    public CitiesListRVAdapter(List<City> cityList, IRVonCityClick irVonCityClick) {
        this.cityList = cityList;
        this.irVonCityClick = irVonCityClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_citieslist_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = cityList.get(position).getName();
        holder.setTextToTextView(text);
        holder.setOnItemClick(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.city_rv);
        }

        void setTextToTextView(String text) {
            textView.setText(text);
        }

        void setOnItemClick(City city) {
            textView.setOnClickListener((v)->{
                if (irVonCityClick != null){
                    irVonCityClick.onCityClick(city);
                }
            });
        }
    }
}
