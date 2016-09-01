package online.pandaapps.gre.projecteuler.Utils.Fonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by sangeet on 02/09/16.
 */
public class CustomFontET extends EditText {
    public CustomFontET(Context context) {
        super(context);
        FontUtils.applyCustomFont(this, context, null);

    }

    public CustomFontET(Context context, AttributeSet attrs) {
        super(context, attrs);

        FontUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontET(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        FontUtils.applyCustomFont(this, context, attrs);
    }
}
