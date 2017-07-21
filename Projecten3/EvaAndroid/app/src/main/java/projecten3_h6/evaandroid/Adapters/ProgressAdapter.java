package projecten3_h6.evaandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projecten3_h6.evaandroid.Domain.Day;
import projecten3_h6.evaandroid.Fragments.ProgressFragment;
import projecten3_h6.evaandroid.R;

/**
 * Created by jensleirens on 07/07/2017.
 */

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>{

    private int itemCount;
    private List<Day> currentDays;
    ViewGroup parent;

    public ProgressAdapter(List<Day> currentDays) {
        this.currentDays = currentDays;
        itemCount = ProgressFragment.segmentSize;
    }

    @Override
    public ProgressAdapter.ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_progress,parent,false);
        this.parent = parent;
        v.setOnClickListener(ProgressFragment.progressOnclickListener);
        return new ProgressAdapter.ProgressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProgressAdapter.ProgressViewHolder holder, final int position) {
        TextView progressDayOfTheWeek = holder.progressDayOfTheWeek;
        TextView progressDishTitle = holder.progressDishTitle;
        ToggleButton toggleComplete = holder.toggleComplete;
        ImageView dayDishImage = holder.dayDishImage;
        Calendar cal = Calendar.getInstance();

        progressDayOfTheWeek.setText(currentDays.get(position).getDayOfTheWeekString());

        if(currentDays.get(position).getDish() != null) {
            progressDishTitle.setText(currentDays.get(position).getDish().getName());
            Context context = holder.dayDishImage.getContext();
            Picasso.with(context).load(currentDays.get(position).getDish().getImageId()).into(dayDishImage);
            dayDishImage.setImageResource(currentDays.get(position).getDish().getImageId());
            toggleComplete.setChecked(currentDays.get(position).isCompleted());
            if(cal.get(Calendar.DAY_OF_YEAR) >= currentDays.get(position).getDayOfTheYear()) {
                toggleComplete.setVisibility(View.VISIBLE);
            }
        } else if (cal.get(Calendar.DAY_OF_YEAR) > currentDays.get(position).getDayOfTheYear()) {
            progressDishTitle.setText("You forgot to pick a dish.");
        }

        toggleComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int daysLength = ProgressFragment.user.getDays().size();
                ProgressFragment.user.getDays().get(daysLength - itemCount + position).setCompleted(isChecked);
                ProgressFragment.user.calculateStatistics();
                ProgressFragment.recheckCheckboxes();
                // Achievement earned
                ProgressFragment.app.earnAchievement(parent.getContext(), LayoutInflater.from(parent.getContext()), parent, "Vegan Rookie");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressCardDay)
        public TextView progressDayOfTheWeek;

        @BindView(R.id.progressCardDishTitle)
        public TextView progressDishTitle;

        @BindView(R.id.toggleComplete)
        public ToggleButton toggleComplete;


        @BindView(R.id.dayDishImage)
        public ImageView dayDishImage;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
