const form = document.querySelector("form");
const usernameInput = document.querySelector(".username");
const passwordInput = document.querySelector(".password");

form.addEventListener("submit", handleSubmit);

/***
 * Handles form submissions for login and registration
 * Delegates to login or register handler based on form type
 */
async function handleSubmit(e) {
  e.preventDefault();

  const username = usernameInput.value;
  const password = passwordInput.value;

  const result = await loginUser(username, password);

  if (!result.success) {
    showErrorMessage(result.message);
    return;
  }

  finishAuth(username);
}