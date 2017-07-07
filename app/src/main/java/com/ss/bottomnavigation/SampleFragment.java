package com.ss.bottomnavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.bottomnavigation.databinding.FragmentSampleBinding;


public class SampleFragment extends Fragment {

    public static final String TAG_1 = SampleFragment.class.getSimpleName() + "1";
    public static final String TAG_2 = SampleFragment.class.getSimpleName() + "2";
    public static final String TAG_3 = SampleFragment.class.getSimpleName() + "3";

    private FragmentSampleBinding binding;
    private String tag;

    public SampleFragment() {
        // Required empty public constructor
    }

    public static SampleFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString("tag", tag);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(inflater, container, false);
        binding.text.setText(tag);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
