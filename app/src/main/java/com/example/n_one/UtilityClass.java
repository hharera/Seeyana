package com.example.n_one;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class UtilityClass {
    public static void buttonEffect(final View view, final ClickEvent clickEvent) {
        Log.wtf("onTouch_1", "true");


        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.wtf("onTouch_", "true");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(view.getContext().getResources().getColor(R.color.yellow));
//                        v.setAlpha(0.1f);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
//                        v.setAlpha(1f);
                        v.setBackgroundColor(Color.parseColor("#28C9C7C7"));
                        v.invalidate();
                        if (clickEvent != null)
                            clickEvent.getActionUpClicked(v);
                        break;
                    }
                }
                return true;
            }
        });
    }
}
