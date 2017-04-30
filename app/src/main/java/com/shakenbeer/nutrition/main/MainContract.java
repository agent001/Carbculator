package com.shakenbeer.nutrition.main;


import com.shakenbeer.nutrition.presentation.BasePresenter;
import com.shakenbeer.nutrition.presentation.MvpView;

interface MainContract {
    interface View extends MvpView {
        void showCalendarUi();
        void showFoodListUi();
        void showStatisticUi();
        void showSettingsUi();
        void showNewMealUi();
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void onShow();
        abstract void onCalendarClick();
        abstract void onFoodListClick();
        abstract void onStatisticsClick();
        abstract void onSettingsClick();
        abstract void onAddMealClick();
    }
}
