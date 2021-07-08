package com.example.universitylist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universitylist.Model.Model;
import com.example.universitylist.R;
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.CardViewHolder> implements Filterable, RecyclerViewFastScroller.OnPopupTextUpdate {

    Context context;
    List<Model> modelList;
    CardClickListener cardClickListener;

    List<Model> modelListAll;
    View view;

    public MainActivityAdapter(Context context, List<Model> modelList,CardClickListener cardClickListener){

        this.context=context;
        this.modelList=modelList;
        this.cardClickListener=cardClickListener;
        this.modelListAll=new ArrayList<>(modelList);

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.main_activity_card,parent,false);
        return new CardViewHolder(view,cardClickListener);
    }

    @Override
    public void onBindViewHolder( MainActivityAdapter.CardViewHolder holder, int position) {
        Model model=modelList.get(position);
        holder.university_name.setText(model.getName());
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Model> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(modelListAll);
            }
            else{
                for(Model model:modelListAll){
                    if(model.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(model);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            modelList.clear();
            modelList.addAll((Collection<? extends Model>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public CharSequence onChange(int i) {
        return String.valueOf(modelList.get(i).getName().charAt(0));
    }


    class CardViewHolder extends RecyclerView.ViewHolder{

        TextView university_name;
        CardClickListener cardClickListener;
        public CardViewHolder( View itemView,CardClickListener cardClickListener) {
            super(itemView);
            university_name=itemView.findViewById(R.id.university_name);

            this.cardClickListener=cardClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.onCardClick(modelList.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface CardClickListener{
        void onCardClick(Model university_model);
    }
}
