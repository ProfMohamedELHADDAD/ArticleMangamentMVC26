package ginf3.managearticle26.Model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DaoArticle implements IDaoArticle<Article>{

    private List<Article> articles = new CopyOnWriteArrayList<>();
    private static DaoArticle instance = new DaoArticle();

    private DaoArticle() {

        articles.add(new Article("Art1","Article1",120));
        articles.add(new Article("Art2","Article2",150));
        articles.add(new Article("Art3","Article3",180));
    }
    // Singleton simple pour partager la même instance dans toute l'application
    public static DaoArticle getInstance() {
        return instance;
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public Article findByCode(String code) {
        for (Article a : articles) {
            if (a.getCode().equals(code)) return a;
        }
        return null;
    }

    public boolean add(Article article) {
        if (findByCode(article.getCode()) != null) {
            return false;
        }
        return articles.add(article);
    }

    public boolean update(Article article) {
        Article existing = findByCode(article.getCode());
        if (existing == null) return false;
        existing.setDesignation(article.getDesignation());
        existing.setPrix(article.getPrix());
        return true;
    }

    public boolean delete(String code) {
        Article a = findByCode(code);
        if (a == null) return false;
        return articles.remove(a);
    }
}
