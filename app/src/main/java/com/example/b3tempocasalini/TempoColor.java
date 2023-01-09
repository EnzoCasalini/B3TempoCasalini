package com.example.b3tempocasalini;

import com.google.gson.annotations.SerializedName;

public enum TempoColor {
    @SerializedName("TEMPO_ROUGE")
    RED(R.color.tempo_red_day_bg, R.string.red),

    @SerializedName("TEMPO_BLANC")
    WHITE(R.color.tempo_white_day_bg, R.string.white),

    @SerializedName("TEMPO_BLEU")
    BLUE(R.color.tempo_blue_day_bg, R.string.blue),

    @SerializedName("NON_DEFINI")
    UNKNOWN(R.color.tempo_undecided_day_bg, R.string.unknown);

    private final int colorResId;
    private final int stringResId;

    TempoColor(int resId, int stringResId) {
        this.colorResId = resId;
        this.stringResId = stringResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public int getStringResId() {
        return stringResId;
    }
}
