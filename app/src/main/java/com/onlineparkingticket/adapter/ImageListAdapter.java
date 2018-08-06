package com.onlineparkingticket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onlineparkingticket.R;
import com.onlineparkingticket.allInterface.OnItemClick;
import com.onlineparkingticket.allInterface.OnLoadMoreListener;

import java.util.ArrayList;


@SuppressWarnings("All")
public class ImageListAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final RecyclerView rvTrending;

    private ArrayList<String> commentList;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private OnItemClick listener;
    private Context mContext;
    String flag = "0";
    TextView textView;
    boolean paidTag;

    public ImageListAdapter(Context mContext, final ArrayList<String> commentList, RecyclerView rvTrending, OnItemClick listener) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.flag = flag;
        this.rvTrending = rvTrending;
        this.paidTag = paidTag;
        this.listener = listener;

        if (rvTrending.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvTrending.getLayoutManager();

            rvTrending.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (commentList.size() > 9) {
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_images, parent, false);

            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {

            Glide.with(mContext)
                    .load(commentList.get(position))
                    .into(((ViewHolder) holder).imDP);

            ((ViewHolder) holder).imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClickPosition(position);
                    }
                }
            });

        } else {
            ((ProgressViewHolder) holder).pbLoadMore.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return commentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imDP, imDelete;

        public ViewHolder(View v) {
            super(v);
            imDP = (ImageView) v.findViewById(R.id.image_ItemImages_DP);
            imDelete = (ImageView) v.findViewById(R.id.image_ItemImages_Delete);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pbLoadMore;

        public ProgressViewHolder(View v) {
            super(v);
            pbLoadMore = (ProgressBar) v.findViewById(R.id.pbLoadMore);
        }
    }
}