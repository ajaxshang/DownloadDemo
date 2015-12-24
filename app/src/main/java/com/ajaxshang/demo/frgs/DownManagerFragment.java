package com.ajaxshang.demo.frgs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajaxshang.demo.R;

/**
 * Created by Administrator on 2015/12/24.
 */
public class DownManagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_downloadmanager, null);
        return view;
    }
}
