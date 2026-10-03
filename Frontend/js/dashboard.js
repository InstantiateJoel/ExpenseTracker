const incomeTabButton = document.getElementById("show-income");
const expenseTabButton = document.getElementById("show-expenses");
const monthDropdown = document.getElementById("month");
const yearDropdown = document.getElementById("year");
let currentView = "expense";

incomeTabButton.addEventListener("click", async () => {
  currentView = "incomes";
  await loadIncomes();
});

expenseTabButton.addEventListener("click", async () => {
  currentView = "expenses";
  loadExpenses()
});

yearDropdown.addEventListener("change", filterByYearAndMonth);
monthDropdown.addEventListener("change", filterByYearAndMonth)

/**
 * Loads all user expenses or incomes from the api by the selected year and month and renders them in the UI
 * @returns { Promise<void> }
 */
async function filterByYearAndMonth() {
  const year = yearDropdown.value;
  const month = monthDropdown.value

  if (!year || !month) {
    return;
  }

  if (currentView === "expense") {
    const res = await getExpenseByYearAndMonth(year, month);


    if (!res.success) {
      showErrorMessage(res.message);
      return;
    }

    renderUserExpenses(res.data);
  } else {
    const res = await getIncomeByYearAndMonth(year, month);

    if (!res.success) {
      showErrorMessage(res.message);
      return;
    }

    renderUserIncomes(res.data);
  }
}

document.addEventListener("DOMContentLoaded", async () => {
  await globalInit();
  await loadExpenses();
  await loadFirstExpense();
});

/**
 * Loads all user expenses from the API and renders them in the UI
 * @returns { Promise<void> }
 */
async function loadExpenses() {
  const res = await getUserExpenses();

  if (!res.success) {
    showErrorMessage(res.message);
    return;
  }

  renderUserExpenses(res.data);
}

/**
 * Loads all user incomes from the API and renders them in the UI
 * @returns { Promise<void> }
 */
async function loadIncomes() {
  const res = await getUserIncomes();

  if (!res.success) {
    showErrorMessage(res.message);
    return;
  }

  renderUserIncomes(res.data);
}

/**
 * Loads the user's first expense and uses its payment date to populate the year dropdown
 * 
 * Displays an error message if the request fails
 * 
 * @returns { Promise <void> }
 */
async function loadFirstExpense() {
  const res = await getFirstUserExpense();

  if (!res.success) {
    showErrorMessage(res.message);
    return;
  }

  if (res.data != null) {
    const year = new Date(res.data.paymentDate).getFullYear();

    renderYearDropdown(year);
  }
}