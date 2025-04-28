package cz.uhk.stag.main;

import com.google.gson.annotations.SerializedName;

class RozvrhAkce {
    @SerializedName("predmet")
    String predmet;
    @SerializedName("nazev")
    String nazev;
    @SerializedName("katedra")
    String katedra;
    @SerializedName("typAkce")
    String typAkce;
    @SerializedName("den")
    String den;
    @SerializedName("hodinaSkutOd")
    HodinaSkutOd hodinaSkutOd;
    @SerializedName("hodinaSkutDo")
    HodinaSkutDo hodinaSkutDo;

    @SerializedName("ucitel")
    Ucitel ucitel;

    static class Ucitel {
        @SerializedName("jmeno")
        String jmeno;
        @SerializedName("prijmeni")
        String prijmeni;
        @SerializedName("titulPred")
        String titulPred;
        @SerializedName("titulZa")
        String titulZa;
    }

    static class HodinaSkutOd {
        @SerializedName("value")
        String value;
    }

    static class HodinaSkutDo {
        @SerializedName("value")
        String value;
    }

}
