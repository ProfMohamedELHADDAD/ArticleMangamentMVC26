package ginf3.managearticle26.Controllers;

import java.io.*;

import ginf3.managearticle26.Model.Article;
import ginf3.managearticle26.Model.DaoArticle;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.*;
/*
LIENS DANS LES JSP (Contexte de l'pplication )
Pour générer les liens vers ces URL propres depuis les JSP,
on doit tenir compte du contexte de l’application web.
Typiquement, on préfixe les URLs avec ${pageContext.request.contextPath},
qui correspond au chemin racine de l’application (ex. /monContexte)
<a href="${pageContext.request.contextPath}/app/list">Voir la liste</a>
 */
/*

Cette expression EL renvoie dynamiquement le contexte courant puis ajoute /app/list.
Si l’application est déployée sous le contexte /monApp, le lien généré sera /monApp/app/list.
Cela permet de conserver des liens indépendants du nom du contexte.
L’avantage est d’obtenir des URLs propres du type /app/list ou /app/edit?code=123
plutôt que des liens du type?action=list.
En résumé, on remplace les anciennes requêtes comme /monApp?action=list
par /monApp/app/list, gérées toutes par le Front Controller.
Cette méthode fournit des URLs plus claires et respecte mieux les conventions REST du web.
 */

@WebServlet(name = "ArticleController", value = "/articles/*")
public class ArticleController extends HttpServlet {
    private DaoArticle dao;

    @Override
    public void init() throws ServletException {
        dao = DaoArticle.getInstance();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupère la partie variable de l'URL (ex: "/list", "/new", etc.)
        // Exemple : /articles/list → pathInfo = "/list"
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            path = "/list"; // URL par défaut
        }

        switch (path) {
            case "/list":
                listArticles(request, response);
                break;
            case "/new":
                showForm(request, response, null);
                break;
            case "/create":
                createArticle(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateArticle(request, response);
                break;
            case "/delete":
                deleteArticle(request, response);
                break;
            default:
                // Pas d'action reconnue → erreur 404 ou page par défaut
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private void listArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("articles", dao.findAll());
        request.getRequestDispatcher("/views/listArticles.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Article article)
            throws ServletException, IOException {
        request.setAttribute("article", article);
        request.getRequestDispatcher("/views/articleForm.jsp").forward(request, response);
    }

    private void createArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String code = request.getParameter("code");
        String designation = request.getParameter("designation");
        double prix = Double.parseDouble(request.getParameter("prix"));

        Article article = new Article(code, designation, prix);
        boolean ok = dao.add(article);
        if (!ok) {
            request.setAttribute("error", "Un article avec ce code existe déjà.");
            showForm(request, response, article);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/articles/list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        Article a = dao.findByCode(code);
        if (a == null) {
            response.sendRedirect(request.getContextPath() + "/articles/list");
            return;
        }
        showForm(request, response, a);
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String code = request.getParameter("code");
        String designation = request.getParameter("designation");
        double prix = Double.parseDouble(request.getParameter("prix"));

        dao.update(new Article(code, designation, prix));
        response.sendRedirect(request.getContextPath() + "/articles/list");
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String code = request.getParameter("code");
        dao.delete(code);
        response.sendRedirect(request.getContextPath() + "/articles/list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
