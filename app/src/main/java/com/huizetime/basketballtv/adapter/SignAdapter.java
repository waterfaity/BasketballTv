package com.huizetime.basketballtv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huizetime.basketballtv.R;
import com.huizetime.basketballtv.bean.tv.TVSignBean;

import java.util.List;

/**
 * Created by water_fairy on 2016/9/30.
 */

public class SignAdapter extends BaseAdapter {
    private List<TVSignBean.Player> mList;
    private Context mContext;
    private int mAdapterBigSize;
    private int mAdapterLittleSize;

    public SignAdapter(List<TVSignBean.Player> mList, Context mContext, int mAdapterBigSize, int mAdapterLittleSize) {
        this.mList = mList;
        this.mContext = mContext;
        this.mAdapterBigSize = mAdapterBigSize;
        this.mAdapterLittleSize = mAdapterLittleSize;
    }


    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private ViewHolder mViewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sign, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        if (position == 0) {
            mViewHolder.mName.setTextSize(mAdapterBigSize);
            mViewHolder.mNum.setTextSize(mAdapterBigSize);
            mViewHolder.mSign.setTextSize(mAdapterBigSize);
            mViewHolder.mPosition.setTextSize(mAdapterBigSize);
        } else {
            mViewHolder.mName.setTextSize(mAdapterLittleSize);
            mViewHolder.mNum.setTextSize(mAdapterLittleSize);
            mViewHolder.mSign.setTextSize(mAdapterLittleSize);
            mViewHolder.mPosition.setTextSize(mAdapterLittleSize);
        }
        mViewHolder = (ViewHolder) convertView.getTag();
        TVSignBean.Player player = mList.get(position);
        mViewHolder.mName.setText(player.getName());
        mViewHolder.mNum.setText(player.getNum());
        mViewHolder.mSign.setText(player.isSign() ? "签到" : "");
        mViewHolder.mPosition.setText(player.getPosition());

        return convertView;
    }

    public class ViewHolder {
        private View view;
        private TextView mName;
        private TextView mNum;
        private TextView mPosition;
        private TextView mSign;

        private ViewHolder(View view) {
            this.view = view;
            mName = (TextView) view.findViewById(R.id.name);
            mNum = (TextView) view.findViewById(R.id.num);
            mPosition = (TextView) view.findViewById(R.id.position);
            mSign = (TextView) view.findViewById(R.id.sign);
        }

    }
}
