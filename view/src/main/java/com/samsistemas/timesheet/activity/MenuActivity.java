package com.samsistemas.timesheet.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.samsistemas.timesheet.R;
import com.samsistemas.timesheet.activity.base.BaseAppCompatActivity;
import com.samsistemas.timesheet.navigation.AccountNavigator;
import com.samsistemas.timesheet.navigation.SettingsNavigator;
import com.samsistemas.timesheet.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class used as MenuActivity. It manages multiples fragments instances by
 * a NavigationDrawer.
 *
 * @author jonatan.salas
 */
public class MenuActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CalendarListener {
    private static final String LOG_TAG = MenuActivity.class.getSimpleName();
    private CustomCalendarView mCalendarView;
    private TextView mDateTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle(R.string.action_view_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitleEnabled(false);

        mDateTitle = (TextView) findViewById(R.id.date);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCalendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        mCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendarView.setShowOverflowDate(true);
        mCalendarView.refreshCalendar(getCalendar());
        mCalendarView.setCalendarListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        final int id = item.getItemId();

        switch (id) {
            case android.support.v7.appcompat.R.id.home:
                 onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        final boolean isChecked = menuItem.isChecked();
        final int id = menuItem.getItemId();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (!isChecked) {
            menuItem.setChecked(true);
        }

        switch (id) {
            case R.id.action_view_calendar:
                setTitle(R.string.action_view_calendar);
                //addFragment(mFragment);
                break;

            case R.id.action_add_hour:
                setTitle(R.string.action_add_hour);
                //addFragment(mFragment);
                break;

            case R.id.action_account:
                AccountNavigator.newInstance().navigateWithAnimation(this, navigationView);
                break;

            case R.id.action_settings:
                SettingsNavigator.newInstance().navigateWithAnimation(this, navigationView);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onDateSelected(Date date) {
        String dateTitle = DateUtil.formatDate(getApplicationContext(), date);
        mDateTitle.setText(dateTitle);
    }

    @Override
    public void onMonthChanged(Date date) {

    }

    private Calendar getCalendar() {
        return Calendar.getInstance(Locale.getDefault());
    }
}
