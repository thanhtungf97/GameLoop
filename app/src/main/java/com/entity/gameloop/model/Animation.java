package com.entity.gameloop.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;
    }

    private float frameTime;

    private long lastFrame;

    public Animation(Bitmap[] frames, float animtime) {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animtime / frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect destination) {
        if (!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float) (frames[frameIndex].getWidth()) / frames[frameIndex].getHeight();
        if (rect.width() > rect.height()) {
            rect.left = (int) (rect.right - (rect.height() * whRatio));
        } else {
            rect.top = (int) (rect.bottom - (rect.width() * (1 / whRatio)));
        }
    }


    public void update() {
        if (!isPlaying) {
            return;
        }
        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameTime++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}