package com.example.a10953.blackcard.z_test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by 10953 on 2017/11/2.
 */

public class BehaviorImageTest extends AppBarLayout.Behavior {

    private static final String TAG = "BehaviorImageTest";

    private static final String image_tag = "overScroll";
    private boolean isAnimate;  //是否有动画

    private View mTargetView;       // 目标View
    private int mParentHeight;      // AppBarLayout的初始高度
    private int mTargetViewHeight;  // 目标View的高度

    private static final float TARGET_HEIGHT = 3000; // 最大滑动距离
    private float mTotalDy;     // 总滑动的像素数
    private float mLastScale;   // 最终放大比例
    private int mLastBottom;    // AppBarLayout的最终Bottom值


    private float flingVelocityY = 0;
    private boolean shouldFling = false;
    private static final float MIN_FLING_VELOCITY = 10;
    private static final int TOP_CHILD_FLING_THRESHOLD = 1;
    private static final float OPTIMAL_FLING_VELOCITY = 3500;

    private int dy_xia;
    private int dy_shang;

    public BehaviorImageTest(){

    }

    public BehaviorImageTest(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }


    //AppBarLayout布局时调用
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {

        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTargetView == null) {
            mTargetView = parent.findViewWithTag(image_tag);
            if (mTargetView != null) {
                initial(abl);
            }
        }
        return handled;

    }


    //当CoordinatorLayout的子View尝试发起嵌套滚动时调用
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        // 开始滑动时，启用动画
        isAnimate = true;
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }


    //当准备开始嵌套滚动时调用
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
                                  int dx, int dy, int[] consumed) {

        if (mTargetView != null && ((dy < 0 && child.getBottom() >= mParentHeight) || (dy > 0 && child.getBottom() > mParentHeight))) {
            scale(child, target, dy);
        } else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

            //上划更流畅
            if (dy > MIN_FLING_VELOCITY || dx >= 0) {
                shouldFling = true;
                flingVelocityY = dy;
            } else {
                shouldFling = false;
            }

        }
    }

    //当嵌套滚动的子View准备快速滚动时调用
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    //停止滚动时调用
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
//        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target);

        //上划更流畅
        if (shouldFling) {
            Log.d(TAG, "onNestedPreScroll: running nested fling, velocityY is " + flingVelocityY);
            onNestedFling(coordinatorLayout, abl, target, 0, flingVelocityY, true);
        }

        recovery(abl);
    }


    // 当嵌套滚动的子View快速滚动时调用
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
                                 float velocityX, float velocityY, boolean consumed) {

        if (target instanceof RecyclerView && velocityY < 0) {
            Log.d(TAG, "onNestedFling: target is recyclerView");
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }

        // prevent fling flickering when going up
        if (target instanceof NestedScrollView && velocityY > 0) {
            consumed = true;
        }

        if (Math.abs(velocityY) < OPTIMAL_FLING_VELOCITY) {
            velocityY = OPTIMAL_FLING_VELOCITY * (velocityY < 0 ? -1 : 1);
        }
        Log.d(TAG, "onNestedFling: velocityY - " + velocityY + ", consumed - " + consumed);


        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    private void initial(AppBarLayout abl) {
        // 必须设置ClipChildren为false，这样目标View在放大时才能超出布局的范围
        abl.setClipChildren(false);
        mParentHeight = abl.getHeight();
        mTargetViewHeight = mTargetView.getHeight();
    }

    private void scale(AppBarLayout abl, View target, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT);
        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT);
        ViewCompat.setScaleX(mTargetView, mLastScale);
        ViewCompat.setScaleY(mTargetView, mLastScale);
        mLastBottom = mParentHeight + (int) (mTargetViewHeight / 2 * (mLastScale - 1));
        abl.setBottom(mLastBottom);
        target.setScrollY(0);
    }

    private void recovery(final AppBarLayout abl) {
        if (mTotalDy > 0) {
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        ViewCompat.setScaleX(mTargetView, value);
                        ViewCompat.setScaleY(mTargetView, value);
                        abl.setBottom((int) (mLastBottom - (mLastBottom - mParentHeight) * animation.getAnimatedFraction()));
                    }
                });

                anim.start();
            } else {
                ViewCompat.setScaleX(mTargetView, 1f);
                ViewCompat.setScaleY(mTargetView, 1f);
                abl.setBottom(mParentHeight);
            }
        }
    }

}
