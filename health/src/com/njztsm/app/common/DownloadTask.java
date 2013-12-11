package com.njztsm.app.common;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageSwitcher;

public class DownloadTask extends AsyncTask {

	private Context context;
	private ImageSwitcher imswitcher;
	private int[] imageIds;
	private View[] views;
	private int i = 0;

	public DownloadTask() {
	}

	public DownloadTask(Context context, ImageSwitcher imswitcher,
			int[] imageIds, View[] views) {
		setContext(context);
		setImswitcher(imswitcher);
		setImageIds(imageIds);
		setViews(views);
	}

	@Override
	protected Object doInBackground(Object... arg0) {

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress(null);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);

		float halfWidth = imswitcher.getWidth() / 2.0f;
		float halfHeight = imswitcher.getHeight() / 2.0f;
		int duration = 500;
		int depthz = 0;// viewFlipper.getWidth()/2;

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
