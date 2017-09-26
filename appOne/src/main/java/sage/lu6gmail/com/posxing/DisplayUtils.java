package sage.lu6gmail.com.posxing;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * 类名: DisplayUtils
 * 此类用途: ---
 *
 * @Author: GuXiao
 * @Date: 2017-07-10 17:00
 * @Email: sage.lu6@gmail.com
 * @FileName: sage.lu6gmail.com.posxing.DisplayUtils.java
 */
public class DisplayUtils {
    public static int dip2px(Context paramContext, float paramFloat)
    {
        return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }

    public static int dip2px(Context paramContext, int paramInt)
    {
        return (int)(0.5F + paramContext.getResources().getDisplayMetrics().density * paramInt);
    }

    public static DisplayMetrics getDiaplay(Context paramContext)
    {
        new DisplayMetrics();
        return paramContext.getResources().getDisplayMetrics();
    }

    public static int getDimensPx(Context paramContext, int paramInt)
    {
        return dip2px(paramContext, paramContext.getResources().getDimension(paramInt));
    }

    public static int getScreenType(Context paramContext)
    {
        DisplayMetrics localDisplayMetrics = getDiaplay(paramContext);
        if (localDisplayMetrics.heightPixels - localDisplayMetrics.widthPixels > 0)
            return 1;
        return 0;
    }

    public static int px2dip(Context paramContext, float paramFloat)
    {
        return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }

    public static int px2dip(Context paramContext, int paramInt)
    {
        float f = paramContext.getResources().getDisplayMetrics().density;
        return (int)(0.5F + paramInt / f);
    }

    public static int px2sp(Context paramContext, float paramFloat)
    {
        return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int px2sp(Context paramContext, int paramInt)
    {
        float f = paramContext.getResources().getDisplayMetrics().scaledDensity;
        return (int)(0.5F + paramInt / f);
    }

    public static void setLucency(TextView paramTextView)
    {
        paramTextView.setTextColor(Color.argb(128, 255, 255, 255));
    }

    public static int sp2px(Context paramContext, float paramFloat)
    {
        return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int sp2px(Context paramContext, int paramInt)
    {
        return (int)(0.5F + paramContext.getResources().getDisplayMetrics().scaledDensity * paramInt);
    }
}
