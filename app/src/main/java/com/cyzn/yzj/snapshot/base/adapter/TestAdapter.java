package com.cyzn.yzj.snapshot.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cyzn.yzj.snapshot.R;
import com.cyzn.yzj.snapshot.base.listener.OnItemClickListener;
import com.cyzn.yzj.snapshot.model.bean.TestBean;

import java.util.List;

/*
 * 项目名:    BaseFrame
 * 包名       com.zhon.frame.mvp.login.adapter
 * 文件名:    TestAdapter
 * 创建者:    ZJB
 * 创建时间:  2017/9/7 on 13:43
 * 描述:     TODO
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestBean.StoriesBean> list;
    private Context context;
    private OnItemClickListener mClickListener;

    public TestAdapter(List<TestBean.StoriesBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImages().get(0)).crossFade().into(holder.imageView);
        holder.textView.setText(list.get(position).getTitle());
        holder.textView1.setText("2018-10-31 12:00");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView, textView1;

        private OnItemClickListener mListener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            //为ItemView添加点击事件
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            textView1 = itemView.findViewById(R.id.text1);

        }


        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getPosition());
        }
    }
}
