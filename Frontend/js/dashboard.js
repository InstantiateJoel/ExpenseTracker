const incomeTabButton = document.getElementById("show-income");
const expenseTabButton = document.getElementById("show-expenses");
document.addEventListener("DOMContentLoaded", async () => {
    await globalInit();
    await loadExpenses();
});

incomeTabButton.addEventListener("click", loadIncomes);
expenseTabButton.addEventListener("click", loadExpenses);

/**
 * Loads all user expenses from the API and renders them in the UI
 * @returns { Promise<void> }
 */
async function loadExpenses() {
  const res = await getUserExpenses();
  if (res.success) {
    renderUserExpenses(res.data);
  }
}

/**
 * Loads all user incomes from the API and renders them in the UI
 * @returns { Promise<void> }
 */
async function loadIncomes() {
  const res = await getUserIncomes();
  if (res.success) {
    renderUserIncomes(res.data);
  }
}