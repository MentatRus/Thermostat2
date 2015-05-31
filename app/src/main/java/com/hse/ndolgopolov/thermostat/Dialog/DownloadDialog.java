package com.hse.ndolgopolov.thermostat.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.app.Dialog;

/**
 * Created by Igor on 31.05.2015.
 */
public class DownloadDialog extends Dialog {
    private ListView list;

    public DownloadDialog(Context context) {
        super(context);
        list = (ListView) findViewById(R.id.downloadListView);

        this.title("Download schedule")
                .positiveAction("DOWNLOAD")
                .negativeAction("CANCEL")
                .contentView(R.layout.download_dialog)
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
