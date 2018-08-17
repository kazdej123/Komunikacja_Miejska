package controller;

public interface Controller {
    void exit();

    void login();


    //-------- show --------//

    void showGminy();

    void showMiejscowosciByGminy();

    void showMiejscowosci();

    void showUliceByMiejscowosci();

    void showUliceByGminy();

    void showUlice();

    void showPrzystankiByUlice();

    void showPrzystankiByMiejscowosci();

    void showPrzystankiByGminy();

    void showPrzystanki();

    void showLinie();

    void showPodlinieByLinie();

    void showPodlinie();

    void showRozkladyJazdyByPodlinie();

    void showRozkladyJazdyByLinie();

    void showRozkladyJazdyByPrzystanki();

    void showRozkladyJazdyByUlice();

    void showRozkladyJazdyByMiejscowosci();

    void showRozkladyJazdyByGminy();

    void showRozkladyJazdy();

    void showDniKursowania();

    void showKursyByDniKursowania();

    void showKursyByRozkladyJazdy();

    void showKursyByPodlinie();

    void showKursyByLinie();

    void showKursyByPrzystanki();

    void showKursyByUlice();

    void showKursyByMiejscowosci();

    void showKursyByGminy();

    void showKursy();

    void showLogi();
}
