const redirect = document.querySelector(".redirect");
const transactionList = document.getElementById("transaction-list");

if (transactionList) {
  transactionList.addEventListener("click", (e) => {
    document.querySelectorAll(".expense-card, .income-card").forEach(c => c.classList.remove("open"));
    const card = e.target.closest(".expense-card, .income-card");

    if (!card) return;

    card.classList.add("open")
  })
}

if (redirect) {
  redirect.addEventListener("click", handleRedirect);
}

/**
 * Adds the main categories as options to the select element
 * @param {Array> {
 *    categoryId: string,
 *    localizedName: string
 * }>} mainCategories
 */
function renderMainCategories(mainCategories) {
  for (let i = 0; i < mainCategories.length; i++) {
    const addOption = document.createElement("option");
    addOption.textContent = mainCategories[i].localizedName;
    addOption.value = mainCategories[i].categoryId;
    selectMainCategory.appendChild(addOption);
  }
}

/**
 * Adds the sub categories as options to the select element
 *  * @param {Array> {
 *    categoryId: string,
 *    localizedName: string
 * }>} subCategories
 */
function renderSubCategoryOptions(subCategories) {
  for (let i = 0; i < subCategories.length; i++) {
    const addOption = document.createElement("option");
    addOption.textContent = subCategories[i].localizedName;
    addOption.value = subCategories[i].categoryId;
    selectSubCategory.appendChild(addOption);
  }
}

/**
 * Helper function to display the error messages
 * @param {string} message 
 */
function showErrorMessage(message) {
  const errorMessage = document.querySelector(".error-message");

  if (!errorMessage) return;
  errorMessage.textContent = message;
}

function handleRedirect() {
  const path = window.location.pathname;

  if (path.includes("entry")) {
    window.location.replace("dashboard.html");
  } else if (path.includes("dashboard")) {
    window.location.replace("entry.html");
  }
}

/**
 * Renders the user expenses in the dashboard
 * @param { Expense[] } userExpenses 
 */
function renderUserExpenses(userExpenses) {
  transactionList.innerHTML = "";

  if (userExpenses.length === 0) {
    transactionList.style.display = "none";
    return;
  }

  for (let i = 0; i < userExpenses.length; i++) {
    const expense = userExpenses[i];

    const expenseCard = document.createElement("div");
    expenseCard.classList.add("expense-card");
    expenseCard.dataset.id = expense.expenseId;

    const cardHeader = document.createElement("div");
    cardHeader.classList.add("top");

    const category = document.createElement("div");
    category.textContent = expense.localizedName;

    const amount = document.createElement("div");
    amount.textContent = expense.amount;

    cardHeader.append(category, amount);

    const date = document.createElement("div");
    date.classList.add("meta");
    date.textContent = expense.paymentDate;

    const description = document.createElement("div");
    description.classList.add("description");
    description.textContent = expense.description;

    const actions = document.createElement("div");
    actions.classList.add("actions");

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = t("ui.actions.delete");

    deleteBtn.addEventListener("click", async () => {
      const result = await deleteExpense(expense.expenseId)

      if (!result.success) showErrorMessage(result.message);

      expenseCard.remove();
    });

    const updateBtn = document.createElement("button");
    updateBtn.textContent = t("ui.actions.update");
    updateBtn.addEventListener("click", () => {
      window.location.replace(`edit?id=${expense.expenseId}&type=expense`);
    });

    actions.append(deleteBtn, updateBtn);

    expenseCard.append(actions, cardHeader, date, description);
    transactionList.append(expenseCard);
  }
}

/**
 * Renders the user incomes in the dashboard
 * @param { Income[] } userIncomes 
 */
function renderUserIncomes(userIncomes) {
  transactionList.innerHTML = "";

  if (userIncomes.length === 0) {
    transactionList.style.display = "none";
    return;
  }

  for (let i = 0; i < userIncomes.length; i++) {
    const income = userIncomes[i];

    const incomeCard = document.createElement("div");
    incomeCard.classList.add("income-card");
    incomeCard.dataset.id = income.incomeId;

    const cardHeader = document.createElement("div");
    cardHeader.classList.add("top");

    const title = document.createElement("div");
    title.textContent = income.title;

    const amount = document.createElement("div");
    amount.textContent = income.amount;

    cardHeader.append(title, amount);

    const incomeDate = document.createElement("div");
    incomeDate.classList.add("meta");
    incomeDate.textContent = income.incomeDate;

    const actions = document.createElement("div");
    actions.classList.add("actions");

    const deleteBtn = document.createElement("button")
    deleteBtn.textContent = t("ui.actions.delete")
    deleteBtn.addEventListener("click", async () => {
      const result = await deleteIncome(income.incomeId);

      if (!result.success) showErrorMessage(result.message);

      incomeCard.remove();
    });

    const updateBtn = document.createElement("button");
    updateBtn.textContent = t("ui.actions.update");

    updateBtn.addEventListener("click", async () => {
      window.location.replace(`edit?id=${income.incomeId}&type=income`);

      typeHandler();
    });

    actions.append(deleteBtn, updateBtn);

    incomeCard.append(actions, cardHeader, incomeDate);

    transactionList.append(incomeCard);
  }
}

/**
 * Applies the translations to the DOM based on the data attributes.
 */
function applyTranslations() {
  document.querySelectorAll("[data-i18n]").forEach(el => {
    const value = el.dataset.i18n
      .split(".")
      .reduce((acc, key) => acc[key], translations);
    el.textContent = value;
  });

  document.querySelectorAll("[data-i18n-placeholder]").forEach(el => {
    const value = el.dataset.i18nPlaceholder
      .split(".")
      .reduce((acc, key) => acc[key], translations);

    el.placeholder = value;
  });
}