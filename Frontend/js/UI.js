const redirect = document.querySelector(".redirect");
const transactionList = document.getElementById("transaction-list");

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

  if (path.includes("entry.html")) {
    window.location.href = "dashboard.html";
  } else if (path.includes("dashboard.html")) {
    window.location.href = "entry.html";
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
    const expenseCard = document.createElement("div");
    expenseCard.classList.add("expense-card");

    const cardHeader = document.createElement("div");
    cardHeader.classList.add("top");

    const category = document.createElement("div");
    category.textContent = userExpenses[i].localizedName;
    const amount = document.createElement("div");
    amount.textContent = userExpenses[i].amount;

    cardHeader.append(category, amount);

    const date = document.createElement("div");
    date.classList.add("meta");
    date.textContent = userExpenses[i].paymentDate;

    const description = document.createElement("div");
    description.classList.add("description");
    description.textContent = userExpenses[i].description;

    expenseCard.append(cardHeader, date, description);
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

  for( let i = 0; i < userIncomes.length; i++) {
    const incomeCard = document.createElement("div");
    incomeCard.classList.add("income-card");

    const cardHeader = document.createElement("div");
    cardHeader.classList.add("top");

    const title = document.createElement("div");
    title.textContent = userIncomes[i].title;

    const amount = document.createElement("div");
    amount.textContent = userIncomes[i].amount;

    cardHeader.append(title, amount);

    const incomeDate = document.createElement("div");
    incomeDate.classList.add("meta");
    incomeDate.textContent = userIncomes[i].incomeDate;

    incomeCard.append(cardHeader, incomeDate);

    transactionList.append(incomeCard);
  }
}