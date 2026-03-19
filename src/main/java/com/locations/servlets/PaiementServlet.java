package com.locations.servlets;

import com.locations.services.ContratLocationService;
import com.locations.services.PaiementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/paiements/*")
public class PaiementServlet extends HttpServlet {

    private final PaiementService service = new PaiementService();
    private final ContratLocationService contratService = new ContratLocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            req.setAttribute("paiements", service.listerTous());
            req.getRequestDispatcher("/WEB-INF/views/locations/paiements.jsp").forward(req, resp);
        } else if (path.equals("/en-retard")) {
            req.setAttribute("paiements", service.listerEnRetard());
            req.setAttribute("titre", "Paiements en retard");
            req.getRequestDispatcher("/WEB-INF/views/locations/paiements.jsp").forward(req, resp);
        } else if (path.equals("/nouveau")) {
            req.setAttribute("contrats", contratService.listerActifs());
            req.getRequestDispatcher("/WEB-INF/views/locations/paiement-form.jsp").forward(req, resp);
        } else if (path.startsWith("/contrat/")) {
            Long contratId = Long.parseLong(path.substring("/contrat/".length()));
            req.setAttribute("paiements", service.listerParContrat(contratId));
            contratService.trouverParId(contratId).ifPresent(c -> req.setAttribute("contrat", c));
            req.getRequestDispatcher("/WEB-INF/views/locations/paiements.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("payer".equals(action)) {
                service.enregistrerPaiement(
                    Long.parseLong(req.getParameter("contratId")),
                    new BigDecimal(req.getParameter("montant")),
                    req.getParameter("dateEcheance"),
                    req.getParameter("periodeMois"),
                    req.getParameter("modePaiement"),
                    req.getParameter("commentaire")
                );
                req.getSession().setAttribute("message", "Paiement enregistré avec succès");
            }
            resp.sendRedirect(req.getContextPath() + "/paiements/");
        } catch (Exception e) {
            req.setAttribute("erreur", e.getMessage());
            req.setAttribute("contrats", contratService.listerActifs());
            req.getRequestDispatcher("/WEB-INF/views/locations/paiement-form.jsp").forward(req, resp);
        }
    }
}
