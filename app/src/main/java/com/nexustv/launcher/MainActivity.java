package com.nexustv.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status;
    private int presses = 0;
    private static final int BG = Color.rgb(8, 20, 32);
    private static final int BLUE = Color.rgb(100, 204, 255);
    private static final String[] TILES = {"Home", "Family Games", "Memories", "Workout", "Nexus AI", "Settings"};

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setPadding(dp(48), dp(30), dp(48), dp(24));
        TextView heading = text("NEXUS TV  ·  DEVICE COMPATIBILITY TEST", 30, BLUE, true);
        root.addView(heading);
        TextView sub = text("ZTE B866V2F • Android TV • remote navigation test", 19, Color.LTGRAY, false);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.topMargin = dp(6); sp.bottomMargin = dp(24);
        root.addView(sub, sp);
        String[][] rows = {{TILES[0],TILES[1],TILES[2]}, {TILES[3],TILES[4],TILES[5]}};
        for (String[] labels : rows) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (String label : labels) {
                TextView tile = text(label, 23, Color.WHITE, true);
                tile.setGravity(Gravity.CENTER);
                tile.setFocusable(true);
                tile.setFocusableInTouchMode(false);
                tile.setBackground(box(false));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, dp(116), 1);
                p.setMargins(dp(5), dp(5), dp(5), dp(5));
                row.addView(tile, p);
                tile.setOnFocusChangeListener((v, focused) -> {
                    v.setBackground(box(focused));
                    v.setScaleX(focused ? 1.03f : 1f);
                    v.setScaleY(focused ? 1.03f : 1f);
                });
                tile.setOnClickListener(v -> status.setText("OK: selected " + label + " • Remote input works (" + (++presses) + ")"));
                if (label.equals("Home")) tile.requestFocus();
            }
            root.addView(row);
        }
        status = text("Use ◀ ▶ ▲ ▼ to move, OK to select, BACK to exit.", 19, Color.WHITE, false);
        LinearLayout.LayoutParams ss = new LinearLayout.LayoutParams(-1, -2);
        ss.topMargin = dp(30);
        root.addView(status, ss);
        TextView footer = text("Local-only test • no sign-in • no microphone • no network access • no TV firmware changes", 15, Color.LTGRAY, false);
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-1, -2);
        fp.topMargin = dp(22); root.addView(footer, fp);
        setContentView(root);
    }

    private GradientDrawable box(boolean selected) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(selected ? Color.rgb(29,85,119) : Color.rgb(24,46,65));
        d.setCornerRadius(dp(14));
        d.setStroke(dp(selected ? 3 : 1), selected ? BLUE : Color.rgb(57,88,111));
        return d;
    }

    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + .5f); }

    @Override public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { finish(); return true; }
        return super.onKeyDown(keyCode, e);
    }
}
