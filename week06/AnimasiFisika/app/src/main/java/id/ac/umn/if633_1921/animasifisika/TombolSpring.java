package id.ac.umn.if633_1921.animasifisika;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;

import static androidx.dynamicanimation.animation.SpringForce.STIFFNESS_HIGH;
import static androidx.dynamicanimation.animation.SpringForce.STIFFNESS_LOW;
import static androidx.dynamicanimation.animation.SpringForce.STIFFNESS_VERY_LOW;


public class TombolSpring extends AppCompatButton {
    public TombolSpring(Context context) {
        super(context);
    }

    public TombolSpring(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TombolSpring(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Create a spring animation along the view's Y position.
                // Let resting position be at the view's current Y position.
                final SpringAnimation anim = new SpringAnimation(this,
                        (FloatPropertyCompat) DynamicAnimation.Y, this.getY())
                        .setStartVelocity(10000); // In pixels per second.
                // Low stiffness makes the spring bouncy.
                anim.getSpring().setStiffness(STIFFNESS_VERY_LOW);
                anim.start();
                break;
            default:
                // Do nothing.
        }
        return super.onTouchEvent(event);
    }
}
