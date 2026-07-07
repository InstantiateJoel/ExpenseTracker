/**
 * Application entry point.
 * 
 * Initializes global state (i18n),
 * loads translations, applies them to the UI
 */
async function globalInit() {
    const page = window.location.pathname;

    currentLanguage = initLanguage();
    await loadTranslations();
    applyTranslations();
}