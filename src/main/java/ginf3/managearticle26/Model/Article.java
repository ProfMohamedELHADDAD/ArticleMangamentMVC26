package ginf3.managearticle26.Model;

import java.util.Objects;

public class Article {
    private String code;
    private String designation;
    private double prix;

    public Article() {}

    public Article(String code, String designation, double prix) {
        this.code = code;
        this.designation = designation;
        this.prix = prix;
    }

    // getters et setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getCode(), article.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
