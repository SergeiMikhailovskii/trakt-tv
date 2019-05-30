package com.mikhailovskii.trakttv.ui.movie_detail;

import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface MovieDetailContract {

    interface MovieDetailView extends MvpView{
        void onExtendedInfoGetSuccess();
    }

    interface MovieDetailPresenter extends MvpPresenter<MovieDetailView>{

        void getExtendedInfo();

    }

}
