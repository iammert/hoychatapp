package akilliyazilim.justhoy.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class HalvelticaEdittext extends EditText{

    public HalvelticaEdittext(Context context) {
        super(context);
        init();
    }

    public HalvelticaEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalvelticaEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "helvelticaregular.ttf");
        setTypeface(tf);
    }
}
