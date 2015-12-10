package icetea.com.xmaslivewallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Created by icetea on 12/8/15.
 */
public class XMasWallpaper extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new XMasEngine();
    }

    class XMasEngine extends Engine {
        private final Runnable mDrawer = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };
        private final Handler mHandler = new Handler();
        private boolean mVisible;
        private Bitmap mBackground;
        private Paint mPaint;
        private SnowFlake snowFlake;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mBackground = BitmapFactory.decodeResource(getResources(), R.raw.bg_xmas);
            mPaint = new Paint();
        }

        private void drawFrame(){
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    // draw something
                    drawSnow(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }

            mHandler.removeCallbacks(mDrawer);
            if (mVisible) {
                mHandler.postDelayed(mDrawer, 30);
            }
        }

        private void drawSnow(Canvas c) {
            c.drawBitmap(mBackground, 0, 0, mPaint);
            snowFlake.draw(c);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            mBackground = Bitmap.createScaledBitmap(mBackground, width, height, false);
            snowFlake = new SnowFlake(XMasWallpaper.this, width, height, 150);
            snowFlake.createSnowFlake();
            drawFrame();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible = false;
            mHandler.removeCallbacks(mDrawer);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            mVisible = visible;
            if(visible){
                drawFrame();
            } else {
                mHandler.removeCallbacks(mDrawer);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mDrawer);
        }
    }

}
