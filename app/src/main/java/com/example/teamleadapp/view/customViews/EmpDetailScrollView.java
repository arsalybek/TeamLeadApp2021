package com.example.teamleadapp.view.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;


public class EmpDetailScrollView extends NestedScrollView {
    private GestureDetector mDetector;

    public void setDetector(GestureDetector detector) {
        mDetector = detector;
    }

    public EmpDetailScrollView(@NonNull Context context) {
        super(context);
    }

    public EmpDetailScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmpDetailScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return mDetector.onTouchEvent(ev);
    }
}
