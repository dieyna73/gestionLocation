package com.locations.servlets;

import com.locations.services.ImmeubleService;
import com.locations.services.UniteLocationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/unites/*")
public class UniteServlet extends HttpServlet {

    private final UniteLocationService service = new UniteLocationService();
    private final ImmeubleService immeubleService = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            req.setAttribute("unites", service.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/unites/liste.jsp").forward(req, resp);
        } else if (path.equals("/disponibles")) {
            String piecesStr = req.getParameter("pieces");
            String loyerMaxStr = req.getParameter("loyerMax");
            Integer pieces = (piecesStr != null && !piecesStr.isEmpty()) ? Integer.parseInt(piecesStr) : null;
            BigDecimal loyerMax = (loyerMaxStr != null && !loyerMaxStr.isEmpty()) ? new BigDecimal(loyerMaxStr) : null;
            req.setAttribute("unites", service.listerDisponiblesAvecFiltres(pieces, loyerMax));
            req.setAttribute("pieces", pieces);
            req.setAttribute("loyerMax", loyerMax);
            req.getRequestDispatcher("/WEB-INF/views/unites/disponibles.jsp").forward(req, resp);
        } else if (path.equals("/nouveau")) {
            req.setAttribute("immeubles", immeubleService.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/unites/form.jsp").forward(req, resp);
        } else if (path.startsWith("/modifier/")) {
            Long id = Long.parseLong(path.substring("/modifier/".length()));
            service.trouverParId(id).ifPresent(u -> req.setAttribute("unite", u));
            req.setAttribute("immeubles", immeubleService.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/unites/form.jsp").forward(req, resp);
        } else if (path.startsWith("/supprimer/")) {
            Long id = Long.parseLong(path.substring("/supprimer/".length()));
            try {
                service.supprimerUnite(id);
                req.getSession().setAttribute("message", "Unité supprimée avec succès");
            } catch (Exception e) {
                req.getSession().setAttribute("erreur", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/unites/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("creer".equals(action)) {
                service.creerUnite(
                    Long.parseLong(req.getParameter("immeubleId")),
                    req.getParameter("numeroUnite"),
                    Integer.parseInt(req.getParameter("nombrePieces")),
                    parseBD(req.getParameter("superficie")),
                    parseBD(req.getParameter("loyerMensuel")),
                    parseBD(req.getParameter("chargesMensuelles")),
                    req.getParameter("description"),
                    req.getParameter("equipements")
                );
                req.getSession().setAttribute("message", "Unité créée avec succès");
            } else if ("modifier".equals(action)) {
                service.modifierUnite(
                    Long.parseLong(req.getParameter("id")),
                    req.getParameter("numeroUnite"),
                    Integer.parseInt(req.getParameter("nombrePieces")),
                    parseBD(req.getParameter("superficie")),
                    parseBD(req.getParameter("loyerMensuel")),
                    parseBD(req.getParameter("chargesMensuelles")),
                    req.getParameter("description"),
                    req.getParameter("equipements"),
                    req.getParameter("statut")
                );
                req.getSession().setAttribute("message", "Unité modifiée avec succès");
            }
            resp.sendRedirect(req.getContextPath() + "/unites/");
        } catch (Exception e) {
            req.setAttribute("erreur", e.getMessage());
            req.setAttribute("immeubles", immeubleService.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/unites/form.jsp").forward(req, resp);
        }
    }

    private BigDecimal parseBD(String s) {
        try { return (s != null && !s.isEmpty()) ? new BigDecimal(s) : null; } catch (Exception e) { return null; }
    }
}
