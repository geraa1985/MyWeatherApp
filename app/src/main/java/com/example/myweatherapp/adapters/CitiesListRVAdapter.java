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

import java.util.LinkedList;

public class CitiesListRVAdapter extends RecyclerView.Adapter<CitiesListRVAdapter.ViewHolder> {
    private LinkedList<String> cityList;
    private IRVonCityClick irVonCityClick;


    public CitiesListRVAdapter(LinkedList<String> cityList, IRVonCityClick irVonCityClick) {
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
        String text = cityList.get(position);
        holder.setTextToTextView(text);
        holder.setOnItemClick(text);
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

        void setOnItemClick(String cityName) {
            textView.setOnClickListener((v)-> new Thread(()->{
                City city = new City(cityName);
                if (irVonCityClick != null){
                    irVonCityClick.onCityClick(city);
                }
            }).start());
        }
    }
}
