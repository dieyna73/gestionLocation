package com.locations.servlets;

import com.locations.entities.Immeuble;
import com.locations.services.ImmeubleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/immeubles/*")
public class ImmeubleServlet extends HttpServlet {

    private final ImmeubleService service = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            lister(req, resp);
        } else if (path.equals("/nouveau")) {
            req.getRequestDispatcher("/WEB-INF/views/immeubles/form.jsp").forward(req, resp);
        } else if (path.startsWith("/modifier/")) {
            Long id = Long.parseLong(path.substring("/modifier/".length()));
            service.trouverParId(id).ifPresent(i -> req.setAttribute("immeuble", i));
            req.getRequestDispatcher("/WEB-INF/views/immeubles/form.jsp").forward(req, resp);
        } else if (path.startsWith("/detail/")) {
            Long id = Long.parseLong(path.substring("/detail/".length()));
            service.trouverParId(id).ifPresent(i -> req.setAttribute("immeuble", i));
            req.getRequestDispatcher("/WEB-INF/views/immeubles/detail.jsp").forward(req, resp);
        } else if (path.startsWith("/supprimer/")) {
            Long id = Long.parseLong(path.substring("/supprimer/".length()));
            try {
                service.supprimerImmeuble(id);
                req.getSession().setAttribute("message", "Immeuble supprimé avec succès");
            } catch (Exception e) {
                req.getSession().setAttribute("erreur", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/immeubles/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                service.creerImmeuble(
                    req.getParameter("nom"), req.getParameter("adresse"),
                    req.getParameter("ville"), req.getParameter("codePostal"),
                    parseInt(req.getParameter("nombreUnites")),
                    req.getParameter("description"), req.getParameter("equipements"),
                    parseInt(req.getParameter("anneeConstruction")),
                    req.getParameter("proprietaire")
                );
                req.getSession().setAttribute("message", "Immeuble créé avec succès");
            } else if ("modifier".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.modifierImmeuble(id,
                    req.getParameter("nom"), req.getParameter("adresse"),
                    req.getParameter("ville"), req.getParameter("codePostal"),
                    parseInt(req.getParameter("nombreUnites")),
                    req.getParameter("description"), req.getParameter("equipements"),
                    parseInt(req.getParameter("anneeConstruction")),
                    req.getParameter("proprietaire")
                );
                req.getSession().setAttribute("message", "Immeuble modifié avec succès");
            }
            resp.sendRedirect(req.getContextPath() + "/immeubles/");
        } catch (Exception e) {
            req.setAttribute("erreur", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/immeubles/form.jsp").forward(req, resp);
        }
    }

    private void lister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Immeuble> immeubles = (search != null && !search.isEmpty())
                ? service.rechercher(search) : service.listerTous();
        req.setAttribute("immeubles", immeubles);
        req.setAttribute("search", search);
        req.getRequestDispatcher("/WEB-INF/views/immeubles/liste.jsp").forward(req, resp);
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}
