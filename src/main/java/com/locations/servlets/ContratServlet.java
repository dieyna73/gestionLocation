package com.locations.servlets;

import com.locations.entities.ContratLocation;
import com.locations.services.ContratLocationService;
import com.locations.services.LocataireService;
import com.locations.services.UniteLocationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/contrats/*")
public class ContratServlet extends HttpServlet {

    private final ContratLocationService service = new ContratLocationService();
    private final UniteLocationService uniteService = new UniteLocationService();
    private final LocataireService locataireService = new LocataireService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            req.setAttribute("contrats", service.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/locations/liste.jsp").forward(req, resp);
        } else if (path.equals("/nouveau")) {
            req.setAttribute("unites", uniteService.listerDisponibles());
            req.setAttribute("locataires", locataireService.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/locations/form.jsp").forward(req, resp);
        } else if (path.startsWith("/detail/")) {
            Long id = Long.parseLong(path.substring("/detail/".length()));
            service.trouverParId(id).ifPresent(c -> req.setAttribute("contrat", c));
            req.getRequestDispatcher("/WEB-INF/views/locations/detail.jsp").forward(req, resp);
        } else if (path.startsWith("/resilier/")) {
            Long id = Long.parseLong(path.substring("/resilier/".length()));
            try {
                service.modifierStatut(id, ContratLocation.Statut.RESILIE);
                req.getSession().setAttribute("message", "Contrat résilié");
            } catch (Exception e) {
                req.getSession().setAttribute("erreur", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/contrats/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            service.creerContrat(
                Long.parseLong(req.getParameter("uniteId")),
                Long.parseLong(req.getParameter("locataireId")),
                req.getParameter("dateDebut"),
                req.getParameter("dateFin"),
                new BigDecimal(req.getParameter("loyerMensuel")),
                req.getParameter("depotGarantie") != null && !req.getParameter("depotGarantie").isEmpty()
                        ? new BigDecimal(req.getParameter("depotGarantie")) : null,
                req.getParameter("conditions")
            );
            req.getSession().setAttribute("message", "Contrat créé avec succès");
            resp.sendRedirect(req.getContextPath() + "/contrats/");
        } catch (Exception e) {
            req.setAttribute("erreur", e.getMessage());
            req.setAttribute("unites", uniteService.listerDisponibles());
            req.setAttribute("locataires", locataireService.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/locations/form.jsp").forward(req, resp);
        }
    }
}
