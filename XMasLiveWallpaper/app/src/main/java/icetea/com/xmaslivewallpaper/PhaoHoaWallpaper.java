package icetea.com.xmaslivewallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by thanhnt on 10/12/2015.
 */
public class PhaoHoaWallpaper extends WallpaperService{



    @Override
    public Engine onCreateEngine() {
        return new PhaoHoaEngine();
    }



    public class PhaoHoaEngine extends Engine{
        private Bitmap background;
        private Paint paint;
        private Fireworks fireworks;
        private boolean mVisible;



        Handler handler=new Handler();
        private final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        };



        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            background = BitmapFactory.decodeResource(getResources(), R.raw.bg_tet);
            paint=new Paint();
        }

        private void drawPhao(final Canvas c) {
            c.drawBitmap(background, 0, 0, paint);
            fireworks.doDraw(c,paint);

        }

        public void startAnimation(){
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {

                    // draw something
                    drawPhao(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }

            handler.removeCallbacks(runnable);
            if (mVisible) {

                handler.postDelayed(runnable, 20);
            }
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            background = Bitmap.createScaledBitmap(background, width, height, false);
            fireworks = new Fireworks( width, height);
            fireworks.createRockets();
            Log.e("onSurfaceChanged","onSurfaceChanged");

            startAnimation();


        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible=false;
            handler.removeCallbacks(runnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            mVisible=visible;
            if(visible){
                startAnimation();
            }else{
                handler.removeCallbacks(runnable);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(runnable);
        }
    }



}
