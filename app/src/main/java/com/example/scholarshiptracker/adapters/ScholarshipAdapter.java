package com.example.scholarshiptracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;

import java.text.DecimalFormat;
import java.util.Objects;


public class ScholarshipAdapter extends ListAdapter<Scholarship, ScholarshipAdapter.ViewHolder> {
    private onClickInterface onClickInterface;
//    Decimal format has absurd number of placeholders so that trolls won't break the precision by adding too much
    public static DecimalFormat amountPrecision = new DecimalFormat("###,###,###,###,###,###,###,###,###.00");

    public ScholarshipAdapter(@NonNull DiffUtil.ItemCallback<Scholarship> diffCallback, onClickInterface onClickInterface) {
        super(diffCallback);
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scholarship currentScholarship = getItem(position);
        holder.bind(currentScholarship, currentScholarship.getScholarshipName(), currentScholarship.getAmount(), currentScholarship.getDateApplied(), onClickInterface);

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
        private ImageView moneyImageView;
        private CardView cardView;
        private ImageButton popupMenu;


        private ViewHolder(@NonNull View itemView) {

            super(itemView);
            scholarshipAmount = itemView.findViewById(R.id.amount_text_view);
            scholarshipName = itemView.findViewById(R.id.scholarship_name_text_view);
            dateApplied = itemView.findViewById(R.id.date_applied_text_view);
            moneyImageView = itemView.findViewById(R.id.list_item_money_image_view);
            cardView = itemView.findViewById(R.id.list_item_card_view);
            popupMenu = itemView.findViewById(R.id.popup_menu_button);


        }

        public void bind(Scholarship currentScholarship, String name, double amount, String appliedDate, ScholarshipAdapter.onClickInterface onClickInterface) {
            scholarshipName.setText(name);

            scholarshipAmount.setText(amountPrecision.format(amount));
            dateApplied.setText(appliedDate);
            moneyImageView.setImageResource(R.drawable.ic_baseline_attach_money_white24);

            popupMenu.setOnClickListener(view -> showPopupMenu(view, menuItem -> {
                switch (menuItem.getItemId()) {
                    case (R.id.edit_scholarship_menu_item):
                        onClickInterface.onEditClicked(currentScholarship, getAbsoluteAdapterPosition());
                        return true;
                    case (R.id.delete_scholarships_menu_item):
                        onClickInterface.onDeleteClicked(currentScholarship, getAbsoluteAdapterPosition());
                        return true;
                    default:
                        return false;

                }
            }));

            cardView.setOnClickListener(view -> {
                onClickInterface.onItemViewClick(currentScholarship, getAdapterPosition());
            });


        }

        public static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.scholarship_list_item, parent, false);
            return new ViewHolder(view);

        }

        private void showPopupMenu(View view, PopupMenu.OnMenuItemClickListener listener) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.list_item_popup_menu);
            popupMenu.setOnMenuItemClickListener(listener);
            popupMenu.show();
        }


    }

    public interface onClickInterface {
        void onEditClicked(Scholarship scholarship, int position);

        void onDeleteClicked(Scholarship scholarship, int position);

        void onItemViewClick(Scholarship scholarship, int position);
    }


}
