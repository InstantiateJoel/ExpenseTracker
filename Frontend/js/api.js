const API_BASE = window.APP_CONFIG?.API_URL || "";

/**
 * API call to register a new user
 * 
 * @async
 * @param { string } username
 * @param { string } password
 * @param { string } passwordConfirm 
 * @returns { Promise<{
 *  success: boolean,
 *  message: string
 *  }> } - Result object indicating wether the register was successful
 */
async function registerUser(username, password, passwordConfirm) {
    try {
        const response = await fetch(`${API_BASE}/users/register`, {
            method: "POST",
            body: JSON.stringify({
                username: username,
                password: password,
                passwordConfirm: passwordConfirm
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        let data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
            };
        }

        console.log(data.messageKey);
        return {
            success: false,
            message: t(`errors.${data.messageKey}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * Authenticates a user with the API
 * 
 * @async
 * @function loginUser
 * @param { string } username 
 * @param { string } password
 * @returns { Promise<{
 *  success: true,
 *  message: string
 *  }> } - Result object indicating wether the login was successful
 */
async function loginUser(username, password) {
    try {
        const response = await fetch(`${API_BASE}/users/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                username,
                password
            }),
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.status === 401) {

            return {
                success: false,
                message: t(`errors.${data.errorCode}`)
            };
        }

        if (response.ok) {
            return { success: true };
        }

        console.log(response.status)
        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        }
    }
}

/**
 * API call to fetch all main categories
 * @returns { Promise<{
 *  success: boolean,
 *  data: Category[]
 *  message: string
 *  }> } - Result object indicating whether the fetch was successful
 */
async function getMainCategories() {
    try {
        const response = await fetch(`${API_BASE}/categories/main`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Accept-Language": currentLanguage
            }
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        }
    }
}

/**
 * API call to fetch all sub categories by a given main-categoryId
 * @param { string } categoryId
 * @returns { Promise<{
 *  success: boolean,
 *  data: Category[],
 *  message: string
 *  }> } - Result object 
 */
async function getSubCategories(categoryId) {
    try {
        const response = await fetch(`${API_BASE}/categories/${categoryId}/sub`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Accept-Language": currentLanguage
            }
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        }
    }
}

/**
 * @typedef { Object } Expense
 * @property { string } categoryId
 * @property { number } amount
 * @property { string } paymentDate
 * @property { string } description
 */
/**
 * Creates a new expense
 * @param { Expense } expense
 * @returns { Promise<{
 *  success: boolean,
 *  message: string
 *  >} } - Result object indicating whether the request was successful 
 */
async function addNewExpense(expense) {
    try {
        const response = await fetch(`${API_BASE}/expense`, {
            method: "POST",
            body: JSON.stringify(expense),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data || null
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * @typedef { Object } Income
 * @property { string } title
 * @property { number } amount
 * @property { string } incomeDate
 */
/**
 * Creates a new income record and persists it in the database
 * @param { Income } income
 * @returns { Promise<{
 *  success: boolean,
 *  message: string 
 *  }> } - Result object indicating whether the request was successful
 */
async function addNewIncome(income) {
    try {
        const response = await fetch(`${API_BASE}/income`, {
            method: "POST",
            body: JSON.stringify(income),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data || null
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * API Call that returns all expenses for the current user
 * @returns { Promise<{
    * success: boolean,
    * message: string 
    * }> } - Result object indicating whether the request was successful
 */
async function getUserExpenses() {
    try {
        const response = await fetch(`${API_BASE}/expense`, {
            method: "GET",
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };

    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * API Call that returns all incomes for the current user
 * @returns { Promise<{
*   success: boolean,
*   message: string 
*   }> } - Result object indicating whether the request was successful
*/
async function getUserIncomes() {
    try {
        const response = await fetch(`${API_BASE}/income`, {
            method: "GET",
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }
        [message]
        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };
    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * API call to delete a user expense
 * @param { string } expenseId 
 * @returns { Promise<{
 *  success: boolean,
 *  message: string 
 *  }>} Result object indicating wether the deletion was successful
 */
async function deleteExpense(expenseId) {
    try {
        const response = await fetch(`${API_BASE}/expense/${expenseId}`, {
            method: "DELETE",
            credentials: "include"
        });

        if (response.ok) {
            return {
                success: true
            };
        }

        return {
            success: false,
            message: t(`errors.genericMessage`)
        };

    } catch (error) {
        return {
            success: false,
            message: t(`errors.network`)
        };
    }
}

/**
 * API call to delete user income
 * @param { string } incomeId 
 * @returns { Promise<{
 *  success: boolean,
 *  message: string
 *  }> } - Result object indicating whether the request was successful or not
 */
async function deleteIncome(incomeId) {
    try {
        const response = await fetch(`${API_BASE}/income/${incomeId}`, {
            method: "DELETE",
            credentials: "include"
        });

        if (response.ok) {
            return {
                success: true
            };
        }

        return {
            success: false,
            message: t(`errors.genericMessage`)
        };
    } catch (error) {
        return {
            success: false,
            message: t(`errors.network`)
        };
    }
}

/**
 * @typedef { Object } result
 * @property { boolean } success - Indicates whether the request was successful or not
 * @property  {string } [message] - Error message on failure
 */
/**
 * 
 * @param { string } expenseId 
 * @param { Expense } expense - Object with all user changes
 * @returns { Promise<{
 *  success: boolean,
 *  message: string 
 *  }> } - Result object indicting whether the request was successful or not
 */
async function updateExpense(expenseId, expense) {
    try {
        const response = await fetch(`${API_BASE}/expense/${expenseId}`, {
            method: "PATCH",
            body: JSON.stringify(expense),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            credentials: "include"
        });

        const data = await response.json().catch(() => null);


        if (response.ok) {
            return {
                success: true,
                data: data || null
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };
    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * @typedef { Object } result
 * @property { boolean } success 
 * @property { string } [message]
 * @property { Income } [data] 
 */
/**
 * 
 * @param { string } incomeId 
 * @param { Income } income
 * @returns { Promise<{
 *  success: boolean,
 *  message: string,
 *  data: Income
 *  }> } - Result object indicating whether the request was successful or not
 */
async function updateIncome(incomeId, income) {
    try {
        const response = await fetch(`${API_BASE}/income/${incomeId}`, {
            method: "PATCH",
            body: JSON.stringify(income),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data || null
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };
    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * Fetches the details of a specific income entry
 * 
 * @param { string } incomeId 
 * @returns { Promise<{
 *  success: boolean,
 *  message: string
 *  }> } - Result object indicating whether the request was successful or not
 */
async function getIncomeDetails(incomeId) {
    try {
        const response = await fetch(`${API_BASE}/income/${incomeId}`, {
            method: "GET",
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };
    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}

/**
 * Fetches teh details of a specific expense entry
 * @param { string } expenseId 
 * @returns { Promise<{
 *  success: boolean,
 *  message: string
 *  }> } - Result object indicating whether the request was successful or not
 */
async function getExpenseDetails(expenseId) {
    try {
        const response = await fetch(`${API_BASE}/expense/${expenseId}`, {
            method: "GET",
            credentials: "include"
        });

        const data = await response.json().catch(() => null);

        if (response.ok) {
            return {
                success: true,
                data: data
            };
        }

        return {
            success: false,
            message: t(`errors.${data.errorCode}`)
        };
    } catch (error) {
        return {
            success: false,
            message: t("errors.network")
        };
    }
}