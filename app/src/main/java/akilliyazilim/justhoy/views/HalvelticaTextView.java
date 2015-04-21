package akilliyazilim.justhoy.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mertsimsek on 13.08.2014.
 */
public class HalvelticaTextView extends TextView {

    public HalvelticaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HalvelticaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalvelticaTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "helvelticabold.otf");
        setTypeface(tf);
    }

}
