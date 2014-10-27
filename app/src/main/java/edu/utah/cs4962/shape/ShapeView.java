package edu.utah.cs4962.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

/**
 * Created by zbynek on 10/27/2014.
 */
public class ShapeView extends View
{
    public final int _SIDE_COUNT_MIN = 3;
    int _sideCount = _SIDE_COUNT_MIN;

    public int getSideCount()
    {
        return _sideCount;
    }

    public void setSideCount(int sideCount)
    {
        _sideCount = sideCount < _SIDE_COUNT_MIN ? _SIDE_COUNT_MIN : sideCount;
        invalidate();
    }

    public ShapeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setBackgroundColor(Color.LTGRAY);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ShapeView);
        int sideCount = attributes.getInteger(R.styleable.ShapeView_side_count, _SIDE_COUNT_MIN);
        attributes.recycle();

        setSideCount(sideCount);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        RectF shapeRect = new RectF();
        shapeRect.left = getPaddingLeft();
        shapeRect.top = getPaddingTop();
        shapeRect.right = getWidth() - getPaddingRight();
        shapeRect.bottom = getHeight() - getPaddingBottom();

        Path shapePath = new Path();
        for(int pointIndex=0; pointIndex < _sideCount; pointIndex++)
        {
            float angle = 2.0f * (float)Math.PI * (float) pointIndex / (float)_sideCount;
            float pointX = shapeRect.centerX() + FloatMath.cos(angle) * 0.5f * shapeRect.width();
            float pointY = shapeRect.centerY() + FloatMath.sin(angle) * 0.5f * shapeRect.height();

            if(pointIndex == 0)
                shapePath.moveTo(pointX, pointY);
            else
                shapePath.lineTo(pointX, pointY);
        }
        shapePath.close();

        Paint shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shapePaint.setStyle(Paint.Style.FILL);
        shapePaint.setColor(Color.GREEN);
        canvas.drawPath(shapePath, shapePaint);

    }
}
