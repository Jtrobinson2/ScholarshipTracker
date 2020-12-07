package com.example.scholarshiptracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;

import java.util.Objects;

public class ScholarshipAdapter extends ListAdapter<Scholarship, ScholarshipAdapter.ViewHolder> {
    

    public ScholarshipAdapter(@NonNull DiffUtil.ItemCallback<Scholarship> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scholarship currentScholarship = getItem(position);
        holder.bind(currentScholarship.getScholarshipID(), currentScholarship.getScholarshipName(), currentScholarship.getAmount(), currentScholarship.getDateApplied());

    }

    public static class ScholarshipDiff extends DiffUtil.ItemCallback<Scholarship> {

        @Override
        public boolean areItemsTheSame(@NonNull Scholarship oldItem, @NonNull Scholarship newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Scholarship oldItem, @NonNull Scholarship newItem) {
            return Objects.equals(oldItem.getScholarshipID(), newItem.getScholarshipID());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView scholarshipAmount;
        private TextView scholarshipName;
        private TextView dateApplied;
        private ImageButton editButton;
        private ImageButton deleteButton;

        private ViewHolder(@NonNull View itemView) {

            super(itemView);
            scholarshipAmount = itemView.findViewById(R.id.amount_text_view);
            scholarshipName = itemView.findViewById(R.id.scholarship_name_text_view);
            dateApplied = itemView.findViewById(R.id.date_applied_text_view);
            editButton = itemView.findViewById(R.id.list_item_edit_button);
            deleteButton = itemView.findViewById(R.id.list_item_delete_button);


        }

        public void bind(int scholarshipID, String name, int amount, String appliedDate) {
            scholarshipName.setText(name);
            scholarshipAmount.setText(String.valueOf(amount));
            dateApplied.setText(appliedDate);




        }

        public static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.scholarship_list_item, parent, false);
            return new ViewHolder(view);

        }

    }
    public interface onClickInterface {
        void onClicked(int scholarshipID);
    }

}
