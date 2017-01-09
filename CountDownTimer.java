import android.os.CountDownTimer;

/**
 * Class extends CountDownTimer, used to show countdown.
 *
 * In the other activity class:
 * private Timer mTimer;
 * mTimer = new Timer(1000 * 60);  // for 1 min
 * mTimer.enableTimerListener(ArcadeModeActivity.this);
 * mTimer.start();
 * set textView in the Override method: setTimeValueTicking();
 *
 *
 * credit: Alexey Mileev
 * extracted from https://github.com/rozag/Tom
 *
 * Modified by https://twitter.com/mohammadlaif
 * http://www.droidprogramming.com
 * mohammadlaif on 1/5/17.
 */

public class Timer extends CountDownTimer{

    private TimerListener mTimerListener = null;
    private StringBuilder mTimeValue;


    public Timer(long millisInFuture) {
        super(millisInFuture, 1000);
    }


    @Override
    public void onTick(long millisUntilFinished) {
        final long SECOND = 1000;
        final long minutes = millisUntilFinished / (60 * SECOND); // -> 1 minute
        final long tmp = millisUntilFinished % (60 * SECOND);
        final long seconds = (tmp <= 6 * SECOND) ? roundSeconds(tmp) / 10 : roundSeconds(tmp);

        mTimeValue = new StringBuilder();
        mTimeValue.append((rightFormat(minutes)));
        mTimeValue.append(":");
        mTimeValue.append((rightFormat(seconds)));

        if (mTimerListener != null) {
            mTimerListener.setTimeValueTicking(mTimeValue.toString());
        }

    }


    @Override
    public void onFinish() {
        if (mTimerListener != null){
            mTimerListener.onFinish();
        }
    }


    private long roundSeconds(long seconds) {
        // Take only 2 left digits (e.g. 12345 -> 12)
        // extracting only the 2 left digits from the long seconds number
        final long SECONDS_IN_MINUTE = 60;
        if (seconds < SECONDS_IN_MINUTE) {
            return seconds;
        } else {
            return roundSeconds(seconds / 10);
        }
    }


    private String rightFormat(long number) {
        if (number < 10){
            return "0" + number;
        } else {
            return "" + number;
        }
    }


    // Listener

    public void enableTimerListener(final TimerListener listener) {
        mTimerListener = listener;
    }


    public void disableTimerListener() {
        mTimerListener = null;
    }


    public interface TimerListener {
        void setTimeValueTicking(final String text);
        void onFinish();
    }
}
