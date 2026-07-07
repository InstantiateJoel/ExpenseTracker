let translations = {};

/**
 * Initializes the application's language setting.
 * 
 * Checks localStorage for a previously selected language.
 * If none exists, it sets and returns the default language ("en-US")
 * @returns { string } The active language code (e.g. "en-US", "de-DE")
 */
function initLanguage() {
    const saved = localStorage.getItem("language");

    if (!saved) {
        localStorage.setItem("language", "en-US");
        return "en-US";
    }

    return saved;
}

/**
 * Saves the preferred language in the local storage
 * @param { string } language 
 */
function setLanguage(language) {
    currentLanguage = language
    localStorage.setItem("language", language);
}

/**
 * Loads translation data for the current language.
 *
 * Fetches the corresponding JSON file based on the normalized
 * language code (e.g. "en-US" → "en").
 * Stores the result in the global `translations` object
 * and applies it to the DOM.
 *
 * @async
 * @function loadTranslations
 * @returns {Promise<void>}
 */
async function loadTranslations() {
    try {
        const response = await fetch(`./locales/${normalizeLanguage(currentLanguage)}.json`);

        if (!response.ok) {
            return;
        }

        translations = await response.json();

    } catch (error) {
        return
    }
}

/**
 * Normalizes a language code to its base form
 * 
 * Examples:
 * "en-US" -> "en"
 * "de-DE" -> "de"
 * 
 * @param { string } language - Full language code
 * @returns { string } Normalized base language code
 */
function normalizeLanguage(language) {
    return language.split("-")[0];
}

/**
 * Loads
 * @param {*} key 
 * @returns 
 */
function t(key) {
    return key.split(".").reduce((obj, k) => obj[k], translations);
}