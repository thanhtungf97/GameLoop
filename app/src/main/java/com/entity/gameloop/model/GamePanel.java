package com.entity.gameloop.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.entity.gameloop.cont.Constants;
import com.entity.gameloop.thread.MainThread;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;

    private SceneManager sceneManager;


    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        mainThread = new MainThread(getHolder(), this);

        sceneManager = new SceneManager();


        setFocusable(true);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        mainThread.setRunning(true);
        mainThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        sceneManager.reciveTouch(event);

        return true;
    }


    public void update() {
        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        sceneManager.draw(canvas);
    }
}
