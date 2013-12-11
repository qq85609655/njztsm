package com.njztsm.app.common;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.Toast;

public class GestureListener implements GestureDetector.OnGestureListener {

	private Context context;
	private ImageSwitcher imswitcher;
	private int[] imageIds;
	private View[] views;
	private int i = 0;

	public GestureListener() {
	}

	public GestureListener(Context context, ImageSwitcher imswitcher,
			int[] imageIds, View[] views) {
		setContext(context);
		setImswitcher(imswitcher);
		setImageIds(imageIds);
		setViews(views);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		if (velocityX > 0) {
			// TODO
			Toast.makeText(context, "速度大于0", Toast.LENGTH_SHORT).show();

			float halfWidth = imswitcher.getWidth() / 2.0f;
			float halfHeight = imswitcher.getHeight() / 2.0f;
			int duration = 500;

			Rotate3D rdin = new Rotate3D(-75, 0, 0, halfWidth, halfHeight);
			rdin.setDuration(duration);
			rdin.setFillAfter(true);
			imswitcher.setInAnimation(rdin);
			Rotate3D rdout = new Rotate3D(15, 90, 0, halfWidth, halfHeight);

			rdout.setDuration(duration);
			rdout.setFillAfter(true);
			imswitcher.setOutAnimation(rdout);

			i = (i - 1);

			Log.i("i的值", String.valueOf(i));
			int p = i % 4;
			Log.i("p的值", String.valueOf(p));
			if (p >= 0) {
				setpic(p);
				imswitcher.setImageResource(imageIds[p]);

			} else {

				int k = 4 + p;
				setpic(k);
				imswitcher.setImageResource(imageIds[k]);

			}

		}
		if (velocityX < 0) {
			// TODO
			Toast.makeText(context, "速度小于0", Toast.LENGTH_SHORT).show();

			float halfWidth = imswitcher.getWidth() / 2.0f;
			float halfHeight = imswitcher.getHeight() / 2.0f;
			int duration = 500;

			Rotate3D rdin = new Rotate3D(75, 0, 0, halfWidth, halfHeight);
			rdin.setDuration(duration);
			rdin.setFillAfter(true);
			imswitcher.setInAnimation(rdin);
			Rotate3D rdout = new Rotate3D(-15, -90, 0, halfWidth, halfHeight);

			rdout.setDuration(duration);
			rdout.setFillAfter(true);
			imswitcher.setOutAnimation(rdout);

			i = (i + 1);
			int p = i % 4;

			if (p >= 0) {
				setpic(p);
				imswitcher.setImageResource(imageIds[p]);

			} else {

				int k = 4 + p;
				setpic(k);
				imswitcher.setImageResource(imageIds[k]);

			}
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		int p = i % 4;

		if (p >= 0) {

			Toast.makeText(context, String.valueOf(p), Toast.LENGTH_SHORT)
					.show();
		} else {

			int k = 4 + p;
			Toast.makeText(context, String.valueOf(k), Toast.LENGTH_SHORT)
					.show();
		}

		// TODO
		Toast.makeText(context, "ssssss", Toast.LENGTH_SHORT).show();

		return true;
	}

	public void setpic(int m) {

		for (int i = 0; i < views.length; i++) {
			if (i == m) {
				views[i].setBackgroundColor(0xffb50202);
			} else {
				views[i].setBackgroundColor(0xffebeaea);
			}

		}

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ImageSwitcher getImswitcher() {
		return imswitcher;
	}

	public void setImswitcher(ImageSwitcher imswitcher) {
		this.imswitcher = imswitcher;
	}

	public int[] getImageIds() {
		return imageIds;
	}

	public void setImageIds(int[] imageIds) {
		this.imageIds = imageIds;
	}

	public View[] getViews() {
		return views;
	}

	public void setViews(View[] views) {
		this.views = views;
	}

}