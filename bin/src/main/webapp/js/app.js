// Auto-dismiss alerts after 4 seconds
document.addEventListener('DOMContentLoaded', function () {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.transition = 'opacity .5s';
            alert.style.opacity = '0';
            setTimeout(function () { alert.remove(); }, 500);
        }, 4000);
    });

    // Confirm delete on all danger links with data-confirm
    document.querySelectorAll('[data-confirm]').forEach(function (el) {
        el.addEventListener('click', function (e) {
            if (!confirm(el.getAttribute('data-confirm'))) e.preventDefault();
        });
    });

    // Highlight active nav link
    const currentPath = window.location.pathname;
    document.querySelectorAll('.navbar-menu a').forEach(function (link) {
        if (currentPath.includes(link.getAttribute('href'))) {
            link.style.background = '#1e293b';
            link.style.color = '#fff';
        }
    });
});
