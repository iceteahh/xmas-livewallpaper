package icetea.com.xmaslivewallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by icetea on 12/9/15.
 */
public class SnowFlake {

    private int mWidth, mHeight;
    private int mMaxParticle;
    private Particle[] particles;
    private Paint mPaint;
    private Random mRandom;
    private int mMinRadius;
    private int mBaseRadius;
    private int mMinVy;
    private int mBaseVy;
    private int mMinVx;

    public SnowFlake(Context context, int width, int height, int maxParticle){
        mWidth = width;
        mHeight = height;
        mMaxParticle = maxParticle;
        particles = new Particle[maxParticle];
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mRandom = new Random();
        mMinRadius = context.getResources().getDimensionPixelSize(R.dimen.particle_min_radius);
        mBaseRadius = context.getResources().getDimensionPixelSize(R.dimen.particle_base_radius);
        mMinVy = context.getResources().getDimensionPixelSize(R.dimen.particle_min_vy);
        mBaseVy = context.getResources().getDimensionPixelSize(R.dimen.particle_base_vy);
        mMinVx = context.getResources().getDimensionPixelSize(R.dimen.particle_min_vx);
    }

    public void draw(Canvas c){
        for(Particle particle : particles){

            particle.x += particle.vx;
            particle.y += particle.vy;

            if(particle.y > mHeight){
                particle.reset();
            }

            mPaint.setAlpha(particle.o);
            c.drawCircle(particle.x, particle.y, particle.r, mPaint);
        }
    }

    public void createSnowFlake() {
        for(int i = 0; i < mMaxParticle; i++){
            Particle particle = new Particle();
            particle.reset();
            particles[i] = particle;
        }
    }

    class Particle {
        float x, y, vx, vy, r;
        int o;

        public void reset(){
            x = ((float) mRandom.nextInt(10) / 10) * mWidth;
            y = ((float) mRandom.nextInt(10) / 10) * - mHeight;
            vy = mMinVy + ((float) mRandom.nextInt(10) / 10)  * mBaseVy;
            vx = mMinVx / 2 - ((float) mRandom.nextInt(10) / 10) * mMinVx;
            r = mMinRadius + ((float) mRandom.nextInt(10) / 10) * mBaseRadius;
            o = 155 + mRandom.nextInt(100);
        }
    }
}
