package com.example.angela.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.angela.fulicenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angela on 2016/10/19.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvFooter)
    public TextView mTvFooter;

    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
