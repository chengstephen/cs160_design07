package com.projects.cs160_design07;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/**
 * Created by cstep on 4/19/2017.
 */

public class EmergencyDialog extends Dialog implements OnClickListener {

    public Activity activity;
    public Button call, dont_call;

    public EmergencyDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.emergency_dialog);
        call = (Button) findViewById(R.id.btn_call);
        dont_call = (Button) findViewById(R.id.btn_dont_call);
        call.setOnClickListener(this);
        dont_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:911"));
                getContext().startActivity(intent);
                break;
            case R.id.btn_dont_call:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
