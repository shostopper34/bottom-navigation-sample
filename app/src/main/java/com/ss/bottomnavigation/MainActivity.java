package com.ss.bottomnavigation;

import android.databinding.DataBindingUtil;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ss.bottomnavigation.databinding.ActivityMainBinding;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private Fragment fragment1;

    private Fragment fragment2;

    private Fragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        disableShiftingMode(binding.bottomNavigation);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.nav_item_1:
                    Log.d(TAG, "item1");
                    changeFragment(fragment1, SampleFragment.TAG_1);
                    binding.toolbar.setTitle(SampleFragment.TAG_1);
                    break;
                case R.id.nav_item_2:
                    Log.d(TAG, "item2");
                    changeFragment(fragment2, SampleFragment.TAG_2);
                    binding.toolbar.setTitle(SampleFragment.TAG_2);
                    break;
                case R.id.nav_item_3:
                    Log.d(TAG, "item3");
                    changeFragment(fragment3, SampleFragment.TAG_3);
                    binding.toolbar.setTitle(SampleFragment.TAG_3);
                    break;
            }
            return false;
        });
        initFragments(savedInstanceState);
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        fragment1 = manager.findFragmentByTag(SampleFragment.TAG_1);
        fragment2 = manager.findFragmentByTag(SampleFragment.TAG_2);
        fragment3 = manager.findFragmentByTag(SampleFragment.TAG_3);

        if (fragment1 == null) {
            fragment1 = SampleFragment.newInstance(SampleFragment.TAG_1);
        }
        if (fragment2 == null) {
            fragment2 = SampleFragment.newInstance(SampleFragment.TAG_2);
        }
        if (fragment3 == null) {
            fragment3 = SampleFragment.newInstance(SampleFragment.TAG_3);
        }

        if (savedInstanceState == null) {
            changeFragment(fragment1, SampleFragment.TAG_1);
        }
    }

    @SuppressWarnings("RestrictedApi")
    private void disableShiftingMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(e.toString(), "could not get shifting mode");
        } catch (IllegalAccessException e) {
            Log.e(e.toString(), "could not change shifting mode value");
        }
    }

    private boolean changeFragment(Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            return false;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment currentFragment = manager.findFragmentById(R.id.content_view);
        if (currentFragment != null) {
            transaction.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            transaction.attach(fragment);
        } else {
            transaction.add(R.id.content_view, fragment, tag);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        manager.executePendingTransactions();

        return true;
    }

    @Override
    public void onBackPressed() {
        if (changeFragment(fragment1, SampleFragment.TAG_1)) {
            binding.toolbar.setTitle(fragment1.getTag());
        }
        super.onBackPressed();
    }
}
