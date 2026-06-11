/**
 * Initializes page-specific logic based on current route 
 */
async function init() {
    const page = window.location.pathname

    if (page.includes("entry.html")) {
        const result = await getMainCategories();

        if (!result.success) {
            return;
        }

        renderMainCategories(result.data);
    }

    if (page.includes("dashboard.html")) {
        const result = await getUserExpenses();

        if (!result.success) {
            return;
        }

        renderUserExpenses(result.data);
    }
}