package online.pandaapps.gre.projecteuler.Utils.Fonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sangeet on 28/08/16.
 */
public class CustomFontTV extends TextView {
    public CustomFontTV(Context context) {
        super(context);

        FontUtils.applyCustomFont(this, context, null);
    }

    public CustomFontTV(Context context, AttributeSet attrs) {
        super(context, attrs);

        FontUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontTV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        FontUtils.applyCustomFont(this, context, attrs);
    }

}
