package com.yingde.gaydcj.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;
import com.yingde.gaydcj.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2017/5/10.
 * 自定义地图覆盖物
 */

public class MapOverlay extends Overlay {
    double dLon = 116.359291;
    double dLat = 39.892616;
    double dSpan = 0.025062;
    int resID[] = { R.drawable.map_mark,
            R.drawable.map_mark,
            R.drawable.map_mark, R.drawable.map_mark,
            R.drawable.map_mark };
    float anchor[][] = { { 0.5f, 1.0f }, { 0.5f, (54.0f - 9.0f) / 54.0f },
            { 0.5f, 0.5f }, { 0.5f, 0.5f }, { 0.5f, 1.0f } };
    int size = resID.length;

    private Context mCon = null;
    private OverlayItem mItem = null;
    private Paint mPaint= null;
    public MapOverlay(Context con)
    {
        mCon = con;
        mPaint = new Paint();
    }
    @Override
    public boolean onTap(GeoPoint point, MapView mapView) {
        // 标记点击的坐标位置
        mItem = new OverlayItem(point, "Tap", point.toString());
        mapView.postInvalidate();
        return true;
    }
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow){
        // TODO Auto-generated method stub
        super.draw(canvas, mapView, shadow);
        if(mItem == null)
            return;
		/*
* 以下为绘制点击位置及其坐标
*/
        Drawable d = mCon.getResources().getDrawable(R.drawable.map_mark);
        Point point = mapView.getProjection().toPixels(mItem.getPoint(), null);
        d.setBounds(point.x - d.getIntrinsicWidth()/2, point.y-d.getIntrinsicHeight(),
                point.x + d.getIntrinsicWidth()/2, point.y);
        d.draw(canvas);
        Rect bounds = new Rect();
        mPaint.getTextBounds(mItem.getSnippet(), 0, mItem.getSnippet().length()-1, bounds);
        //显示坐标文本
        canvas.drawText(mItem.getSnippet(), point.x-bounds.width()/2,
                point.y-d.getIntrinsicHeight(), mPaint);
    }

}
