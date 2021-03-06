package com.mytv.rtzhdj.mvp.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 自动加载 RecyclerView
 * [滑动时不加载图片，停止滑动时加载 - Glide]
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-2
 * @update
 */
public class AutoLoadRecyclerViewGlide extends RecyclerView implements LoadFinishCallBack {

	private onLoadMoreListener loadMoreListener;	//加载更多回调
	private boolean isLoadingMore;					//是否加载更多

	private Context mContext;

	public AutoLoadRecyclerViewGlide(Context context) {
		this(context, null);
		this.mContext = context;
	}

	public AutoLoadRecyclerViewGlide(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.mContext = context;
	}

	public AutoLoadRecyclerViewGlide(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;

		isLoadingMore = false;	//默认无需加载更多
		setOnScrollListener(new AutoLoadScrollListener(true, true));
	}

	/**
	 * 配置显示图片，需要设置这几个参数，快速滑动时，暂停图片加载
	 *
	 * @param pauseOnScroll
	 * @param pauseOnFling
	 */
	public void setOnPauseListenerParams(boolean pauseOnScroll, boolean pauseOnFling) {

		setOnScrollListener(new AutoLoadScrollListener(pauseOnScroll, pauseOnFling));

	}

	public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}

	@Override
	public void loadFinish(Object obj) {
		isLoadingMore = false;
	}


	//加载更多的回调接口
	public interface onLoadMoreListener {
		void loadMore();
	}


	/**
	 * 滑动自动加载监听器
	 */
	private class AutoLoadScrollListener extends OnScrollListener {

		private final boolean pauseOnScroll;
		private final boolean pauseOnFling;

		public AutoLoadScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
			super();
			this.pauseOnScroll = pauseOnScroll;
			this.pauseOnFling = pauseOnFling;
		}

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);

			//由于GridLayoutManager是LinearLayoutManager子类，所以也适用
			if (getLayoutManager() instanceof LinearLayoutManager) {
				int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
				int totalItemCount = AutoLoadRecyclerViewGlide.this.getAdapter().getItemCount();

				//有回调接口，且不是加载状态，且计算后剩下2个item，且处于向下滑动，则自动加载
				if (loadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount -
						2 && dy > 0) {
					loadMoreListener.loadMore();
					isLoadingMore = true;
				}
			}
		}

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

			switch (newState) {
				case 0:
//						imageLoader.resume();
					Glide.with(mContext).resumeRequests();
					break;

				case 1:
					if (pauseOnScroll) {
//							imageLoader.pause();
						Glide.with(mContext).pauseRequests();
					} else {
//							imageLoader.resume();
						Glide.with(mContext).resumeRequests();

					}
					break;

				case 2:
					if (pauseOnFling) {
//							imageLoader.pause();
						Glide.with(mContext).resumeRequests();
					} else {
//							imageLoader.resume();
						Glide.with(mContext).resumeRequests();
					}
					break;
			}
		}
	}
}
