const API_BASE = window.APP_CONFIG?.API_URL || "";

async function registerUser(pUsername, pPassword, pPasswordConfirm) {
    const response = await fetch(`${API_BASE}/users/register`, {
        method: "POST",
        body: JSON.stringify({
            username: pUsername,
            password: pPassword,
            passwordConfirm: pPasswordConfirm
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