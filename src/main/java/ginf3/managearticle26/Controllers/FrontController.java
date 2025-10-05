package ginf3.managearticle26.Controllers;

import ginf3.managearticle26.Model.Article;
import ginf3.managearticle26.Model.DaoArticle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/articles/*")
public class FrontController extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/views/article.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Article article)
            throws ServletException, IOException {
        request.setAttribute("article", article);
        request.getRequestDispatcher("//WEB-INF/views/article.jsp").forward(request, response);
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
        Article article = new Article(code, designation, prix);
        dao.update(article);
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
