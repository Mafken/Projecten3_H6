package projecten3_h6.evaandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import projecten3_h6.evaandroid.domain.Ingredient;
import projecten3_h6.evaandroid.R;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppinglistViewHolder> {

    private int itemCount;
    private List<Ingredient> shoppingList;

    public ShoppingListAdapter(List<Ingredient> shoppingList) {
        this.shoppingList = shoppingList;
        this.itemCount = shoppingList.size();
    }

    @Override
    public ShoppingListAdapter.ShoppinglistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_shoppinglist, parent, false);
        return new ShoppinglistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppinglistViewHolder holder, int position) {
        CheckBox shoppingslistItemName = holder.shoppinglistItemName;
        TextView shoppinglistItemCount = holder.shoppinglistItemCount;

        shoppingslistItemName.setText(shoppingList.get(position).getName());
        shoppinglistItemCount.setText(shoppingList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class ShoppinglistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shoppinglistItemNamecbx)
        public CheckBox shoppinglistItemName;

        @BindView(R.id.shoppinglistItemCount)
        public TextView shoppinglistItemCount;

        public ShoppinglistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
