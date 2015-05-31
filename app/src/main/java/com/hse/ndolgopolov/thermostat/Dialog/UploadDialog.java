package com.hse.ndolgopolov.thermostat.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.app.Dialog;
import com.rey.material.widget.EditText;

/**
 * Created by Igor on 31.05.2015.
 */
public class UploadDialog extends Dialog {
    private EditText editText;

    public UploadDialog(Context context) {
        super(context);

        this.title("Upload schedule")
                .positiveAction("UPLOAD")
                .negativeAction("CANCEL")
                .contentView(R.layout.upload_dialog)
                .cancelable(true)
                .positiveActionTextColor(Color.parseColor("#3F51B5"))
                .negativeActionTextColor(Color.RED);

        mNegativeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    public Button getPositiveButton() {
        return mPositiveAction;
    }
}
