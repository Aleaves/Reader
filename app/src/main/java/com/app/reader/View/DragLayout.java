package com.app.reader.View;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 *
 * @author poplar
 * 
 */
public class DragLayout extends FrameLayout {

	private static final String TAG = "TAG";
	private ViewDragHelper mDragHelper;
	private ViewGroup mLeftContent;
	private ViewGroup mMainContent;
	private OnDragStatusChangeListener mListener;
	private Status mStatus = Status.Close;
	private boolean isDrag = true;
	private GestureDetectorCompat mDetectorCompat;

	public static enum Status {
		Close, Open, Draging, Status;
	}
	
	public interface OnDragStatusChangeListener {
		void onClose();

		void onOpen();

		void onDraging(float percent);
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status mStatus) {
		this.mStatus = mStatus;
	}

	public void setDragStatusListener(OnDragStatusChangeListener mListener) {
		this.mListener = mListener;
	}

	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mDragHelper = ViewDragHelper.create(this, mCallback);
		mDetectorCompat = new GestureDetectorCompat(getContext(),
				mGestureListener);

	}

	SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			if ((Math.abs(distanceX) > Math.abs(distanceY)) && distanceX < 0
					&& isDrag != false && mStatus == Status.Close) {
				return true;
			} else if ((Math.abs(distanceX) > Math.abs(distanceY))
					&& distanceX > 0 && isDrag != false
					&& mStatus == Status.Open) {
				return true;
			} else {
				return false;
			}
		};
	};

	public void setDrag(boolean isDrag) {
		this.isDrag = isDrag;
		if (isDrag) {
			mDragHelper.abort();
		}
	}

	ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			Log.d(TAG, "tryCaptureView: " + child);
			return true;
		};

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			Log.d(TAG, "onViewCaptured: " + capturedChild);
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return mRange;
		}

		public int clampViewPositionHorizontal(View child, int left, int dx) {
			Log.d(TAG,
					"clampViewPositionHorizontal: " + "oldLeft: "
							+ child.getLeft() + " dx: " + dx + " left: " + left);

			if (child == mMainContent) {
				left = fixLeft(left);
			}
			return left;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			Log.d(TAG, "onViewPositionChanged: " + "left: " + left + " dx: "
					+ dx);

			int newLeft = left;
			if (changedView == mLeftContent) {
				newLeft = mMainContent.getLeft() + dx;
			}

			// ��������
			newLeft = fixLeft(newLeft);

			if (changedView == mLeftContent) {
				mLeftContent.layout(0, 0, 0 + mWidth, 0 + mHeight);
				mMainContent.layout(newLeft, 0, newLeft + mWidth, 0 + mHeight);
			}
			dispatchDragEvent(newLeft);

			invalidate();
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			Log.d(TAG, "onViewReleased: " + "xvel: " + xvel + " yvel: " + yvel);
			super.onViewReleased(releasedChild, xvel, yvel);

			if (xvel == 0 && mMainContent.getLeft() > mRange / 2.0f) {
				open();
			} else if (xvel > 0) {
				open();
			} else {
				close();
			}

		}

		@Override
		public void onViewDragStateChanged(int state) {
			// TODO Auto-generated method stub

			super.onViewDragStateChanged(state);
		}

	};

	private int fixLeft(int left) {
		if (left < 0) {
			return 0;
		} else if (left > mRange) {
			return mRange;
		}
		return left;
	}

	protected void dispatchDragEvent(int newLeft) {
		float percent = newLeft * 1.0f / mRange;
		Log.d(TAG, "percent: " + percent);

		if (mListener != null) {
			mListener.onDraging(percent);
		}

		Status preStatus = mStatus;
		mStatus = updateStatus(percent);
		if (mStatus != preStatus) {
			if (mStatus == Status.Close) {
				if (mListener != null) {
					mListener.onClose();
				}
			} else if (mStatus == Status.Open) {
				if (mListener != null) {
					mListener.onOpen();
				}
			}
		}

		animViews(percent);

	}

	private Status updateStatus(float percent) {
		if (percent == 0f) {
			return Status.Close;
		} else if (percent == 1.0f) {
			return Status.Open;
		}
		return Status.Draging;
	}

	private void animViews(float percent) {
		ViewHelper.setScaleX(mLeftContent, evaluate(percent, 0.5f, 1.0f));
		ViewHelper.setScaleY(mLeftContent, 0.5f + 0.5f * percent);
		ViewHelper.setTranslationX(mLeftContent,
				evaluate(percent, -mWidth / 2.0f, 0));
		ViewHelper.setAlpha(mLeftContent, evaluate(percent, 0.5f, 1.0f));

		ViewHelper.setScaleX(mMainContent, evaluate(percent, 1.0f, 0.8f));
		ViewHelper.setScaleY(mMainContent, evaluate(percent, 1.0f, 0.8f));

		getBackground()
				.setColorFilter(
						(Integer) evaluateColor(percent, Color.BLACK,
								Color.TRANSPARENT), Mode.SRC_OVER);
	}

	public Float evaluate(float fraction, Number startValue, Number endValue) {
		float startFloat = startValue.floatValue();
		return startFloat + fraction * (endValue.floatValue() - startFloat);
	}

	public Object evaluateColor(float fraction, Object startValue,
			Object endValue) {
		int startInt = (Integer) startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;

		int endInt = (Integer) endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;

		return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
				| (int) ((startR + (int) (fraction * (endR - startR))) << 16)
				| (int) ((startG + (int) (fraction * (endG - startG))) << 8)
				| (int) ((startB + (int) (fraction * (endB - startB))));
	}

	@Override
	public void computeScroll() {
		super.computeScroll();

		if (mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	public void close() {
		close(true);
	}

	public void close(boolean isSmooth) {
		int finalLeft = 0;
		if (isSmooth) {
			if (mDragHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
		}
	}

	public void open() {
		open(true);
	}

	public void open(boolean isSmooth) {
		int finalLeft = mRange;
		if (isSmooth) {
			if (mDragHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);

			}
		} else {
			mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
		}
	}

	private int mHeight;
	private int mWidth;
	private int mRange;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ���ݸ�mDragHelper
		boolean onTouchEvent = mDetectorCompat.onTouchEvent(ev);
		return mDragHelper.shouldInterceptTouchEvent(ev) & onTouchEvent;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			mDragHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (getChildCount() < 2) {
			throw new IllegalStateException(
					"����������������. Your ViewGroup must have 2 children at least.");
		}
		if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
			throw new IllegalArgumentException(
					"��View������ViewGroup������. Your children must be an instance of ViewGroup");
		}

		mLeftContent = (ViewGroup) getChildAt(0);
		mMainContent = (ViewGroup) getChildAt(1);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mHeight = getMeasuredHeight();
		mWidth = getMeasuredWidth();

		mRange = (int) (mWidth * 0.6f);

	}

}
