/**
 * Handles user registration flow including validation and API request
 * @param {string} username 
 * @param {string} password 
 */
async function handleRegister(username, password, passwordConfirm) {
  const validation = validatePassword(password, passwordConfirm);

  if (!validation.valid) {
    showErrorMessage(validation.message);
    return;
  }

  const result = await registerUser(username, password, passwordConfirm);


  if (!result.success) {
    showErrorMessage(result.message);
    return;
  }

  finishAuth(username);
}

/**
 * Handles user login flow including validation and API request
 * @param {string} username
 * @param {string} password
 * @returns 
 */
async function handleLogin(username, password) {
  const result = await loginUser(username, password);

  if (!result.success) {
    showErrorMessage(result.message);
    return;
  }

  finishAuth(username);
}

/**
 * Completes authentication and redirects user to the main page
 * @param {string} username
 */
function finishAuth(username) {
  localStorage.setItem("username", username);
  showErrorMessage("");
  window.location.href = "entry.html"
}

/**
 * Checks if the password and password confirmation for registering match
 * 
 * @param { string } password
 * @param { string } passwordConfirm
 * @returns { object } - Returns an object indicating whether the passwords match. If unsuccessful, an error description is included
 */
function validatePassword(password, passwordConfirm) {
    if (password !== passwordConfirm) {
        return {
            valid: false,
            message: "Passwords do not match"
        };
    }
    return {
        valid: true,
    };
}