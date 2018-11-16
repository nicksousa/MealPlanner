package scu.csci187.fall2018.mealtracker.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import scu.csci187.fall2018.mealtracker.R;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {

    private List<String> meals;
    private List<String> dates;
    private List<String> picUrls;
    private ItemClickListener clickListener;
    Context mContext;

    public HomeRecyclerViewAdapter(Context context, List<String> meals, List<String> dates,
                                                                         List<String> picUrls) {
        this.meals = meals;
        this.dates = dates;
        this.picUrls = picUrls;
        this.mContext = context;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        vHolder.imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vHolder.getAdapterPosition();
                Toast.makeText(mContext, "TEST CLICK:  " +
                        String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        return vHolder;
    }

    // Binds data to recycled views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemName.setText(meals.get(position));
        holder.itemDate.setText(dates.get(position));
        new ImageLoaderFromUrl(holder.imView).execute(picUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imView;
        private TextView itemDate;
        private TextView itemName;

        public MyViewHolder(View itemView) {
            super(itemView);

            imView = itemView.findViewById(R.id.listItemPic);
            itemDate = itemView.findViewById(R.id.listItemDate);
            itemName = itemView.findViewById(R.id.listItemName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition()); // call the onClick in the OnItemClickListener
        }

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}