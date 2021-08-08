package com.quince_tech.ulasbana.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quince_tech.ulasbana.Interface.ILoadMore;
import com.quince_tech.ulasbana.Model.Item;
import com.quince_tech.ulasbana.R;

import java.util.List;


/**
 * Created by qapitall on 25.02.2018.
 */

class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar=itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView time, message, point;
    public CardView cardView;
    public ItemViewHolder(View itemView) {
        super(itemView);
        time=itemView.findViewById(R.id.txtTime);
        cardView=itemView.findViewById(R.id.background);
        message=itemView.findViewById(R.id.txtMessage);
        point=itemView.findViewById(R.id.textPoint);
    }
}

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    private ILoadMore loadMore;
    private boolean isLoading;
    private Activity activity;
    private List<Item> items;
    private int visibleThreshold=20;
    private int lastVisibleItem, totalItemCount;
    private int i,j;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerVi;
    private MyAdapter myAdapter;

    public MyAdapter(final RecyclerView recyclerView, Activity activity, List<Item> items,SharedPreferences pref){
        this.activity=activity;
        this.items=items;
        this.pref = pref;
        this.editor = this.pref.edit();
        this.recyclerVi = recyclerView;
        myAdapter = this;

        final LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount=linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();

                if( !isLoading&&totalItemCount<=(lastVisibleItem+visibleThreshold)){
                    if(loadMore!=null)
                        loadMore.onLoadMore();
                        isLoading=true;
                }

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_ITEM){
            View view= LayoutInflater.from(activity)
                    .inflate(R.layout.item_layout,parent,false);
            return new ItemViewHolder(view);
        }
        else if (viewType==VIEW_TYPE_LOADING){
            View view= LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        System.out.println("test");
        if(holder instanceof ItemViewHolder ){
            final ItemViewHolder viewHolder=(ItemViewHolder) holder;
            viewHolder.time.setText(items.get(position).getTime());
            viewHolder.message.setText(items.get(position).getMessage());
            viewHolder.point.setText(items.get(position).getPoint());

                viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Skoru silmek istediğinize emin misiniz?");
                        builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                            }
                        });


                        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editor.remove("save" + items.get(position).getId());
                                editor.commit();
                                items.remove(position);
                                recyclerVi.setAdapter(myAdapter);
                            }
                        });


                        builder.show();
                        return false;
                    }
                });

        }else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder=(LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
