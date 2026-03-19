package com.locations.servlets;

import com.locations.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ImmeubleService immeubleService = new ImmeubleService();
    private final UniteLocationService uniteService = new UniteLocationService();
    private final LocataireService locataireService = new LocataireService();
    private final ContratLocationService contratService = new ContratLocationService();
    private final PaiementService paiementService = new PaiementService();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("nbImmeubles", immeubleService.compterImmeubles());
        req.setAttribute("nbUnitesDisponibles", uniteService.compterParStatut("DISPONIBLE"));
        req.setAttribute("nbUnitesOccupees", uniteService.compterParStatut("OCCUPEE"));
        req.setAttribute("nbLocataires", locataireService.compterLocataires());
        req.setAttribute("nbContratsActifs", contratService.compterParStatut("ACTIF"));
        req.setAttribute("totalEncaisse", paiementService.totalEncaisse());
        req.setAttribute("paiementsEnRetard", paiementService.listerEnRetard());
        req.setAttribute("contratsActifs", contratService.listerActifs());

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}
