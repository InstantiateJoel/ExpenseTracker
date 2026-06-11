const selectMainCategory = document.getElementById("main-category");
const selectSubCategory = document.getElementById("sub-category");
const amountInput = document.getElementById("amount");
const descriptionInput = document.getElementById("description");
const dateInput = document.getElementById("payment-date");
const titleInput = document.getElementById("income-title");
const incomeAmountInput = document.getElementById("income-amount");
const incomeDate = document.getElementById("income-date");
const expenseForm = document.querySelector(".expense-form");
const incomeForm = document.querySelector(".income-form");
document.addEventListener("DOMContentLoaded", init);
expenseForm.addEventListener("submit", handleCreateExpense);
incomeForm.addEventListener("submit", handleCreateIncome);

if (selectMainCategory) {
    selectMainCategory.addEventListener("change", handleMainCategoryChange);
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
    resetSubCategorySelect();
    const mainCategoryId = event.target.value;

    const result = await getSubCategories(mainCategoryId);

    if (!result.success) {
        return;
    }

    renderSubCategoryOptions(result.data);
}

/**
 * Resets the subcategory dropdown and pre selects the default option
 */
function resetSubCategorySelect() {
    selectSubCategory.innerHTML = `
    <option value="" disabled selected>Select sub category</option>`;
}

/**
 * Handles creating a new expense 
 * @param {Event } e 
 * @returns {Promise<void>}
 */
async function handleCreateExpense(e) {
    e.preventDefault();

    const expense = {
        category: selectSubCategory.value,
        description: descriptionInput.value || "",
        amount: Number(amountInput.value),
        paymentDate: dateInput.value
    };

    const result = await addNewExpense(expense);

    if (!result.success) {
        showErrorMessage(result.message);
        return;
    }

    expenseForm.reset();
    resetSubCategorySelect();
}

/**
 * Handles creating a new income
 * @param {Event} e
 * @returns {Promise<void>} 
 */
async function handleCreateIncome(e) {
    e.preventDefault();

    const income = {
        title: titleInput.value,
        amount: incomeAmountInput.value,
        incomeDate: incomeDate.value
    };

    const result = await addNewIncome(income);

    if (!result.success) {
        showErrorMessage(result.message);
    }
}