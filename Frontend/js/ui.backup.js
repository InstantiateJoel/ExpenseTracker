const errorMessage = document.querySelector(".error-message");
const form = document.querySelector("form");
const usernameInput = document.querySelector(".username");
const passwordInput = document.querySelector(".password");
const isRegisterPage = !!document.querySelector(".password-confirm");
const passwordConfirmInput = document.querySelector(".password-confirm");
const selectMainCategory = document.getElementById("main-category");
const selectSubCategory = document.getElementById("sub-category");


document.addEventListener("DOMContentLoaded", init);

if (selectMainCategory) {
  selectMainCategory.addEventListener("change", handleMainCategoryChange);
}

/**
 * When the user registers or login and clicks on the submit button
 */
form.addEventListener("submit", handleSubmit);


/**
 * Initializes page-specific logic based on current route 
 */
async function init() {
  if (window.location.pathname.includes("entry.html")) {
    const mainCategoriesJson = await getMainCategories();
    addMainCategoryOptions(mainCategoriesJson);
  }
}

/**
 * Handles the main category change event by coordinating
 * fetching subcategories and updating the UI.
 *
 * Acts as a controller function that delegates work to
 * the API and UI layer.
 *
 * @param {Event} event - The change event from the main category select element.
 * @returns {Promise<void>} Resolves after subcategories have been fetched and rendered.
 */
async function handleMainCategoryChange(event) {
  const mainCategoryId = event.target.value;

  const subCategoriesJSON = await fetchSubcategories(mainCategoryId);

  addSubCategoryOptions(subCategoriesJSON);
}

/***
 * Handles form submissions for login and registration
 * Delegates to login or register handler based on form type
 */
async function handleSubmit(e) {
  e.preventDefault();

  const username = usernameInput.value;
  const password = passwordInput.value;

  if (isRegisterPage) {
    const passwordConfirm = passwordConfirmInput.value;

    await handleRegister(username, password, passwordConfirm);
    return;
  }

  await handleLogin(username, password);
}

/**
 * Handles user registration flow including validation and API request
 * @param {string} username 
 * @param {string} password 
 */
async function handleRegister(username, password, passwordConfirm) {
  const validation = validatePassword(password, passwordConfirm);

  if (!validation.valid) {
    errorMessage.textContent = validation.message;
    return;
  }

  const result = await registerUser(username, password, passwordConfirm);


  if (!result.success) {
    errorMessage.textContent = result.message;
    return;
  }

  finishAuth(username);
}

/**
 * Handles user login flow including validation and API request
 * @param {string} username
 * @param {string} password
 * @returns 
 */
async function handleLogin(username, password) {
  const result = await loginUser(username, password);

  if (!result.success) {
    errorMessage.textContent = result.message;
    return;
  }

  finishAuth(username);
}

/**
 * Completes authentication and redirects user to the main page
 * @param {string} username
 */
function finishAuth(username) {
  localStorage.setItem("username", username);
  errorMessage.textContent = "";
  window.location.href = "entry.html"
}

/**
 * Fetches subcategories for a given main category ID
 * Wrapper around the API call getSubcategories
 * @param {string} mainCategoryId - The ID of the selected main category
 * @returns {Promise<Object>} API response containing subcategory data
 */
async function fetchSubcategories(mainCategoryId) {
  return await getSubCategories(mainCategoryId);
}

/**
 * Adds the main categories as options to the select element
 * @param {{
 *  success: boolean,
 *  data: Array<{
 *    categoryId: string,
 *    localizedName: string
 * }>
 * }} mainCategoriesJson
 */
function addMainCategoryOptions(mainCategoriesJson) {
  for (let i = 0; i < mainCategoriesJson.data.length; i++) {
    const addOption = document.createElement("option");
    addOption.textContent = mainCategoriesJson.data[i].localizedName;
    addOption.value = mainCategoriesJson.data[i].categoryId;
    selectMainCategory.appendChild(addOption);
  }
}

/**
 * Adds the sub categories as options to the select element
 * @param {{
 *  success: boolean,
 *  data: Array<{
 *    categoryId: string,
 *    localizedName: string
 * }>
 * }} subCategoriesJSON
 */
function addSubCategoryOptions(subCategoriesJSON) {
  for (let i = 0; i < subCategoriesJSON.data.length; i++) {
    const addOption = document.createElement("option");
    addOption.textContent = subCategoriesJSON.data[i].localizedName;
    addOption.value = subCategoriesJSON.data[i].categoryId;
    selectSubCategory.appendChild(addOption);
  }
}

/**
 * Todos:
 * 1. Refactor:
 * 1.1 Refactor UI.js into smaller files -> login.js/register.js/entry.js/dashboard.js
 * why? -> i always select inputs/fields that are **NOT** there! and the UI.js file should only do UI shit and not this and that -> mby ask chatty what to do
 * 1.2 Check if API.js needs refactoring as well
 * 
 * 2. Get all the field inputs from Expense
 * 2.1 Give the button a functionality
 * 2.2 Make the request to the database to save a new expense
 * 
 * 3. Make the same shit for income
 * 
 * 4. Get request for user expenses
 * 
 * 5. Find out what to do with the single categories (because the sub categories are a required field!) 
 */