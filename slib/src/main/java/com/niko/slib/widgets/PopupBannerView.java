package com.niko.slib.widgets;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.niko.slib.R;
import com.niko.slib.databinding.DisconnectViewBinding;


/**
 * Created by niko on 16/7/12.
 */

public class PopupBannerView extends PopupWindow {
    private DisconnectViewBinding binding;

    public PopupBannerView(Context context, int width, int height) {
        super(width, height);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.disconnect_view, null, false);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                view.getContext().startActivity(intent);
            }
        });
        setContentView(binding.getRoot());
        ColorDrawable cd = new ColorDrawable(context.getResources().getColor(R.color.alpha_gray));
        setBackgroundDrawable(cd);
    }

    public DisconnectViewBinding getBinding() {
        return binding;
    }
}
