package akilliyazilim.justhoy.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class ShimmerExtendedText extends ShimmerTextView{
    public ShimmerExtendedText(Context context) {
        super(context);
        init();
    }

    public ShimmerExtendedText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShimmerExtendedText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "helvelticabold.otf");
        setTypeface(tf);
    }
}
