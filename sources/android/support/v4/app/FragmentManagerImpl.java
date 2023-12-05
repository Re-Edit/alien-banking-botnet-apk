package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.os.BuildCompat;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final boolean HONEYCOMB = (Build.VERSION.SDK_INT >= 11);
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField = null;
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    private CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    /* compiled from: FragmentManager */
    interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    public static int reverseTransit(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i == 4099) {
            return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        }
        if (i != 8194) {
            return 0;
        }
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public static int transitToStyleIndex(int i, boolean z) {
        if (i == 4097) {
            return z ? 1 : 2;
        }
        if (i == 4099) {
            return z ? 5 : 6;
        }
        if (i != 8194) {
            return -1;
        }
        return z ? 3 : 4;
    }

    /* access modifiers changed from: package-private */
    public LayoutInflaterFactory getLayoutInflaterFactory() {
        return this;
    }

    FragmentManagerImpl() {
    }

    /* compiled from: FragmentManager */
    static class AnimateOnHWLayerIfNeededListener implements Animation.AnimationListener {
        private Animation.AnimationListener mOriginalListener;
        private boolean mShouldRunOnHWLayer;
        View mView;

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation) {
            if (view != null && animation != null) {
                this.mView = view;
            }
        }

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation, Animation.AnimationListener animationListener) {
            if (view != null && animation != null) {
                this.mOriginalListener = animationListener;
                this.mView = view;
                this.mShouldRunOnHWLayer = true;
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            Animation.AnimationListener animationListener = this.mOriginalListener;
            if (animationListener != null) {
                animationListener.onAnimationStart(animation);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            View view = this.mView;
            if (view != null && this.mShouldRunOnHWLayer) {
                if (ViewCompat.isAttachedToWindow(view) || BuildCompat.isAtLeastN()) {
                    this.mView.post(new Runnable() {
                        public void run() {
                            ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 0, (Paint) null);
                        }
                    });
                } else {
                    ViewCompat.setLayerType(this.mView, 0, (Paint) null);
                }
            }
            Animation.AnimationListener animationListener = this.mOriginalListener;
            if (animationListener != null) {
                animationListener.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            Animation.AnimationListener animationListener = this.mOriginalListener;
            if (animationListener != null) {
                animationListener.onAnimationRepeat(animation);
            }
        }
    }

    static boolean modifiesAlpha(Animation animation) {
        if (animation instanceof AlphaAnimation) {
            return true;
        }
        if (animation instanceof AnimationSet) {
            List<Animation> animations = ((AnimationSet) animation).getAnimations();
            for (int i = 0; i < animations.size(); i++) {
                if (animations.get(i) instanceof AlphaAnimation) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View view, Animation animation) {
        return Build.VERSION.SDK_INT >= 19 && ViewCompat.getLayerType(view) == 0 && ViewCompat.hasOverlappingRendering(view) && modifiesAlpha(animation);
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw runtimeException;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean execPendingActions = execPendingActions();
        forcePostponedTransactions();
        return execPendingActions;
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState((String) null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate((String) null, -1, 0);
    }

    public void popBackStack(String str, int i) {
        enqueueAction(new PopBackStackState(str, -1, i), false);
    }

    public boolean popBackStackImmediate(String str, int i) {
        checkStateLoss();
        return popBackStackImmediate(str, -1, i);
    }

    public void popBackStack(int i, int i2) {
        if (i >= 0) {
            enqueueAction(new PopBackStackState((String) null, i, i2), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        checkStateLoss();
        execPendingActions();
        if (i >= 0) {
            return popBackStackImmediate((String) null, i, i2);
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    private boolean popBackStackImmediate(String str, int i, int i2) {
        execPendingActions();
        ensureExecReady(true);
        boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
        if (popBackStackState) {
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        return popBackStackState;
    }

    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public FragmentManager.BackStackEntry getBackStackEntryAt(int i) {
        return this.mBackStack.get(i);
    }

    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<FragmentManager.OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    public void putFragment(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    public Fragment getFragment(Bundle bundle, String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        if (i >= this.mActive.size()) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        Fragment fragment = this.mActive.get(i);
        if (fragment == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        return fragment;
    }

    public List<Fragment> getFragments() {
        return this.mActive;
    }

    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        Bundle saveFragmentBasicState;
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        if (fragment.mState <= 0 || (saveFragmentBasicState = saveFragmentBasicState(fragment)) == null) {
            return null;
        }
        return new Fragment.SavedState(saveFragmentBasicState);
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            DebugUtils.buildShortClassTag(fragment, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        int size6;
        String str2 = str + "    ";
        ArrayList<Fragment> arrayList = this.mActive;
        if (arrayList != null && (size6 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size6; i++) {
                Fragment fragment = this.mActive.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment);
                if (fragment != null) {
                    fragment.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        ArrayList<Fragment> arrayList2 = this.mAdded;
        if (arrayList2 != null && (size5 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size5; i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mAdded.get(i2).toString());
            }
        }
        ArrayList<Fragment> arrayList3 = this.mCreatedMenus;
        if (arrayList3 != null && (size4 = arrayList3.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(this.mCreatedMenus.get(i3).toString());
            }
        }
        ArrayList<BackStackRecord> arrayList4 = this.mBackStack;
        if (arrayList4 != null && (size3 = arrayList4.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(this.mBackStackIndices.get(i5));
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        ArrayList<OpGenerator> arrayList5 = this.mPendingActions;
        if (arrayList5 != null && (size = arrayList5.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(this.mPendingActions.get(i6));
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
        ArrayList<Integer> arrayList6 = this.mAvailIndices;
        if (arrayList6 != null && arrayList6.size() > 0) {
            printWriter.print(str);
            printWriter.print("  mAvailIndices: ");
            printWriter.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    static Animation makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    static Animation makeFadeAnimation(Context context, float f, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220);
        return alphaAnimation;
    }

    /* access modifiers changed from: package-private */
    public Animation loadAnimation(Fragment fragment, int i, boolean z, int i2) {
        int transitToStyleIndex;
        Animation loadAnimation;
        Animation onCreateAnimation = fragment.onCreateAnimation(i, z, fragment.getNextAnim());
        if (onCreateAnimation != null) {
            return onCreateAnimation;
        }
        if (fragment.getNextAnim() != 0 && (loadAnimation = AnimationUtils.loadAnimation(this.mHost.getContext(), fragment.getNextAnim())) != null) {
            return loadAnimation;
        }
        if (i == 0 || (transitToStyleIndex = transitToStyleIndex(i, z)) < 0) {
            return null;
        }
        switch (transitToStyleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if (i2 == 0 && this.mHost.onHasWindowAnimations()) {
                    i2 = this.mHost.onGetWindowAnimations();
                }
                return i2 == 0 ? null : null;
        }
    }

    public void performPendingDeferredStart(Fragment fragment) {
        if (!fragment.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        fragment.mDeferStart = false;
        moveToState(fragment, this.mCurState, 0, 0, false);
    }

    private void setHWLayerAnimListenerIfAlpha(View view, Animation animation) {
        Animation.AnimationListener animationListener;
        if (view != null && animation != null && shouldRunOnHWLayer(view, animation)) {
            try {
                if (sAnimationListenerField == null) {
                    sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                    sAnimationListenerField.setAccessible(true);
                }
                animationListener = (Animation.AnimationListener) sAnimationListenerField.get(animation);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "No field with the name mListener is found in Animation class", e);
                animationListener = null;
                ViewCompat.setLayerType(view, 2, (Paint) null);
                animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, animation, animationListener));
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Cannot access Animation's mListener field", e2);
                animationListener = null;
                ViewCompat.setLayerType(view, 2, (Paint) null);
                animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, animation, animationListener));
            }
            ViewCompat.setLayerType(view, 2, (Paint) null);
            animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, animation, animationListener));
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isStateAtLeast(int i) {
        return this.mCurState >= i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: int} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0303, code lost:
        if (r11 >= 4) goto L_0x0325;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0307, code lost:
        if (DEBUG == false) goto L_0x031f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0309, code lost:
        android.util.Log.v(TAG, "movefrom STARTED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x031f, code lost:
        r17.performStop();
        dispatchOnFragmentStopped(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0325, code lost:
        if (r11 >= 3) goto L_0x0344;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0329, code lost:
        if (DEBUG == false) goto L_0x0341;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x032b, code lost:
        android.util.Log.v(TAG, "movefrom STOPPED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0341, code lost:
        r17.performReallyStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0344, code lost:
        if (r11 >= 2) goto L_0x03ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0348, code lost:
        if (DEBUG == false) goto L_0x0360;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x034a, code lost:
        android.util.Log.v(TAG, "movefrom ACTIVITY_CREATED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0362, code lost:
        if (r7.mView == null) goto L_0x0373;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x036a, code lost:
        if (r6.mHost.onShouldSaveFragmentState(r7) == false) goto L_0x0373;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x036e, code lost:
        if (r7.mSavedViewState != null) goto L_0x0373;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0370, code lost:
        saveFragmentViewState(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0373, code lost:
        r17.performDestroyView();
        dispatchOnFragmentViewDestroyed(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x037b, code lost:
        if (r7.mView == null) goto L_0x03c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x037f, code lost:
        if (r7.mContainer == null) goto L_0x03c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0384, code lost:
        if (r6.mCurState <= 0) goto L_0x03a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0388, code lost:
        if (r6.mDestroyed != false) goto L_0x03a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0390, code lost:
        if (r7.mView.getVisibility() != 0) goto L_0x03a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0396, code lost:
        if (r7.mPostponedAlpha < 0.0f) goto L_0x03a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0398, code lost:
        r0 = loadAnimation(r7, r19, false, r20);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x03a1, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x03a2, code lost:
        r7.mPostponedAlpha = 0.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x03a4, code lost:
        if (r0 == null) goto L_0x03bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x03a6, code lost:
        r7.setAnimatingAway(r7.mView);
        r7.setStateAfterAnimating(r11);
        r0.setAnimationListener(new android.support.v4.app.FragmentManagerImpl.AnonymousClass2(r6, r7.mView, r0));
        r7.mView.startAnimation(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x03bd, code lost:
        r7.mContainer.removeView(r7.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x03c4, code lost:
        r7.mContainer = null;
        r7.mView = null;
        r7.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x03ca, code lost:
        if (r11 >= 1) goto L_0x0427;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x03ce, code lost:
        if (r6.mDestroyed == false) goto L_0x03e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x03d4, code lost:
        if (r17.getAnimatingAway() == null) goto L_0x03e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x03d6, code lost:
        r0 = r17.getAnimatingAway();
        r7.setAnimatingAway((android.view.View) null);
        r0.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x03e4, code lost:
        if (r17.getAnimatingAway() == null) goto L_0x03ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x03e6, code lost:
        r7.setStateAfterAnimating(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x03ec, code lost:
        if (DEBUG == false) goto L_0x0404;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x03ee, code lost:
        android.util.Log.v(TAG, "movefrom CREATED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x0406, code lost:
        if (r7.mRetaining != false) goto L_0x040f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0408, code lost:
        r17.performDestroy();
        dispatchOnFragmentDestroyed(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x040f, code lost:
        r7.mState = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x0411, code lost:
        r17.performDetach();
        dispatchOnFragmentDetached(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x0417, code lost:
        if (r21 != false) goto L_0x0427;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x041b, code lost:
        if (r7.mRetaining != false) goto L_0x0421;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x041d, code lost:
        makeInactive(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0421, code lost:
        r7.mHost = null;
        r7.mParentFragment = null;
        r7.mFragmentManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0165, code lost:
        if (r11 <= 1) goto L_0x026a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0169, code lost:
        if (DEBUG == false) goto L_0x0181;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x016b, code lost:
        android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0183, code lost:
        if (r7.mFromLayout != false) goto L_0x0255;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0187, code lost:
        if (r7.mContainerId == 0) goto L_0x01f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x018c, code lost:
        if (r7.mContainerId != -1) goto L_0x01ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x018e, code lost:
        throwException(new java.lang.IllegalArgumentException("Cannot create fragment " + r7 + " for a container view with no id"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01ac, code lost:
        r0 = (android.view.ViewGroup) r6.mContainer.onFindViewById(r7.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01b6, code lost:
        if (r0 != null) goto L_0x01fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ba, code lost:
        if (r7.mRestored != false) goto L_0x01fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
        r3 = r17.getResources().getResourceName(r7.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01c7, code lost:
        r3 = android.support.v4.os.EnvironmentCompat.MEDIA_UNKNOWN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01f9, code lost:
        r0 = null;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x042c  */
    /* JADX WARNING: Removed duplicated region for block: B:208:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(android.support.v4.app.Fragment r17, int r18, int r19, int r20, boolean r21) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            boolean r0 = r7.mAdded
            r8 = 1
            if (r0 == 0) goto L_0x0011
            boolean r0 = r7.mDetached
            if (r0 == 0) goto L_0x000e
            goto L_0x0011
        L_0x000e:
            r0 = r18
            goto L_0x0016
        L_0x0011:
            r0 = r18
            if (r0 <= r8) goto L_0x0016
            r0 = 1
        L_0x0016:
            boolean r1 = r7.mRemoving
            if (r1 == 0) goto L_0x0020
            int r1 = r7.mState
            if (r0 <= r1) goto L_0x0020
            int r0 = r7.mState
        L_0x0020:
            boolean r1 = r7.mDeferStart
            r9 = 4
            r10 = 3
            if (r1 == 0) goto L_0x002e
            int r1 = r7.mState
            if (r1 >= r9) goto L_0x002e
            if (r0 <= r10) goto L_0x002e
            r11 = 3
            goto L_0x002f
        L_0x002e:
            r11 = r0
        L_0x002f:
            int r0 = r7.mState
            r12 = 2
            r13 = 0
            r14 = 0
            if (r0 >= r11) goto L_0x02d5
            boolean r0 = r7.mFromLayout
            if (r0 == 0) goto L_0x003f
            boolean r0 = r7.mInLayout
            if (r0 != 0) goto L_0x003f
            return
        L_0x003f:
            android.view.View r0 = r17.getAnimatingAway()
            if (r0 == 0) goto L_0x0056
            r7.setAnimatingAway(r13)
            int r2 = r17.getStateAfterAnimating()
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r16
            r1 = r17
            r0.moveToState(r1, r2, r3, r4, r5)
        L_0x0056:
            int r0 = r7.mState
            r1 = 8
            r2 = 11
            switch(r0) {
                case 0: goto L_0x0062;
                case 1: goto L_0x0165;
                case 2: goto L_0x026a;
                case 3: goto L_0x026e;
                case 4: goto L_0x0290;
                default: goto L_0x005f;
            }
        L_0x005f:
            r8 = r11
            goto L_0x0428
        L_0x0062:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x007c
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "moveto CREATED: "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r0, r3)
        L_0x007c:
            android.os.Bundle r0 = r7.mSavedFragmentState
            if (r0 == 0) goto L_0x00c4
            android.os.Bundle r0 = r7.mSavedFragmentState
            android.support.v4.app.FragmentHostCallback r3 = r6.mHost
            android.content.Context r3 = r3.getContext()
            java.lang.ClassLoader r3 = r3.getClassLoader()
            r0.setClassLoader(r3)
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r3 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r3)
            r7.mSavedViewState = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r3 = "android:target_state"
            android.support.v4.app.Fragment r0 = r6.getFragment(r0, r3)
            r7.mTarget = r0
            android.support.v4.app.Fragment r0 = r7.mTarget
            if (r0 == 0) goto L_0x00b1
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r3 = "android:target_req_state"
            int r0 = r0.getInt(r3, r14)
            r7.mTargetRequestCode = r0
        L_0x00b1:
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r3 = "android:user_visible_hint"
            boolean r0 = r0.getBoolean(r3, r8)
            r7.mUserVisibleHint = r0
            boolean r0 = r7.mUserVisibleHint
            if (r0 != 0) goto L_0x00c4
            r7.mDeferStart = r8
            if (r11 <= r10) goto L_0x00c4
            r11 = 3
        L_0x00c4:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r7.mHost = r0
            android.support.v4.app.Fragment r3 = r6.mParent
            r7.mParentFragment = r3
            if (r3 == 0) goto L_0x00d1
            android.support.v4.app.FragmentManagerImpl r0 = r3.mChildFragmentManager
            goto L_0x00d5
        L_0x00d1:
            android.support.v4.app.FragmentManagerImpl r0 = r0.getFragmentManagerImpl()
        L_0x00d5:
            r7.mFragmentManager = r0
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            android.content.Context r0 = r0.getContext()
            r6.dispatchOnFragmentPreAttached(r7, r0, r14)
            r7.mCalled = r14
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            android.content.Context r0 = r0.getContext()
            r7.onAttach((android.content.Context) r0)
            boolean r0 = r7.mCalled
            if (r0 == 0) goto L_0x02b9
            android.support.v4.app.Fragment r0 = r7.mParentFragment
            if (r0 != 0) goto L_0x00f9
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r0.onAttachFragment(r7)
            goto L_0x00fe
        L_0x00f9:
            android.support.v4.app.Fragment r0 = r7.mParentFragment
            r0.onAttachFragment(r7)
        L_0x00fe:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            android.content.Context r0 = r0.getContext()
            r6.dispatchOnFragmentAttached(r7, r0, r14)
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x0116
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.performCreate(r0)
            android.os.Bundle r0 = r7.mSavedFragmentState
            r6.dispatchOnFragmentCreated(r7, r0, r14)
            goto L_0x011d
        L_0x0116:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.restoreChildFragmentState(r0)
            r7.mState = r8
        L_0x011d:
            r7.mRetaining = r14
            boolean r0 = r7.mFromLayout
            if (r0 == 0) goto L_0x0165
            android.os.Bundle r0 = r7.mSavedFragmentState
            android.view.LayoutInflater r0 = r7.getLayoutInflater(r0)
            android.os.Bundle r3 = r7.mSavedFragmentState
            android.view.View r0 = r7.performCreateView(r0, r13, r3)
            r7.mView = r0
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x0163
            android.view.View r0 = r7.mView
            r7.mInnerView = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L_0x0143
            android.view.View r0 = r7.mView
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r0, r14)
            goto L_0x014b
        L_0x0143:
            android.view.View r0 = r7.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r7.mView = r0
        L_0x014b:
            boolean r0 = r7.mHidden
            if (r0 == 0) goto L_0x0154
            android.view.View r0 = r7.mView
            r0.setVisibility(r1)
        L_0x0154:
            android.view.View r0 = r7.mView
            android.os.Bundle r3 = r7.mSavedFragmentState
            r7.onViewCreated(r0, r3)
            android.view.View r0 = r7.mView
            android.os.Bundle r3 = r7.mSavedFragmentState
            r6.dispatchOnFragmentViewCreated(r7, r0, r3, r14)
            goto L_0x0165
        L_0x0163:
            r7.mInnerView = r13
        L_0x0165:
            if (r11 <= r8) goto L_0x026a
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0181
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "moveto ACTIVITY_CREATED: "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r0, r3)
        L_0x0181:
            boolean r0 = r7.mFromLayout
            if (r0 != 0) goto L_0x0255
            int r0 = r7.mContainerId
            if (r0 == 0) goto L_0x01f9
            int r0 = r7.mContainerId
            r3 = -1
            if (r0 != r3) goto L_0x01ac
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Cannot create fragment "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r4 = " for a container view with no id"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            r6.throwException(r0)
        L_0x01ac:
            android.support.v4.app.FragmentContainer r0 = r6.mContainer
            int r3 = r7.mContainerId
            android.view.View r0 = r0.onFindViewById(r3)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x01fa
            boolean r3 = r7.mRestored
            if (r3 != 0) goto L_0x01fa
            android.content.res.Resources r3 = r17.getResources()     // Catch:{ NotFoundException -> 0x01c7 }
            int r4 = r7.mContainerId     // Catch:{ NotFoundException -> 0x01c7 }
            java.lang.String r3 = r3.getResourceName(r4)     // Catch:{ NotFoundException -> 0x01c7 }
            goto L_0x01c9
        L_0x01c7:
            java.lang.String r3 = "unknown"
        L_0x01c9:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r15 = "No view found for id 0x"
            r5.append(r15)
            int r15 = r7.mContainerId
            java.lang.String r15 = java.lang.Integer.toHexString(r15)
            r5.append(r15)
            java.lang.String r15 = " ("
            r5.append(r15)
            r5.append(r3)
            java.lang.String r3 = ") for fragment "
            r5.append(r3)
            r5.append(r7)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            r6.throwException(r4)
            goto L_0x01fa
        L_0x01f9:
            r0 = r13
        L_0x01fa:
            r7.mContainer = r0
            android.os.Bundle r3 = r7.mSavedFragmentState
            android.view.LayoutInflater r3 = r7.getLayoutInflater(r3)
            android.os.Bundle r4 = r7.mSavedFragmentState
            android.view.View r3 = r7.performCreateView(r3, r0, r4)
            r7.mView = r3
            android.view.View r3 = r7.mView
            if (r3 == 0) goto L_0x0253
            android.view.View r3 = r7.mView
            r7.mInnerView = r3
            int r3 = android.os.Build.VERSION.SDK_INT
            if (r3 < r2) goto L_0x021c
            android.view.View r2 = r7.mView
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r2, r14)
            goto L_0x0224
        L_0x021c:
            android.view.View r2 = r7.mView
            android.view.ViewGroup r2 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r2)
            r7.mView = r2
        L_0x0224:
            if (r0 == 0) goto L_0x022b
            android.view.View r2 = r7.mView
            r0.addView(r2)
        L_0x022b:
            boolean r0 = r7.mHidden
            if (r0 == 0) goto L_0x0234
            android.view.View r0 = r7.mView
            r0.setVisibility(r1)
        L_0x0234:
            android.view.View r0 = r7.mView
            android.os.Bundle r1 = r7.mSavedFragmentState
            r7.onViewCreated(r0, r1)
            android.view.View r0 = r7.mView
            android.os.Bundle r1 = r7.mSavedFragmentState
            r6.dispatchOnFragmentViewCreated(r7, r0, r1, r14)
            android.view.View r0 = r7.mView
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x024f
            android.view.ViewGroup r0 = r7.mContainer
            if (r0 == 0) goto L_0x024f
            goto L_0x0250
        L_0x024f:
            r8 = 0
        L_0x0250:
            r7.mIsNewlyAdded = r8
            goto L_0x0255
        L_0x0253:
            r7.mInnerView = r13
        L_0x0255:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.performActivityCreated(r0)
            android.os.Bundle r0 = r7.mSavedFragmentState
            r6.dispatchOnFragmentActivityCreated(r7, r0, r14)
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x0268
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.restoreViewState(r0)
        L_0x0268:
            r7.mSavedFragmentState = r13
        L_0x026a:
            if (r11 <= r12) goto L_0x026e
            r7.mState = r10
        L_0x026e:
            if (r11 <= r10) goto L_0x0290
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x028a
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto STARTED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x028a:
            r17.performStart()
            r6.dispatchOnFragmentStarted(r7, r14)
        L_0x0290:
            if (r11 <= r9) goto L_0x02b6
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02ac
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto RESUMED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02ac:
            r17.performResume()
            r6.dispatchOnFragmentResumed(r7, r14)
            r7.mSavedFragmentState = r13
            r7.mSavedViewState = r13
        L_0x02b6:
            r8 = r11
            goto L_0x0428
        L_0x02b9:
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " did not call through to super.onAttach()"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x02d5:
            int r0 = r7.mState
            if (r0 <= r11) goto L_0x0427
            int r0 = r7.mState
            switch(r0) {
                case 1: goto L_0x03ca;
                case 2: goto L_0x0344;
                case 3: goto L_0x0325;
                case 4: goto L_0x0303;
                case 5: goto L_0x02e0;
                default: goto L_0x02de;
            }
        L_0x02de:
            goto L_0x0427
        L_0x02e0:
            r0 = 5
            if (r11 >= r0) goto L_0x0303
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02fd
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom RESUMED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02fd:
            r17.performPause()
            r6.dispatchOnFragmentPaused(r7, r14)
        L_0x0303:
            if (r11 >= r9) goto L_0x0325
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x031f
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STARTED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x031f:
            r17.performStop()
            r6.dispatchOnFragmentStopped(r7, r14)
        L_0x0325:
            if (r11 >= r10) goto L_0x0344
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0341
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STOPPED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0341:
            r17.performReallyStop()
        L_0x0344:
            if (r11 >= r12) goto L_0x03ca
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0360
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom ACTIVITY_CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0360:
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x0373
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            boolean r0 = r0.onShouldSaveFragmentState(r7)
            if (r0 == 0) goto L_0x0373
            android.util.SparseArray<android.os.Parcelable> r0 = r7.mSavedViewState
            if (r0 != 0) goto L_0x0373
            r16.saveFragmentViewState(r17)
        L_0x0373:
            r17.performDestroyView()
            r6.dispatchOnFragmentViewDestroyed(r7, r14)
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x03c4
            android.view.ViewGroup r0 = r7.mContainer
            if (r0 == 0) goto L_0x03c4
            int r0 = r6.mCurState
            r1 = 0
            if (r0 <= 0) goto L_0x03a1
            boolean r0 = r6.mDestroyed
            if (r0 != 0) goto L_0x03a1
            android.view.View r0 = r7.mView
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x03a1
            float r0 = r7.mPostponedAlpha
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 < 0) goto L_0x03a1
            r0 = r19
            r2 = r20
            android.view.animation.Animation r0 = r6.loadAnimation(r7, r0, r14, r2)
            goto L_0x03a2
        L_0x03a1:
            r0 = r13
        L_0x03a2:
            r7.mPostponedAlpha = r1
            if (r0 == 0) goto L_0x03bd
            android.view.View r1 = r7.mView
            r7.setAnimatingAway(r1)
            r7.setStateAfterAnimating(r11)
            android.view.View r1 = r7.mView
            android.support.v4.app.FragmentManagerImpl$2 r2 = new android.support.v4.app.FragmentManagerImpl$2
            r2.<init>(r1, r0, r7)
            r0.setAnimationListener(r2)
            android.view.View r1 = r7.mView
            r1.startAnimation(r0)
        L_0x03bd:
            android.view.ViewGroup r0 = r7.mContainer
            android.view.View r1 = r7.mView
            r0.removeView(r1)
        L_0x03c4:
            r7.mContainer = r13
            r7.mView = r13
            r7.mInnerView = r13
        L_0x03ca:
            if (r11 >= r8) goto L_0x0427
            boolean r0 = r6.mDestroyed
            if (r0 == 0) goto L_0x03e0
            android.view.View r0 = r17.getAnimatingAway()
            if (r0 == 0) goto L_0x03e0
            android.view.View r0 = r17.getAnimatingAway()
            r7.setAnimatingAway(r13)
            r0.clearAnimation()
        L_0x03e0:
            android.view.View r0 = r17.getAnimatingAway()
            if (r0 == 0) goto L_0x03ea
            r7.setStateAfterAnimating(r11)
            goto L_0x0428
        L_0x03ea:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0404
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0404:
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x040f
            r17.performDestroy()
            r6.dispatchOnFragmentDestroyed(r7, r14)
            goto L_0x0411
        L_0x040f:
            r7.mState = r14
        L_0x0411:
            r17.performDetach()
            r6.dispatchOnFragmentDetached(r7, r14)
            if (r21 != 0) goto L_0x0427
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x0421
            r16.makeInactive(r17)
            goto L_0x0427
        L_0x0421:
            r7.mHost = r13
            r7.mParentFragment = r13
            r7.mFragmentManager = r13
        L_0x0427:
            r8 = r11
        L_0x0428:
            int r0 = r7.mState
            if (r0 == r8) goto L_0x045b
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveToState: Fragment state for "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " not updated inline; "
            r1.append(r2)
            java.lang.String r2 = "expected state "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r2 = " found "
            r1.append(r2)
            int r2 = r7.mState
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.w(r0, r1)
            r7.mState = r8
        L_0x045b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public void moveToState(Fragment fragment) {
        moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /* access modifiers changed from: package-private */
    public void completeShowHideFragment(Fragment fragment) {
        if (fragment.mView != null) {
            Animation loadAnimation = loadAnimation(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle());
            if (loadAnimation != null) {
                setHWLayerAnimListenerIfAlpha(fragment.mView, loadAnimation);
                fragment.mView.startAnimation(loadAnimation);
                setHWLayerAnimListenerIfAlpha(fragment.mView, loadAnimation);
                loadAnimation.start();
            }
            fragment.mView.setVisibility((!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8);
            if (fragment.isHideReplaced()) {
                fragment.setHideReplaced(false);
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r0 = r0.mView;
        r1 = r11.mContainer;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveFragmentToExpectedState(android.support.v4.app.Fragment r11) {
        /*
            r10 = this;
            if (r11 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r10.mCurState
            boolean r1 = r11.mRemoving
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x001d
            boolean r1 = r11.isInBackStack()
            if (r1 == 0) goto L_0x0017
            int r0 = java.lang.Math.min(r0, r2)
            r6 = r0
            goto L_0x001e
        L_0x0017:
            int r0 = java.lang.Math.min(r0, r3)
            r6 = r0
            goto L_0x001e
        L_0x001d:
            r6 = r0
        L_0x001e:
            int r7 = r11.getNextTransition()
            int r8 = r11.getNextTransitionStyle()
            r9 = 0
            r4 = r10
            r5 = r11
            r4.moveToState(r5, r6, r7, r8, r9)
            android.view.View r0 = r11.mView
            if (r0 == 0) goto L_0x008c
            android.support.v4.app.Fragment r0 = r10.findFragmentUnder(r11)
            if (r0 == 0) goto L_0x004e
            android.view.View r0 = r0.mView
            android.view.ViewGroup r1 = r11.mContainer
            int r0 = r1.indexOfChild(r0)
            android.view.View r4 = r11.mView
            int r4 = r1.indexOfChild(r4)
            if (r4 >= r0) goto L_0x004e
            r1.removeViewAt(r4)
            android.view.View r4 = r11.mView
            r1.addView(r4, r0)
        L_0x004e:
            boolean r0 = r11.mIsNewlyAdded
            if (r0 == 0) goto L_0x008c
            android.view.ViewGroup r0 = r11.mContainer
            if (r0 == 0) goto L_0x008c
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            r4 = 0
            if (r0 >= r1) goto L_0x0063
            android.view.View r0 = r11.mView
            r0.setVisibility(r3)
            goto L_0x0070
        L_0x0063:
            float r0 = r11.mPostponedAlpha
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0070
            android.view.View r0 = r11.mView
            float r1 = r11.mPostponedAlpha
            r0.setAlpha(r1)
        L_0x0070:
            r11.mPostponedAlpha = r4
            r11.mIsNewlyAdded = r3
            int r0 = r11.getNextTransition()
            int r1 = r11.getNextTransitionStyle()
            android.view.animation.Animation r0 = r10.loadAnimation(r11, r0, r2, r1)
            if (r0 == 0) goto L_0x008c
            android.view.View r1 = r11.mView
            r10.setHWLayerAnimListenerIfAlpha(r1, r0)
            android.view.View r1 = r11.mView
            r1.startAnimation(r0)
        L_0x008c:
            boolean r0 = r11.mHiddenChanged
            if (r0 == 0) goto L_0x0093
            r10.completeShowHideFragment(r11)
        L_0x0093:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveFragmentToExpectedState(android.support.v4.app.Fragment):void");
    }

    /* access modifiers changed from: package-private */
    public void moveToState(int i, boolean z) {
        boolean z2;
        FragmentHostCallback fragmentHostCallback;
        if (this.mHost == null && i != 0) {
            throw new IllegalStateException("No activity");
        } else if (z || i != this.mCurState) {
            this.mCurState = i;
            if (this.mActive != null) {
                ArrayList<Fragment> arrayList = this.mAdded;
                if (arrayList != null) {
                    int size = arrayList.size();
                    z2 = false;
                    for (int i2 = 0; i2 < size; i2++) {
                        Fragment fragment = this.mAdded.get(i2);
                        moveFragmentToExpectedState(fragment);
                        if (fragment.mLoaderManager != null) {
                            z2 |= fragment.mLoaderManager.hasRunningLoaders();
                        }
                    }
                } else {
                    z2 = false;
                }
                int size2 = this.mActive.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Fragment fragment2 = this.mActive.get(i3);
                    if (fragment2 != null && ((fragment2.mRemoving || fragment2.mDetached) && !fragment2.mIsNewlyAdded)) {
                        moveFragmentToExpectedState(fragment2);
                        if (fragment2.mLoaderManager != null) {
                            z2 |= fragment2.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                if (!z2) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 5) {
                    fragmentHostCallback.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = this.mActive.get(i);
                if (fragment != null) {
                    performPendingDeferredStart(fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void makeActive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            ArrayList<Integer> arrayList = this.mAvailIndices;
            if (arrayList == null || arrayList.size() <= 0) {
                if (this.mActive == null) {
                    this.mActive = new ArrayList<>();
                }
                fragment.setIndex(this.mActive.size(), this.mParent);
                this.mActive.add(fragment);
            } else {
                ArrayList<Integer> arrayList2 = this.mAvailIndices;
                fragment.setIndex(arrayList2.remove(arrayList2.size() - 1).intValue(), this.mParent);
                this.mActive.set(fragment.mIndex, fragment);
            }
            if (DEBUG) {
                Log.v(TAG, "Allocated fragment index " + fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void makeInactive(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            if (DEBUG) {
                Log.v(TAG, "Freeing fragment index " + fragment);
            }
            this.mActive.set(fragment.mIndex, (Object) null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList<>();
            }
            this.mAvailIndices.add(Integer.valueOf(fragment.mIndex));
            this.mHost.inactivateFragment(fragment.mWho);
            fragment.initState();
        }
    }

    public void addFragment(Fragment fragment, boolean z) {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList<>();
        }
        if (DEBUG) {
            Log.v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (fragment.mDetached) {
            return;
        }
        if (!this.mAdded.contains(fragment)) {
            this.mAdded.add(fragment);
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if (z) {
                moveToState(fragment);
                return;
            }
            return;
        }
        throw new IllegalStateException("Fragment already added: " + fragment);
    }

    public void removeFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            ArrayList<Fragment> arrayList = this.mAdded;
            if (arrayList != null) {
                arrayList.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    public void hideFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    public void showFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public void detachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        Log.v(TAG, "remove from detach: " + fragment);
                    }
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    public void attachFragment(Fragment fragment) {
        if (DEBUG) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList<>();
                }
                if (!this.mAdded.contains(fragment)) {
                    if (DEBUG) {
                        Log.v(TAG, "add from attach: " + fragment);
                    }
                    this.mAdded.add(fragment);
                    fragment.mAdded = true;
                    if (fragment.mHasMenu && fragment.mMenuVisible) {
                        this.mNeedMenuInvalidate = true;
                        return;
                    }
                    return;
                }
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
        }
    }

    public Fragment findFragmentById(int i) {
        ArrayList<Fragment> arrayList = this.mAdded;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && fragment.mFragmentId == i) {
                    return fragment;
                }
            }
        }
        ArrayList<Fragment> arrayList2 = this.mActive;
        if (arrayList2 == null) {
            return null;
        }
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            Fragment fragment2 = this.mActive.get(size2);
            if (fragment2 != null && fragment2.mFragmentId == i) {
                return fragment2;
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String str) {
        ArrayList<Fragment> arrayList = this.mAdded;
        if (!(arrayList == null || str == null)) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        ArrayList<Fragment> arrayList2 = this.mActive;
        if (arrayList2 == null || str == null) {
            return null;
        }
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            Fragment fragment2 = this.mActive.get(size2);
            if (fragment2 != null && str.equals(fragment2.mTag)) {
                return fragment2;
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String str) {
        Fragment findFragmentByWho;
        ArrayList<Fragment> arrayList = this.mActive;
        if (arrayList == null || str == null) {
            return null;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Fragment fragment = this.mActive.get(size);
            if (fragment != null && (findFragmentByWho = fragment.findFragmentByWho(str)) != null) {
                return findFragmentByWho;
            }
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    public void enqueueAction(OpGenerator opGenerator, boolean z) {
        if (!z) {
            checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList<>();
            }
            this.mPendingActions.add(opGenerator);
            scheduleCommit();
        }
    }

    /* access modifiers changed from: private */
    public void scheduleCommit() {
        synchronized (this) {
            boolean z = false;
            boolean z2 = this.mPostponedTransactions != null && !this.mPostponedTransactions.isEmpty();
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                z = true;
            }
            if (z2 || z) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null) {
                if (this.mAvailBackStackIndices.size() > 0) {
                    int intValue = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
                    if (DEBUG) {
                        Log.v(TAG, "Adding back stack index " + intValue + " with " + backStackRecord);
                    }
                    this.mBackStackIndices.set(intValue, backStackRecord);
                    return intValue;
                }
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (DEBUG) {
                Log.v(TAG, "Setting back stack index " + size + " to " + backStackRecord);
            }
            this.mBackStackIndices.add(backStackRecord);
            return size;
        }
    }

    public void setBackStackIndex(int i, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (i < size) {
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + i + " to " + backStackRecord);
                }
                this.mBackStackIndices.set(i, backStackRecord);
            } else {
                while (size < i) {
                    this.mBackStackIndices.add((Object) null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Adding available back stack index " + size);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(size));
                    size++;
                }
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + i + " with " + backStackRecord);
                }
                this.mBackStackIndices.add(backStackRecord);
            }
        }
    }

    public void freeBackStackIndex(int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, (Object) null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + i);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(i));
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!z) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList<>();
                this.mTmpIsPop = new ArrayList<>();
            }
            executePostponedTransaction((ArrayList<BackStackRecord>) null, (ArrayList<Boolean>) null);
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public void execSingleAction(OpGenerator opGenerator, boolean z) {
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: finally extract failed */
    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean z = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                z = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        doPendingDeferredStart();
        return z;
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int indexOf;
        int indexOf2;
        ArrayList<StartEnterTransitionListener> arrayList3 = this.mPostponedTransactions;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i = 0;
        while (i < size) {
            StartEnterTransitionListener startEnterTransitionListener = this.mPostponedTransactions.get(i);
            if (arrayList != null && !startEnterTransitionListener.mIsBack && (indexOf2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(indexOf2).booleanValue()) {
                startEnterTransitionListener.cancelTransaction();
            } else if (startEnterTransitionListener.isReady() || (arrayList != null && startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                size--;
                if (arrayList == null || startEnterTransitionListener.mIsBack || (indexOf = arrayList.indexOf(startEnterTransitionListener.mRecord)) == -1 || !arrayList2.get(indexOf).booleanValue()) {
                    startEnterTransitionListener.completeTransaction();
                } else {
                    startEnterTransitionListener.cancelTransaction();
                }
            }
            i++;
        }
    }

    private void optimizeAndExecuteOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                throw new IllegalStateException("Internal error with the back stack records");
            }
            executePostponedTransaction(arrayList, arrayList2);
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                if (!arrayList.get(i).mAllowOptimization) {
                    if (i2 != i) {
                        executeOpsTogether(arrayList, arrayList2, i2, i);
                    }
                    i2 = i + 1;
                    if (arrayList2.get(i).booleanValue()) {
                        while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mAllowOptimization) {
                            i2++;
                        }
                    }
                    executeOpsTogether(arrayList, arrayList2, i, i2);
                    i = i2 - 1;
                }
                i++;
            }
            if (i2 != size) {
                executeOpsTogether(arrayList, arrayList2, i2, size);
            }
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        int i3;
        ArrayList<BackStackRecord> arrayList3 = arrayList;
        ArrayList<Boolean> arrayList4 = arrayList2;
        int i4 = i;
        int i5 = i2;
        boolean z = arrayList3.get(i4).mAllowOptimization;
        ArrayList<Fragment> arrayList5 = this.mTmpAddedFragments;
        if (arrayList5 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList5.clear();
        }
        ArrayList<Fragment> arrayList6 = this.mAdded;
        if (arrayList6 != null) {
            this.mTmpAddedFragments.addAll(arrayList6);
        }
        int i6 = i4;
        boolean z2 = false;
        while (true) {
            int i7 = -1;
            if (i6 >= i5) {
                break;
            }
            BackStackRecord backStackRecord = arrayList3.get(i6);
            boolean booleanValue = arrayList4.get(i6).booleanValue();
            if (!booleanValue) {
                backStackRecord.expandReplaceOps(this.mTmpAddedFragments);
            } else {
                backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments);
            }
            if (!booleanValue) {
                i7 = 1;
            }
            backStackRecord.bumpBackStackNesting(i7);
            z2 = z2 || backStackRecord.mAddToBackStack;
            i6++;
        }
        this.mTmpAddedFragments.clear();
        if (!z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i2, false);
        }
        executeOps(arrayList, arrayList2, i, i2);
        if (z) {
            ArraySet arraySet = new ArraySet();
            addAddedFragments(arraySet);
            int postponePostponableTransactions = postponePostponableTransactions(arrayList, arrayList2, i, i2, arraySet);
            makeRemovedFragmentsInvisible(arraySet);
            i3 = postponePostponableTransactions;
        } else {
            i3 = i5;
        }
        if (i3 != i4 && z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i3, true);
            moveToState(this.mCurState, true);
        }
        while (i4 < i5) {
            BackStackRecord backStackRecord2 = arrayList3.get(i4);
            if (arrayList4.get(i4).booleanValue() && backStackRecord2.mIndex >= 0) {
                freeBackStackIndex(backStackRecord2.mIndex);
                backStackRecord2.mIndex = -1;
            }
            i4++;
        }
        if (z2) {
            reportBackStackChanged();
        }
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            Fragment valueAt = arraySet.valueAt(i);
            if (!valueAt.mAdded) {
                View view = valueAt.getView();
                if (Build.VERSION.SDK_INT < 11) {
                    valueAt.getView().setVisibility(4);
                } else {
                    valueAt.mPostponedAlpha = view.getAlpha();
                    view.setAlpha(0.0f);
                }
            }
        }
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, ArraySet<Fragment> arraySet) {
        int i3 = i2;
        for (int i4 = i2 - 1; i4 >= i; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            boolean booleanValue = arrayList2.get(i4).booleanValue();
            if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i4 + 1, i2)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, booleanValue);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (booleanValue) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps();
                }
                i3--;
                if (i4 != i3) {
                    arrayList.remove(i4);
                    arrayList.add(i3, backStackRecord);
                }
                addAddedFragments(arraySet);
            }
        }
        return i3;
    }

    /* access modifiers changed from: private */
    public void completeExecute(BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(backStackRecord);
        arrayList2.add(Boolean.valueOf(z));
        executeOps(arrayList, arrayList2, 0, 1);
        if (z2) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            moveToState(this.mCurState, true);
        }
        ArrayList<Fragment> arrayList3 = this.mActive;
        if (arrayList3 != null) {
            int size = arrayList3.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = this.mActive.get(i);
                if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && backStackRecord.interactsWith(fragment.mContainerId)) {
                    if (Build.VERSION.SDK_INT >= 11 && fragment.mPostponedAlpha > 0.0f) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha);
                    }
                    if (z3) {
                        fragment.mPostponedAlpha = 0.0f;
                    } else {
                        fragment.mPostponedAlpha = -1.0f;
                        fragment.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    private Fragment findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup == null || view == null) {
            return null;
        }
        for (int indexOf = this.mAdded.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
            Fragment fragment2 = this.mAdded.get(indexOf);
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return fragment2;
            }
        }
        return null;
    }

    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                backStackRecord.executePopOps();
            } else {
                backStackRecord.executeOps();
            }
            i++;
        }
    }

    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        int i = this.mCurState;
        if (i >= 1) {
            int min = Math.min(i, 4);
            ArrayList<Fragment> arrayList = this.mAdded;
            int size = arrayList == null ? 0 : arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Fragment fragment = this.mAdded.get(i2);
                if (fragment.mState < min) {
                    moveToState(fragment, min, fragment.getNextAnim(), fragment.getNextTransition(), false);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        arraySet.add(fragment);
                    }
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        ArrayList<Fragment> arrayList = this.mActive;
        int size = arrayList == null ? 0 : arrayList.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = this.mActive.get(i);
            if (!(fragment == null || fragment.getAnimatingAway() == null)) {
                int stateAfterAnimating = fragment.getStateAfterAnimating();
                View animatingAway = fragment.getAnimatingAway();
                fragment.setAnimatingAway((View) null);
                Animation animation = animatingAway.getAnimation();
                if (animation != null) {
                    animation.cancel();
                }
                moveToState(fragment, stateAfterAnimating, 0, 0, false);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        if (r0 <= 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean generateOpsForPendingActions(java.util.ArrayList<android.support.v4.app.BackStackRecord> r5, java.util.ArrayList<java.lang.Boolean> r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.mPendingActions     // Catch:{ all -> 0x003d }
            r1 = 0
            if (r0 == 0) goto L_0x003b
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.mPendingActions     // Catch:{ all -> 0x003d }
            int r0 = r0.size()     // Catch:{ all -> 0x003d }
            if (r0 != 0) goto L_0x000f
            goto L_0x003b
        L_0x000f:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.mPendingActions     // Catch:{ all -> 0x003d }
            int r0 = r0.size()     // Catch:{ all -> 0x003d }
            r2 = 0
        L_0x0016:
            if (r2 >= r0) goto L_0x0026
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r3 = r4.mPendingActions     // Catch:{ all -> 0x003d }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x003d }
            android.support.v4.app.FragmentManagerImpl$OpGenerator r3 = (android.support.v4.app.FragmentManagerImpl.OpGenerator) r3     // Catch:{ all -> 0x003d }
            r3.generateOps(r5, r6)     // Catch:{ all -> 0x003d }
            int r2 = r2 + 1
            goto L_0x0016
        L_0x0026:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r5 = r4.mPendingActions     // Catch:{ all -> 0x003d }
            r5.clear()     // Catch:{ all -> 0x003d }
            android.support.v4.app.FragmentHostCallback r5 = r4.mHost     // Catch:{ all -> 0x003d }
            android.os.Handler r5 = r5.getHandler()     // Catch:{ all -> 0x003d }
            java.lang.Runnable r6 = r4.mExecCommit     // Catch:{ all -> 0x003d }
            r5.removeCallbacks(r6)     // Catch:{ all -> 0x003d }
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            if (r0 <= 0) goto L_0x003a
            r1 = 1
        L_0x003a:
            return r1
        L_0x003b:
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            return r1
        L_0x003d:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            throw r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.generateOpsForPendingActions(java.util.ArrayList, java.util.ArrayList):boolean");
    }

    /* access modifiers changed from: package-private */
    public void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean z = false;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = this.mActive.get(i);
                if (!(fragment == null || fragment.mLoaderManager == null)) {
                    z |= fragment.mLoaderManager.hasRunningLoaders();
                }
            }
            if (!z) {
                this.mHavePendingDeferredStart = false;
                startPendingDeferredFragments();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(backStackRecord);
        reportBackStackChanged();
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackState(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String str, int i, int i2) {
        int i3;
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        if (arrayList3 == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size = arrayList3.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.mBackStack.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i >= 0) {
                int size2 = this.mBackStack.size() - 1;
                while (i3 >= 0) {
                    BackStackRecord backStackRecord = this.mBackStack.get(i3);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size2 = i3 - 1;
                }
                if (i3 < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    i3--;
                    while (i3 >= 0) {
                        BackStackRecord backStackRecord2 = this.mBackStack.get(i3);
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                            break;
                        }
                        i3--;
                    }
                }
            } else {
                i3 = -1;
            }
            if (i3 == this.mBackStack.size() - 1) {
                return false;
            }
            for (int size3 = this.mBackStack.size() - 1; size3 > i3; size3--) {
                arrayList.add(this.mBackStack.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public FragmentManagerNonConfig retainNonConfig() {
        ArrayList arrayList;
        ArrayList arrayList2;
        FragmentManagerNonConfig retainNonConfig;
        if (this.mActive != null) {
            arrayList2 = null;
            arrayList = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = this.mActive.get(i);
                if (fragment != null) {
                    boolean z = true;
                    if (fragment.mRetainInstance) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                        }
                        arrayList2.add(fragment);
                        fragment.mRetaining = true;
                        fragment.mTargetIndex = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                        if (DEBUG) {
                            Log.v(TAG, "retainNonConfig: keeping retained " + fragment);
                        }
                    }
                    if (fragment.mChildFragmentManager == null || (retainNonConfig = fragment.mChildFragmentManager.retainNonConfig()) == null) {
                        z = false;
                    } else {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            for (int i2 = 0; i2 < i; i2++) {
                                arrayList.add((Object) null);
                            }
                        }
                        arrayList.add(retainNonConfig);
                    }
                    if (arrayList != null && !z) {
                        arrayList.add((Object) null);
                    }
                }
            }
        } else {
            arrayList2 = null;
            arrayList = null;
        }
        if (arrayList2 == null && arrayList == null) {
            return null;
        }
        return new FragmentManagerNonConfig(arrayList2, arrayList);
    }

    /* access modifiers changed from: package-private */
    public void saveFragmentViewState(Fragment fragment) {
        if (fragment.mInnerView != null) {
            SparseArray<Parcelable> sparseArray = this.mStateArray;
            if (sparseArray == null) {
                this.mStateArray = new SparseArray<>();
            } else {
                sparseArray.clear();
            }
            fragment.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                fragment.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        } else {
            bundle = null;
        }
        if (fragment.mView != null) {
            saveFragmentViewState(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public Parcelable saveAllState() {
        int[] iArr;
        int size;
        int size2;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        ArrayList<Fragment> arrayList = this.mActive;
        BackStackState[] backStackStateArr = null;
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        int size3 = this.mActive.size();
        FragmentState[] fragmentStateArr = new FragmentState[size3];
        boolean z = false;
        for (int i = 0; i < size3; i++) {
            Fragment fragment = this.mActive.get(i);
            if (fragment != null) {
                if (fragment.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.mIndex));
                }
                FragmentState fragmentState = new FragmentState(fragment);
                fragmentStateArr[i] = fragmentState;
                if (fragment.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
                } else {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState(fragment);
                    if (fragment.mTarget != null) {
                        if (fragment.mTarget.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, fragment.mTarget);
                        if (fragment.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, fragment.mTargetRequestCode);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + fragment + ": " + fragmentState.mSavedFragmentState);
                }
                z = true;
            }
        }
        if (!z) {
            if (DEBUG) {
                Log.v(TAG, "saveAllState: no fragments!");
            }
            return null;
        }
        ArrayList<Fragment> arrayList2 = this.mAdded;
        if (arrayList2 == null || (size2 = arrayList2.size()) <= 0) {
            iArr = null;
        } else {
            iArr = new int[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                iArr[i2] = this.mAdded.get(i2).mIndex;
                if (iArr[i2] < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + iArr[i2]));
                }
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                }
            }
        }
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        if (arrayList3 != null && (size = arrayList3.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i3 = 0; i3 < size; i3++) {
                backStackStateArr[i3] = new BackStackState(this.mBackStack.get(i3));
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.mActive = fragmentStateArr;
        fragmentManagerState.mAdded = iArr;
        fragmentManagerState.mBackStack = backStackStateArr;
        return fragmentManagerState;
    }

    /* access modifiers changed from: package-private */
    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        List<FragmentManagerNonConfig> list;
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.mActive != null) {
                if (fragmentManagerNonConfig != null) {
                    List<Fragment> fragments = fragmentManagerNonConfig.getFragments();
                    list = fragmentManagerNonConfig.getChildNonConfigs();
                    int size = fragments != null ? fragments.size() : 0;
                    for (int i = 0; i < size; i++) {
                        Fragment fragment = fragments.get(i);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: re-attaching retained " + fragment);
                        }
                        FragmentState fragmentState = fragmentManagerState.mActive[fragment.mIndex];
                        fragmentState.mInstance = fragment;
                        fragment.mSavedViewState = null;
                        fragment.mBackStackNesting = 0;
                        fragment.mInLayout = false;
                        fragment.mAdded = false;
                        fragment.mTarget = null;
                        if (fragmentState.mSavedFragmentState != null) {
                            fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                            fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            fragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
                        }
                    }
                } else {
                    list = null;
                }
                this.mActive = new ArrayList<>(fragmentManagerState.mActive.length);
                ArrayList<Integer> arrayList = this.mAvailIndices;
                if (arrayList != null) {
                    arrayList.clear();
                }
                int i2 = 0;
                while (i2 < fragmentManagerState.mActive.length) {
                    FragmentState fragmentState2 = fragmentManagerState.mActive[i2];
                    if (fragmentState2 != null) {
                        Fragment instantiate = fragmentState2.instantiate(this.mHost, this.mParent, (list == null || i2 >= list.size()) ? null : list.get(i2));
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: active #" + i2 + ": " + instantiate);
                        }
                        this.mActive.add(instantiate);
                        fragmentState2.mInstance = null;
                    } else {
                        this.mActive.add((Object) null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList<>();
                        }
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: avail #" + i2);
                        }
                        this.mAvailIndices.add(Integer.valueOf(i2));
                    }
                    i2++;
                }
                if (fragmentManagerNonConfig != null) {
                    List<Fragment> fragments2 = fragmentManagerNonConfig.getFragments();
                    int size2 = fragments2 != null ? fragments2.size() : 0;
                    for (int i3 = 0; i3 < size2; i3++) {
                        Fragment fragment2 = fragments2.get(i3);
                        if (fragment2.mTargetIndex >= 0) {
                            if (fragment2.mTargetIndex < this.mActive.size()) {
                                fragment2.mTarget = this.mActive.get(fragment2.mTargetIndex);
                            } else {
                                Log.w(TAG, "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.mTargetIndex);
                                fragment2.mTarget = null;
                            }
                        }
                    }
                }
                if (fragmentManagerState.mAdded != null) {
                    this.mAdded = new ArrayList<>(fragmentManagerState.mAdded.length);
                    int i4 = 0;
                    while (i4 < fragmentManagerState.mAdded.length) {
                        Fragment fragment3 = this.mActive.get(fragmentManagerState.mAdded[i4]);
                        if (fragment3 == null) {
                            throwException(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[i4]));
                        }
                        fragment3.mAdded = true;
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: added #" + i4 + ": " + fragment3);
                        }
                        if (!this.mAdded.contains(fragment3)) {
                            this.mAdded.add(fragment3);
                            i4++;
                        } else {
                            throw new IllegalStateException("Already added!");
                        }
                    }
                } else {
                    this.mAdded = null;
                }
                if (fragmentManagerState.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
                    for (int i5 = 0; i5 < fragmentManagerState.mBackStack.length; i5++) {
                        BackStackRecord instantiate2 = fragmentManagerState.mBackStack[i5].instantiate(this);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: back stack #" + i5 + " (index " + instantiate2.mIndex + "): " + instantiate2);
                            instantiate2.dump("  ", new PrintWriter(new LogWriter(TAG)), false);
                        }
                        this.mBackStack.add(instantiate2);
                        if (instantiate2.mIndex >= 0) {
                            setBackStackIndex(instantiate2.mIndex, instantiate2);
                        }
                    }
                    return;
                }
                this.mBackStack = null;
            }
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost == null) {
            this.mHost = fragmentHostCallback;
            this.mContainer = fragmentContainer;
            this.mParent = fragment;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void noteStateNotSaved() {
        this.mStateSaved = false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        moveToState(1, false);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchPause() {
        moveToState(4, false);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        moveToState(3, false);
    }

    public void dispatchReallyStop() {
        moveToState(2, false);
    }

    public void dispatchDestroyView() {
        moveToState(1, false);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        moveToState(0, false);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        ArrayList<Fragment> arrayList = this.mAdded;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null) {
                    fragment.performMultiWindowModeChanged(z);
                }
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        ArrayList<Fragment> arrayList = this.mAdded;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null) {
                    fragment.performPictureInPictureModeChanged(z);
                }
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performConfigurationChanged(configuration);
                }
            }
        }
    }

    public void dispatchLowMemory() {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z;
        ArrayList<Fragment> arrayList = null;
        if (this.mAdded != null) {
            z = false;
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(fragment);
                    z = true;
                }
            }
        } else {
            z = false;
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment fragment2 = this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mAdded == null) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = this.mAdded.get(i);
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        if (this.mLifecycleCallbacks == null) {
            this.mLifecycleCallbacks = new CopyOnWriteArrayList<>();
        }
        this.mLifecycleCallbacks.add(new Pair(fragmentLifecycleCallbacks, Boolean.valueOf(z)));
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            synchronized (copyOnWriteArrayList) {
                int i = 0;
                int size = this.mLifecycleCallbacks.size();
                while (true) {
                    if (i >= size) {
                        break;
                    } else if (this.mLifecycleCallbacks.get(i).first == fragmentLifecycleCallbacks) {
                        this.mLifecycleCallbacks.remove(i);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreAttached(fragment, context, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentPreAttached(this, fragment, context);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentAttached(fragment, context, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentAttached(this, fragment, context);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentCreated(fragment, bundle, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentCreated(this, fragment, bundle);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentActivityCreated(fragment, bundle, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentActivityCreated(this, fragment, bundle);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewCreated(fragment, view, bundle, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentViewCreated(this, fragment, view, bundle);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentStarted(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStarted(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentStarted(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentResumed(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentResumed(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentResumed(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentPaused(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPaused(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentPaused(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentStopped(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStopped(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentStopped(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentSaveInstanceState(this, fragment, bundle);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewDestroyed(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentViewDestroyed(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentDestroyed(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDestroyed(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentDestroyed(this, fragment);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentDetached(Fragment fragment, boolean z) {
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            FragmentManager fragmentManager = fragment2.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDetached(fragment, true);
            }
        }
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        if (copyOnWriteArrayList != null) {
            Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                Pair next = it.next();
                if (!z || ((Boolean) next.second).booleanValue()) {
                    ((FragmentManager.FragmentLifecycleCallbacks) next.first).onFragmentDetached(this, fragment);
                }
            }
        }
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Fragment fragment;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        String str2 = str;
        Fragment fragment2 = null;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet2.getAttributeValue((String) null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet2, FragmentTag.Fragment);
        int i = 0;
        String string = attributeValue == null ? obtainStyledAttributes.getString(0) : attributeValue;
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string2 = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), string)) {
            return null;
        }
        if (view != null) {
            i = view.getId();
        }
        if (i == -1 && resourceId == -1 && string2 == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + string);
        }
        if (resourceId != -1) {
            fragment2 = findFragmentById(resourceId);
        }
        if (fragment2 == null && string2 != null) {
            fragment2 = findFragmentByTag(string2);
        }
        if (fragment2 == null && i != -1) {
            fragment2 = findFragmentById(i);
        }
        if (DEBUG) {
            Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + string + " existing=" + fragment2);
        }
        if (fragment2 == null) {
            Fragment instantiate = Fragment.instantiate(context, string);
            instantiate.mFromLayout = true;
            instantiate.mFragmentId = resourceId != 0 ? resourceId : i;
            instantiate.mContainerId = i;
            instantiate.mTag = string2;
            instantiate.mInLayout = true;
            instantiate.mFragmentManager = this;
            FragmentHostCallback fragmentHostCallback = this.mHost;
            instantiate.mHost = fragmentHostCallback;
            instantiate.onInflate(fragmentHostCallback.getContext(), attributeSet2, instantiate.mSavedFragmentState);
            addFragment(instantiate, true);
            fragment = instantiate;
        } else if (!fragment2.mInLayout) {
            fragment2.mInLayout = true;
            fragment2.mHost = this.mHost;
            if (!fragment2.mRetaining) {
                fragment2.onInflate(this.mHost.getContext(), attributeSet2, fragment2.mSavedFragmentState);
            }
            fragment = fragment2;
        } else {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(i) + " with another fragment for " + string);
        }
        if (this.mCurState >= 1 || !fragment.mFromLayout) {
            moveToState(fragment);
        } else {
            moveToState(fragment, 1, 0, 0, false);
        }
        if (fragment.mView != null) {
            if (resourceId != 0) {
                fragment.mView.setId(resourceId);
            }
            if (fragment.mView.getTag() == null) {
                fragment.mView.setTag(string2);
            }
            return fragment.mView;
        }
        throw new IllegalStateException("Fragment " + string + " did not create a view.");
    }

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment = {16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    /* compiled from: FragmentManager */
    private class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
        }
    }

    /* compiled from: FragmentManager */
    static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        /* access modifiers changed from: private */
        public final boolean mIsBack;
        private int mNumPostponed;
        /* access modifiers changed from: private */
        public final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean z) {
            this.mIsBack = z;
            this.mRecord = backStackRecord;
        }

        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed == 0) {
                this.mRecord.mManager.scheduleCommit();
            }
        }

        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        public void completeTransaction() {
            boolean z = this.mNumPostponed > 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int size = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = fragmentManagerImpl.mAdded.get(i);
                fragment.setOnStartEnterTransitionListener((Fragment.OnStartEnterTransitionListener) null);
                if (z && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, !z, true);
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }
    }
}
