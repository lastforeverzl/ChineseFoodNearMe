package com.zackyzhang.chinesefoodnearme.mvp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zackyzhang.chinesefoodnearme.R;
import com.zackyzhang.chinesefoodnearme.data.entity.Business;
import com.zackyzhang.chinesefoodnearme.transition.TransitionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei on 3/30/17.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {

    public interface OnPlaceClickListener {
        void onPlaceClicked(View sharedImage, String transitionName, final int position);
    }

    private LayoutInflater mLayoutInflater;
    private List<Business> placeList = new ArrayList<>();
    private Context context;
    private OnPlaceClickListener listener;

    public PlacesAdapter(Context context, OnPlaceClickListener listener) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.item_business_place, parent, false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder holder, int position) {
        holder.title.setText(placeList.get(position).getName());
        Picasso.with(context).load(placeList.get(position).getImageUrl()).fit().into(holder.placePhoto);
        float rating = placeList.get(position).getRating();
        setYelpRating(holder, rating);
        holder.number.setText(String.valueOf(position + 1));
        holder.price.setText(placeList.get(position).getPrice());
        holder.rating.setText("Based on " + placeList.get(position).getReviewCount() + " reviews");
        holder.root.setOnClickListener(view -> listener.onPlaceClicked(holder.root, TransitionUtils.getRecyclerViewTransition(position), position));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void setPlaceList(List<Business> placeList) {
        this.placeList = placeList;
        for (int i = 0; i < placeList.size(); i++) {
            notifyItemInserted(i);
        }
    }

    private void setYelpRating(PlacesViewHolder holder, float rating) {
        if (rating == 0) holder.yelpRating.setImageResource(R.drawable.stars_small_0);
        else if (rating == 1) holder.yelpRating.setImageResource(R.drawable.stars_small_1);
        else if (rating == 1.5) holder.yelpRating.setImageResource(R.drawable.stars_small_1_half);
        else if (rating == 2) holder.yelpRating.setImageResource(R.drawable.stars_small_2);
        else if (rating == 2.5) holder.yelpRating.setImageResource(R.drawable.stars_small_2_half);
        else if (rating == 3) holder.yelpRating.setImageResource(R.drawable.stars_small_3);
        else if (rating == 3.5) holder.yelpRating.setImageResource(R.drawable.stars_small_3_half);
        else if (rating == 4) holder.yelpRating.setImageResource(R.drawable.stars_small_4);
        else if (rating == 4.5) holder.yelpRating.setImageResource(R.drawable.stars_small_4_half);
        else if (rating == 5) holder.yelpRating.setImageResource(R.drawable.stars_small_5);
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_title) TextView title;
        @BindView(R.id.yelp_ratingbar) ImageView yelpRating;
        @BindView(R.id.rating) TextView rating;
        @BindView(R.id.rv_root) CardView root;
        @BindView(R.id.rv_headerImage) ImageView placePhoto;
        @BindView(R.id.price) TextView price;
        @BindView(R.id.rv_number) TextView number;

        public PlacesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
