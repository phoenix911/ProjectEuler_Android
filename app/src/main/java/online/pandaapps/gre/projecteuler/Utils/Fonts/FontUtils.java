package online.pandaapps.gre.projecteuler.Utils.Fonts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.R;

/**
 * Created by sangeet on 28/08/16.
 */
public class FontUtils {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont (TextView customFontTV, Context context, AttributeSet attrs){

        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);

        // check if a special textStyle was used (e.g. extra bold)
        int textStyle = attributeArray.getInt(R.styleable.CustomFontTextView_textStyle, 0);

        // if nothing extra was used, fall back to regular android:textStyle parameter
        if (textStyle == 0) {
            textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        customFontTV.setTypeface(customFont);

        attributeArray.recycle();

    }

    private static Typeface selectTypeface(Context context, String fontName, int textStyle) {
        if (fontName.contentEquals(context.getString(R.string.font_name_poiretone))) {
            return FontCache.getTypeface("PoiretOne-Regular.ttf", context);
        }
        else if (fontName.contentEquals(context.getString(R.string.font_name_arimo_madurai))) {
            /*
            information about the TextView textStyle:
            http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
            */
            switch (textStyle) {
                case Typeface.BOLD: // bold
                    return FontCache.getTypeface("PoiretOne-Regular.ttf", context);

                case Typeface.ITALIC: // italic
                    return FontCache.getTypeface("PoiretOne-Regular.ttf", context);

                case Typeface.BOLD_ITALIC: // bold italic
                    return FontCache.getTypeface("PoiretOne-Regular.ttf", context);


                case Typeface.NORMAL: // regular
                default:
                    return FontCache.getTypeface("PoiretOne-Regular.ttf", context);
            }
        }
        else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            return null;
        }
    }
}
