const selectMainCategory = document.querySelector("#main-category");
const selectSubCategory = document.querySelector("#sub-category");
document.addEventListener("DOMContentLoaded", async () => {
    await globalInit();
    init();
});

let currentId = null;
let currentType = null;

if (selectMainCategory && selectSubCategory) {
    selectMainCategory.addEventListener("change", handleMainCategoryChange);
}

/**
 * Handles changes of the main category select
 * Clears and resets the subcategory dropdown, then loads
 * and renders subcategories based on the selected main category
 * @param { Event } event 
 * @returns { Promise<void> }
 */
async function handleMainCategoryChange(event) {
    const mainCategoryId = event.target.value;

    selectSubCategory.innerHTML = `
        <option value="" disabled selected data-i18n="entry.expense.form.subCategoryPlaceholder"></option>`;

    applyTranslations();
    const result = await getSubCategories(mainCategoryId);

    if (!result.success) return;

    renderSubCategoryOptions(result.data ?? []);
}

const button = document.querySelector("form");
if (button) {
    button.addEventListener("submit", handleSubmit);
}

/**
 * Handles form submissions for updating either income or expense entry.
 * Determines which submit button was used and sends the corresponding update request
 * Redirects back to the dashboard on success
 * @param { Event } e 
 */
async function handleSubmit(e) {
    e.preventDefault();

    const submitter = e.submitter;

    if (submitter.id === "update-income") {
        const income = getFormData("income");
        const response = await updateIncome(currentId, income);

        if (!response.success) {
            showErrorMessage(response.message);
        }

        window.location.replace("dashboard.html");
    }

    if (submitter.id === "update-expense") {
        const expense = getFormData("expense");

        const payload = {
            amount: expense.amount,
            description: expense.description,
            paymentDate: expense.paymentDate,
            category: expense.subCategory
        };

        const response = await updateExpense(currentId, payload);

        console.log(response);
        if (!response.success) {
            showErrorMessage(response.message);
        }

        window.location.replace("dashboard.html");
    }
}

/**
 * Retrieves the URL params when site is loaded, to get information from the selected income/expense
 */
function init() {
    const params = new URLSearchParams(location.search);

    currentId = params.get("id");
    currentType = params.get("type");

    typeHandler(currentType, currentId);
}

/**
 * Decides which function is called, to render the right form
 * @param { string } type - The type from the URL params (e.g: income/expense) 
 * @param { string } id - The incomeId from the selected income/expense
 */
async function typeHandler(type, id) {
    if (type === "income") {
        const incomeForm = document.querySelector(".income-section");
        incomeForm.classList.add("open");

        setState(type);

        const incomeDetails = await loadDetails(id);

        fillIncomeForm(incomeDetails);
    }

    if (type === "expense") {
        const expenseForm = document.querySelector(".expense-section");
        expenseForm.classList.add("open");

        setState(type);

        const expenseDetails = await loadDetails(id);

        const mainCategories = await getMainCategories();
        renderMainCategories(mainCategories.data);

        selectMainCategory.value = expenseDetails.mainCategory;

        const subCategories = await getSubCategories(expenseDetails.mainCategory);
        renderSubCategoryOptions(subCategories.data);
        requestAnimationFrame(() => {
            selectSubCategory.value = expenseDetails.category;
        });

        fillExpenseForm(expenseDetails);
    }
}

/**
 * Collects form data from either the income or expense section
 * Only enabled (non-disabled) fields are included in the returned object
 * @param { string } type - The form type ("income" or "expense") 
 * @returns { Object } Key-value map of form field names and values
 */
function getFormData(type) {
    if (type === "income") {
        const section = document.querySelector("." + type + "-section");

        const data = {};

        section.querySelectorAll("input, select, textarea").forEach(el => {
            if (!el.disabled) {
                data[el.name] = el.value;
            }
        });

        return data;
    }

    if (type === "expense") {
        const section = document.querySelector("." + type + "-section");

        const data = {};

        section.querySelectorAll("input, select, textarea").forEach(el => {
            if (!el.disabled) {
                data[el.name] = el.value;
            }
        });

        return data;
    }
}

/**
 * Disables all input, select, and textarea fields of the inactive form section
 * Used to prevent editing of the non active form (income or expense)
 * 
 * @param { string } type - The active form type ("income" or "expense") 
 */
function setState(type) {
    const inactive = type === "income" ? "expense-section" : "income-section";

    const section = document.querySelector("." + inactive);
    const fields = section.querySelectorAll("input, select, textarea");

    fields.forEach(field => {
        field.disabled = true;
    });
}

/**
 * Loads income or expense details by ID and handles error messaging
 * 
 * @param { string } id - Either income or expense ID 
 * @returns { Promise<Object> } Income or Expense data object 
 */
async function loadDetails(id) {
    let result = null;

    if (currentType === "income") {
        result = await getIncomeDetails(id);

        if (!result.success) {
            showErrorMessage(result.message);
        }
    }

    if (currentType === "expense") {
        result = await getExpenseDetails(id);

        if (!result.success) {
            showErrorMessage(result.message);
        }
    }

    return result.data;
}

/**
 * Populates the income form with the provided income data
 * @param { Object } incomeDetails - Response object returned from getIncomeDetails
 */
function fillIncomeForm(incomeDetails) {
    const form = document.querySelector(".income-section");

    form.querySelector("#income-title").value = incomeDetails.title ?? "";
    form.querySelector("#income-amount").value = incomeDetails.amount ?? "";
    form.querySelector("#income-date").value = incomeDetails.incomeDate ?? "";
}

function fillExpenseForm(expenseDetails) {
    const form = document.querySelector(".expense-section");

    form.querySelector("#main-category").value = expenseDetails.mainCategory ?? "";
    form.querySelector("#sub-category").value = expenseDetails.subCategory ?? "";
    form.querySelector("#amount").value = expenseDetails.amount ?? "";
    form.querySelector("#description").value = expenseDetails.description ?? "";
    form.querySelector("#payment-date").value = expenseDetails.paymentDate ?? "";
}