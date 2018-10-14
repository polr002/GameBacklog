package com.example.roy.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private List<gameObject> gameBacklog;
    final private CardClickListener cardClickListener;


    public interface CardClickListener {
        void cardOnClick (int i);
    }

    public CustomAdapter(List<gameObject> gameBacklog, CardClickListener cardClickListener) {
        this.gameBacklog = gameBacklog;
        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_layout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        gameObject games = gameBacklog.get(position);

        holder.gameTitle.setText(games.getTitle());
        holder.platform.setText(games.getPlatform());
        holder.status.setText(games.getStatus());
       // holder.dateAdded.setText(games.getDateAdded());

    }

    @Override
    public int getItemCount() {
        return gameBacklog.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView gameTitle;
        TextView platform;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);

            gameTitle = itemView.findViewById(R.id.gameTitle);
            platform = itemView.findViewById(R.id.platform);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            cardClickListener.cardOnClick(clickedPosition);
        }
    }
    public void swapList (List<gameObject> newList) {
        gameBacklog = newList;
        if(newList != null) {
            this.notifyDataSetChanged();
        }
    }

}
