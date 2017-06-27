package com.yingde.gaydcj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.yingde.gaydcj.R;


public class PsyManageGetPoliceNameAdapter extends BaseAdapter implements
		Filterable {
	// 实现过滤
	private ArrayFilter mFilter;
	private LayoutInflater mInflater;
	Context context;
	// 派出所名称回调
	private pcsmcCallClass pcsmcCall;
	// 派出所名称字符数组
	public String[] pcsmcStr;

	public PsyManageGetPoliceNameAdapter(Context context,
			pcsmcCallClass pcsmcCall, String[] pcsmcStr) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.pcsmcCall = pcsmcCall;
		this.pcsmcStr = pcsmcStr;
	}

	@Override
	public int getCount() {
		return pcsmcStr == null ? 0 : pcsmcStr.length;
	}

	@Override
	public Object getItem(int arg0) {
		return pcsmcStr[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = new ViewHolder();
		if (arg1 == null) {
			// holder = new ViewHolder();
			arg1 = mInflater.inflate(R.layout.get_polise_name_item, null);
			holder.text_get_police_name = (TextView) arg1
					.findViewById(R.id.text_get_police_name);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.text_get_police_name.setText(pcsmcStr[arg0]);
		// settag 就是缓存当前的 下标啊 没有下标就拿不到数据
		holder.text_get_police_name.setTag(arg0);
		// 控件item点击事件
		holder.text_get_police_name.setOnClickListener(pcsmcCall);
		return arg1;
	}

	private class ViewHolder {
		private TextView text_get_police_name;
	}

	// 重写实现过滤方法
	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			results.values = pcsmcStr;
			results.count = pcsmcStr.length;
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			pcsmcStr = (String[]) results.values;
			if (results.count >= 0) {
				// 要刷新的数据源改变了就调用notifyDataSetChanged （）
				notifyDataSetChanged();
			} else {
				// 数据源失效了之后就 调用notifyDataSetInvalidated（）
				notifyDataSetInvalidated();
			}
		}

	}

	// 这个就是一个抽象类 里面实现了View的点击事件 还有一个 抽象方法 点击事件触发的时候 实现抽象方法的接口回调
	public static abstract class pcsmcCallClass implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			myonClick((Integer) arg0.getTag(), arg0);
		}

		public abstract void myonClick(int position, View arg0);
	}
}
