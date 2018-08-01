package com.onlineparkingticket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.allInterface.OnLoadMoreListener;

import java.util.ArrayList;


@SuppressWarnings("All")
public class PendingTicketAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final RecyclerView rvTrending;

    private ArrayList<String> commentList;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;
    String flag = "0";
    TextView textView;
    boolean paidTag, chkSelect;


    public PendingTicketAdapter(Context mContext, ArrayList<String> commentList, RecyclerView rvTrending, boolean paidTag, boolean chkSelect) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.flag = flag;
        this.rvTrending = rvTrending;
        this.paidTag = paidTag;
        this.chkSelect = chkSelect;

        if (rvTrending.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvTrending.getLayoutManager();


            rvTrending.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
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
                    R.layout.item_resolved_ticket, parent, false);

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

            if (paidTag) {
                ((ViewHolder) holder).tvPaid.setText(mContext.getString(R.string.paid));
            } else {
                ((ViewHolder) holder).tvPaid.setText(mContext.getString(R.string.unpaid));
            }

            if (chkSelect) {
                ((ViewHolder) holder).chkSelect.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).chkSelect.setVisibility(View.GONE);
            }

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

        TextView tvName, tvDate, tvPlate, tvViolationNo, tvPaid;
        CheckBox chkSelect;

        public ViewHolder(View v) {
            super(v);
            chkSelect = (CheckBox) v.findViewById(R.id.checkbox_ItemCommonList_Select);

            tvName = (TextView) v.findViewById(R.id.item_CommonListTicket_Name);
            tvDate = (TextView) v.findViewById(R.id.item_CommonListTicket_Date);
            tvPlate = (TextView) v.findViewById(R.id.item_CommonListTicket_Plate);
            tvViolationNo = (TextView) v.findViewById(R.id.item_CommonListTicket_ViolationNo);
            tvPaid = (TextView) v.findViewById(R.id.item_CommonListTicket_Paid);
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
