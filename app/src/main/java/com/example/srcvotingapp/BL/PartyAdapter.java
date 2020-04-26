package com.example.srcvotingapp.BL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srcvotingapp.R;

import java.util.ArrayList;

import static com.example.srcvotingapp.ApplicationClass.Portfolios;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {
    private ArrayList<String> candidates;
    private ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public PartyAdapter(Context context, ArrayList<String> list) {
        candidates = list;
        activity = (ItemClicked) context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPref;
        TextView tvName, tvEmail, tvPortfolio;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvPortfolio = itemView.findViewById(R.id.tvCandidatePortfolioRow);
            tvName = itemView.findViewById(R.id.tvCandidateNameRow);
            tvEmail = itemView.findViewById(R.id.tvCandidateEmailRow);
            ivPref = itemView.findViewById(R.id.ivCandidatePicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(candidates.get(i));

        viewHolder.tvPortfolio.setText(Portfolios[i]);
        String[] details = candidates.get(i).split(",");

        if (details.length > 1) {
            viewHolder.tvName.setText(details[0].trim());
            viewHolder.tvEmail.setText(details[1].trim());
        }else{
            viewHolder.tvName.setText(candidates.get(i).trim());
            viewHolder.tvEmail.setText(candidates.get(i).trim());
        }

        // TODO: 2019/10/24 Load Pictures
//        if (candidates.get(i).getPreference().equals("bus"))
//        {
//            viewHolder.ivPref.setImageResource(R.drawable.bus);
//        }
//        else
//        {
//            viewHolder.ivPref.setImageResource(R.drawable.plane);
//        }

    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
