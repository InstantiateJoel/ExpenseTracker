const API_BASE = window.APP_CONFIG?.API_URL || "";

/**
 * Registers a new user via the API
 * 
 * @async
 * @function registerUser
 * @param { string } username - The Username choosen by the user
 * @param { string } password - The users's password
 * @param { string } passwordConfirm - Confirmation of the user's passwrod
 * @returns { Promise<{Success: boolean, message?: string}> } - Returns an object indicating whether the registration was successful. If unsuccessful, a message key or error description is included.
 */
async function registerUser(username, password, passwordConfirm) {
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

    let data = await response.json();

    if (response.status === 200) {
        return {
            success: true
        };
    }

    if(response.status === 409) {
        return {
            success: false,
            message: data?.messageKey || "Invalid Input"
        };
    }
    
    if (response.status === 400) {
    return {
        success: false,
        message: data?.messageKey || "Something went wrong"
    };
    }
    return {
        success: false,
        message: "Something went wrong"
    };
}

/**
 * Authenticates a user with the API
 * 
 * @async
 * @function loginUser
 * @param { string } username - The user's username
 * @param { string } password - The users's password
 * 
 */
async function loginUser(username, password) {
    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);

    const response = await fetch(`${API_BASE}/users/login`, {
        method: "POST",
        body: formData,
        credentials: "include"
    });

    if (response.status === 200) {
        return { success: true };
    }

    if (response.status === 401) {
        return {
            success: false,
            message: data?.messageKey || "Something went wrong"
        };
    }

    return {
        success: false,
        message: "Something went wrong"
    };
}