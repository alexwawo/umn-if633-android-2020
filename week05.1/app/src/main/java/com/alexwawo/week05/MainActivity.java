package com.alexwawo.week05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int OFFSET = 120;
    private static final int MULTIPLIER = 100;
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private int mOffset = OFFSET;
    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);

        // Set properties of the Paint used to draw on the canvas.
        mPaint.setColor(mColorBackground);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);

        // Get a reference to the ImageView.
        mImageView = (ImageView) findViewById(R.id.myimageview);

    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;
        Log.d("DEBUG", "vWidth: " + vWidth);
        Log.d("DEBUG", "vHeight: " + vHeight);
        Log.d("DEBUG", "halfWidth: " + halfWidth);
        Log.d("DEBUG", "halfHeight: " + halfHeight);
        Log.d("DEBUG", "mOffset: " + mOffset);
        Log.d("DEBUG", "OFFSET: " + OFFSET);
        if (mOffset == OFFSET) {
            Log.d("DEBUG", "mOffset == OFFSET");
            mBitmap = Bitmap.createBitmap(
                    vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        } else {
            if (mOffset < halfWidth && mOffset < halfHeight) {
                Log.d("DEBUG", "mOffset < halfWidth & halfHeight");
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                // mRect.set(LEFT, TOP, RIGHT, BOTTOM);
                mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else {
                Log.d("DEBUG", "else");
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done);
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        view.invalidate();
    }
}
