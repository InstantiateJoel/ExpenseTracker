const form = document.querySelector("form");
const usernameInput = document.querySelector(".username");
const passwordInput = document.querySelector(".password");
const passwordConfirmInput = document.querySelector(".password-confirm");

/**
 * When the user registers or login and clicks on the submit button
 */
form.addEventListener("submit", handleSubmit);

/***
 * Handles form submissions for login and registration
 * Delegates to login or register handler based on form type
 */
async function handleSubmit(e) {
    e.preventDefault();

    const username = usernameInput.value;
    const password = passwordInput.value;
    const passwordConfirm = passwordConfirmInput.value;

    const validPassword = validatePassword(password, passwordConfirm);

    if (!validPassword.valid) {
        showErrorMessage(validPassword.message);
        return
    }

    const result = await registerUser(username, password, passwordConfirm);

    if (!result.success) {
        showErrorMessage(result.message);
        return;
    }

    finishAuth(username);
}