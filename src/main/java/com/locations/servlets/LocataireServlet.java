package com.locations.servlets;

import com.locations.services.LocataireService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/locataires/*")
public class LocataireServlet extends HttpServlet {

    private final LocataireService service = new LocataireService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            String search = req.getParameter("search");
            req.setAttribute("locataires", search != null && !search.isEmpty()
                    ? service.rechercher(search) : service.listerTous());
            req.setAttribute("search", search);
            req.getRequestDispatcher("/WEB-INF/views/locataires/liste.jsp").forward(req, resp);
        } else if (path.equals("/inscription")) {
            req.getRequestDispatcher("/WEB-INF/views/locataires/inscription.jsp").forward(req, resp);
        } else if (path.startsWith("/profil/")) {
            Long id = Long.parseLong(path.substring("/profil/".length()));
            service.trouverParId(id).ifPresent(l -> req.setAttribute("locataire", l));
            req.getRequestDispatcher("/WEB-INF/views/locataires/profil.jsp").forward(req, resp);
        } else if (path.startsWith("/modifier/")) {
            Long id = Long.parseLong(path.substring("/modifier/".length()));
            service.trouverParId(id).ifPresent(l -> req.setAttribute("locataire", l));
            req.getRequestDispatcher("/WEB-INF/views/locataires/form.jsp").forward(req, resp);
        } else if (path.startsWith("/supprimer/")) {
            Long id = Long.parseLong(path.substring("/supprimer/".length()));
            try {
                service.supprimerLocataire(id);
                req.getSession().setAttribute("message", "Locataire supprimé");
            } catch (Exception e) {
                req.getSession().setAttribute("erreur", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/locataires/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("inscrire".equals(action)) {
                service.inscrireLocataire(
                    req.getParameter("nom"), req.getParameter("prenom"),
                    req.getParameter("email"), req.getParameter("telephone"),
                    req.getParameter("dateNaissance"), req.getParameter("pieceIdentite"),
                    req.getParameter("numeroPiece"), req.getParameter("adresse"),
                    req.getParameter("profession"), req.getParameter("username"),
                    req.getParameter("motDePasse")
                );
                req.getSession().setAttribute("message", "Inscription réussie");
                resp.sendRedirect(req.getContextPath() + "/locataires/");
            } else if ("modifier".equals(action)) {
                service.modifierLocataire(
                    Long.parseLong(req.getParameter("id")),
                    req.getParameter("nom"), req.getParameter("prenom"),
                    req.getParameter("email"), req.getParameter("telephone"),
                    req.getParameter("dateNaissance"), req.getParameter("pieceIdentite"),
                    req.getParameter("numeroPiece"), req.getParameter("adresse"),
                    req.getParameter("profession")
                );
                req.getSession().setAttribute("message", "Profil mis à jour");
                resp.sendRedirect(req.getContextPath() + "/locataires/");
            }
        } catch (Exception e) {
            req.setAttribute("erreur", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/locataires/inscription.jsp").forward(req, resp);
        }
    }
}
