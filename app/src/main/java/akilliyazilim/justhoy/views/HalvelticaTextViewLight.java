package akilliyazilim.justhoy.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class HalvelticaTextViewLight extends TextView {

    public HalvelticaTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HalvelticaTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalvelticaTextViewLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "helvelticaregular.ttf");
        setTypeface(tf);
    }

}