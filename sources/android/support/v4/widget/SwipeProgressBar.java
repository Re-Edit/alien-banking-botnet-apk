package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

final class SwipeProgressBar {
    private static final int ANIMATION_DURATION_MS = 2000;
    private static final int COLOR1 = -1291845632;
    private static final int COLOR2 = Integer.MIN_VALUE;
    private static final int COLOR3 = 1291845632;
    private static final int COLOR4 = 436207616;
    private static final int FINISH_ANIMATION_DURATION_MS = 1000;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private Rect mBounds = new Rect();
    private final RectF mClipRect = new RectF();
    private int mColor1;
    private int mColor2;
    private int mColor3;
    private int mColor4;
    private long mFinishTime;
    private final Paint mPaint = new Paint();
    private View mParent;
    private boolean mRunning;
    private long mStartTime;
    private float mTriggerPercentage;

    SwipeProgressBar(View view) {
        this.mParent = view;
        this.mColor1 = COLOR1;
        this.mColor2 = Integer.MIN_VALUE;
        this.mColor3 = COLOR3;
        this.mColor4 = COLOR4;
    }

    /* access modifiers changed from: package-private */
    public void setColorScheme(int i, int i2, int i3, int i4) {
        this.mColor1 = i;
        this.mColor2 = i2;
        this.mColor3 = i3;
        this.mColor4 = i4;
    }

    /* access modifiers changed from: package-private */
    public void setTriggerPercentage(float f) {
        this.mTriggerPercentage = f;
        this.mStartTime = 0;
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (!this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = true;
            this.mParent.postInvalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = false;
            this.mParent.postInvalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRunning() {
        return this.mRunning || this.mFinishTime > 0;
    }

    /* access modifiers changed from: package-private */
    public void draw(Canvas canvas) {
        long j;
        float f;
        Canvas canvas2 = canvas;
        int width = this.mBounds.width();
        int height = this.mBounds.height();
        int i = width / 2;
        int i2 = height / 2;
        int save = canvas.save();
        canvas2.clipRect(this.mBounds);
        if (this.mRunning || this.mFinishTime > 0) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j2 = this.mStartTime;
            long j3 = (currentAnimationTimeMillis - j2) / 2000;
            float f2 = ((float) ((currentAnimationTimeMillis - j2) % 2000)) / 20.0f;
            boolean z = false;
            if (!this.mRunning) {
                j = j3;
                long j4 = this.mFinishTime;
                if (currentAnimationTimeMillis - j4 >= 1000) {
                    this.mFinishTime = 0;
                    return;
                }
                float f3 = (float) i;
                float interpolation = INTERPOLATOR.getInterpolation((((float) ((currentAnimationTimeMillis - j4) % 1000)) / 10.0f) / 100.0f) * f3;
                this.mClipRect.set(f3 - interpolation, 0.0f, f3 + interpolation, (float) height);
                canvas2.saveLayerAlpha(this.mClipRect, 0, 0);
                z = true;
            } else {
                j = j3;
            }
            if (j == 0) {
                canvas2.drawColor(this.mColor1);
            } else if (f2 >= 0.0f && f2 < 25.0f) {
                canvas2.drawColor(this.mColor4);
            } else if (f2 >= 25.0f && f2 < 50.0f) {
                canvas2.drawColor(this.mColor1);
            } else if (f2 < 50.0f || f2 >= 75.0f) {
                canvas2.drawColor(this.mColor3);
            } else {
                canvas2.drawColor(this.mColor2);
            }
            if (f2 < 0.0f || f2 > 25.0f) {
                f = 0.0f;
            } else {
                Canvas canvas3 = canvas;
                drawCircle(canvas3, (float) i, (float) i2, this.mColor1, ((f2 + 25.0f) * 2.0f) / 100.0f);
                f = 0.0f;
            }
            if (f2 >= f && f2 <= 50.0f) {
                Canvas canvas4 = canvas;
                drawCircle(canvas4, (float) i, (float) i2, this.mColor2, (f2 * 2.0f) / 100.0f);
            }
            if (f2 >= 25.0f && f2 <= 75.0f) {
                Canvas canvas5 = canvas;
                drawCircle(canvas5, (float) i, (float) i2, this.mColor3, ((f2 - 25.0f) * 2.0f) / 100.0f);
            }
            if (f2 >= 50.0f && f2 <= 100.0f) {
                Canvas canvas6 = canvas;
                drawCircle(canvas6, (float) i, (float) i2, this.mColor4, ((f2 - 50.0f) * 2.0f) / 100.0f);
            }
            if (f2 >= 75.0f && f2 <= 100.0f) {
                Canvas canvas7 = canvas;
                drawCircle(canvas7, (float) i, (float) i2, this.mColor1, ((f2 - 75.0f) * 2.0f) / 100.0f);
            }
            if (this.mTriggerPercentage > 0.0f && z) {
                canvas2.restoreToCount(save);
                int save2 = canvas.save();
                canvas2.clipRect(this.mBounds);
                drawTrigger(canvas2, i, i2);
                save = save2;
            }
            ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
        } else {
            float f4 = this.mTriggerPercentage;
            if (f4 > 0.0f && ((double) f4) <= 1.0d) {
                drawTrigger(canvas2, i, i2);
            }
        }
        canvas2.restoreToCount(save);
    }

    private void drawTrigger(Canvas canvas, int i, int i2) {
        this.mPaint.setColor(this.mColor1);
        float f = (float) i;
        canvas.drawCircle(f, (float) i2, this.mTriggerPercentage * f, this.mPaint);
    }

    private void drawCircle(Canvas canvas, float f, float f2, int i, float f3) {
        this.mPaint.setColor(i);
        canvas.save();
        canvas.translate(f, f2);
        float interpolation = INTERPOLATOR.getInterpolation(f3);
        canvas.scale(interpolation, interpolation);
        canvas.drawCircle(0.0f, 0.0f, f, this.mPaint);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void setBounds(int i, int i2, int i3, int i4) {
        Rect rect = this.mBounds;
        rect.left = i;
        rect.top = i2;
        rect.right = i3;
        rect.bottom = i4;
    }
}
