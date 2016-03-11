package app.droidekas.scalablegridrecycler.adapter;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.droidekas.scalablegridrecycler.R;
import app.droidekas.scalablegridrecycler.views.GridRecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Satyarth on 11/03/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.Holder> {

    private List<Uri> dataList;
    public static final int totalSpan = 100;
    private int currentSpan;
    private int spanConstant = 20;
    private int multiplier = 1;
    private GridRecyclerView grv;

    public GridAdapter(GridRecyclerView gr) {
        super();
        dataList = new ArrayList<>();
        currentSpan = spanConstant;
        grv = gr;
    }


    public void addData(List<Uri> dataToAdd) {
        dataList.addAll(dataToAdd);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setUp(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_in_view)
        ImageView imgView;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgView.setOnClickListener(new View.OnClickListener() {
                @
                        Override
                public void onClick(View v) {
                    setCurrentSpan(++multiplier * spanConstant % 100);
                    delayedNotify(getAdapterPosition(), calculateRange());
                }
            });
        }

        public void setUp(Uri uri) {
            Glide.with(imgView.getContext())
                    .load(uri)
                    .centerCrop()
                    .crossFade()
                    .into(imgView);

        }
    }


    public GridLayoutManager.SpanSizeLookup getScalableSpanSizeLookUp() {
        return scalableSpanSizeLookUp;
    }

    public int calculateRange() {
        int start = ((GridLayoutManager) grv.getLayoutManager()).findFirstVisibleItemPosition();
        int end = ((GridLayoutManager) grv.getLayoutManager()).findLastVisibleItemPosition();
        if (start < 0)
            start = 0;
        if (end < 0)
            end = 0;
        return end - start;
    }


    private GridLayoutManager.SpanSizeLookup scalableSpanSizeLookUp = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return getCurrentSpan();
        }
    };


    public int getCurrentSpan() {
        return currentSpan;
    }

    public void setCurrentSpan(int span) {
        this.currentSpan = span;

    }

    public void delayedNotify(final int pos, final int range) {
        grv.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemRangeChanged(pos - range > 0 ? pos - range : 0, range * 2 < getItemCount() ? range * 2 : range);
            }
        }, 100);
    }

}
