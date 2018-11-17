package scu.csci187.fall2018.mealtracker.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import scu.csci187.fall2018.mealtracker.Fragments.SearchFragment;
import scu.csci187.fall2018.mealtracker.R;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private List<String> meals;
    private List<String> picUrls;
    private ItemClickListener clickListener;
    SearchFragment sourceFragment;
    Context mContext;

    public SearchRecyclerViewAdapter(Context context, List<String> meals, List<String> picUrls,
                                                        SearchFragment sourceFragment) {
        this.meals = meals;
        this.picUrls = picUrls;
        this.mContext = context;
        this.sourceFragment = sourceFragment;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        // create and attach ClickListener for both image and frame of Search Item
        vHolder.imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vHolder.getAdapterPosition();
                String mealName = meals.get(position);
                sourceFragment.showMealDetail(mealName);
            }
        });
        vHolder.searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vHolder.getAdapterPosition();
                String mealName = meals.get(position);
                sourceFragment.showMealDetail(mealName);
            }
        });

        return vHolder;
    }

    // Binds data to recycled views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemName.setText(meals.get(position));
        new ImageLoaderFromUrl(holder.imView).execute(picUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imView;
        private TextView itemName;
        private TableLayout searchLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imView = itemView.findViewById(R.id.searchMealPic);
            itemName = itemView.findViewById(R.id.searchMealname);
            searchLayout = itemView.findViewById(R.id.searchTableLayout);
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
