package model;

public interface Model {

    int getGminyRowCount();

    int getMiejscowosciByGminyRowCount();

    int getMiejscowosciRowCount();

    int getUliceByMiejscowosciRowCount();

    int getUliceByGminyRowCount();

    int getUliceRowCount();

    int getPrzystankiByUliceRowCount();

    int getPrzystankiByMiejscowosciRowCount();

    int getPrzystankiByGminyRowCount();

    int getPrzystankiRowCount();

    int getLinieRowCount();

    int getPodlinieByLinieRowCount();

    int getPodlinieRowCount();

    int getRozkladyJazdyByPodlinieRowCount();

    int getRozkladyJazdyByLinieRowCount();

    int getRozkladyJazdyByPrzystankiRowCount();

    int getRozkladyJazdyByUliceRowCount();

    int getRozkladyJazdyByMiejscowosciRowCount();

    int getRozkladyJazdyByGminyRowCount();

    int getRozkladyJazdyRowCount();

    int getDniKursowaniaRowCount();

    int getKursyByDniKursowaniaRowCount();

    int getKursyByRozkladyJazdyRowCount();

    int getKursyByPodlinieRowCount();

    int getKursyByLinieRowCount();

    int getKursyByPrzystankiRowCount();

    int getKursyByUliceRowCount();

    int getKursyByMiejscowosciRowCount();

    int getKursyByGminyRowCount();

    int getKursyRowCount();

    int getLogiRowCount();

    Object getGminyValueAt();

    Object getMiejscowosciByGminyValueAt();

    Object getMiejscowosciValueAt();

    Object getUliceByMiejscowosciValueAt();

    Object getUliceByGminyValueAt();

    Object getUliceValueAt();

    Object getPrzystankiByUliceValueAt();

    Object getPrzystankiByMiejscowosciValueAt();

    Object getPrzystankiByGminyValueAt();

    Object getPrzystankiValueAt();

    Object getLinieValueAt();

    Object getPodlinieByLinieValueAt();

    Object getPodlinieValueAt();

    Object getRozkladyJazdyByPodlinieValueAt();

    Object getRozkladyJazdyByLinieValueAt();

    Object getRozkladyJazdyByPrzystankiValueAt();

    Object getRozkladyJazdyByUliceValueAt();

    Object getRozkladyJazdyByMiejscowosciValueAt();

    Object getRozkladyJazdyByGminyValueAt();

    Object getRozkladyJazdyValueAt();

    Object getDniKursowaniaValueAt();

    Object getKursyByDniKursowaniaValueAt();

    Object getKursyByRozkladyJazdyValueAt();

    Object getKursyByPodlinieValueAt();

    Object getKursyByLinieValueAt();

    Object getKursyByPrzystankiValueAt();

    Object getKursyByUliceValueAt();

    Object getKursyByMiejscowosciValueAt();

    Object getKursyByGminyValueAt();

    Object getKursyValueAt();

    Object getLogiValueAt();
}
