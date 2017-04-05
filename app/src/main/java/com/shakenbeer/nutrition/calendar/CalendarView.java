package com.shakenbeer.nutrition.calendar;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.shakenbeer.nutrition.CarbculatorApplication;
import com.shakenbeer.nutrition.model.Day;
import com.shakenbeer.nutrition.util.ui.EndlessRecyclerViewScrollListener;

import java.util.List;

import javax.inject.Inject;

public class CalendarView extends RecyclerView implements CalendarContract.View {

    @Inject
    CalendarContract.Presenter presenter;
    @Inject
    DayAdapter adapter;

    public CalendarView(Context context) {
        super(context);
        DaggerCalendarComponent.builder()
                .applicationComponent(CarbculatorApplication.get(context).getComponent())
                .calendarModule(new CalendarModule())
                .build()
                .inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        setLayoutManager(layoutManager);
        addItemDecoration(new DividerItemDecoration(context, layoutManager.getOrientation()));
        setAdapter(adapter);

        addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("CalendarView", "onLoadMore");
                presenter.obtainDays();
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.attachView(this);
        presenter.obtainDays();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }

    @Override
    public void showDays(List<Day> days) {
        adapter.addItems(days);
    }

    @Override
    public void showDayUi(Day day) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
