//const API_BASE = window.APP_CONFIG?.API_URL || "";

const API_BASE = "http://192.168.178.44:8081";

/**
 * Registers a new user via the API
 * 
 * @async
 * @param { string } username
 * @param { string } password
 * @param { string } passwordConfirm 
 * @returns { Promise<object> }
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

        return {
            success: false,
            message: getErrorMessage(response, data, "Registration failed. Please try again.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
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
 * @returns { Promise<object> } - 
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

        if (response.ok) {
            return { success: true };
        }

        return {
            success: false,
            message: getErrorMessage(response, data, "Invalid username or password. Please try again.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
        }
    }
}

/**
 * Retrieves all the main categories 
 * @returns { Promise<object> }
 */
async function getMainCategories() {
    try {
        const response = await fetch(`${API_BASE}/categories/main`, {
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
            message: getErrorMessage(response, data, "Failed to load categories.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
        }
    }
}

/**
 * Gets all the child categories for a main category the user choose
 * @param { string } categoryId
 * @returns { Promise<object> }
 */
async function getSubCategories(categoryId) {
    try {
        const response = await fetch(`${API_BASE}/categories/${categoryId}/sub`, {
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
            message: getErrorMessage(response, data, "Failed to load categories")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
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
 * @returns { Promise<object> }
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
            message: getErrorMessage(response, data, "Failed to add expense. Please try again.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
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
 * @returns { Promise<object> }
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
            message: getErrorMessage(response, data, "Failed to add income. Please try again.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
        };
    }
}

/**
 * API Call that returns all expenses for the current user
 * @returns { Promise<object> }
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
            message: getErrorMessage(response, data, "Failed to load expenses.")
        };

    } catch (error) {
        return {
            success: false,
            message: "Network error"
        };
    }
}

/**
 * API Call that returns all incomes for the current user
 * @returns { Promise<object> }
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

        return {
            success: false,
            message: getErrorMessage(response, data, "Failed to load incomes.")
        };
    } catch (error) {
        return {
            success: false,
            message: "Network error"
        };
    }
}

function getErrorMessage(response, data, fallbackMessage) {
    const messages = {
        401: "Please log in to continue.",
        409: "This action could not be completed due to a conflict. Please try again.",
        500: "Server error. Please try again later."
    };

    return messages[response.status] || data?.message || fallbackMessage;
}