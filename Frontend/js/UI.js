const errorMessage = document.querySelector(".error-message");

document.querySelector("form").addEventListener("submit", async function (e) {
  e.preventDefault();

  const username = (document.querySelector(".username")).value;
  const password = (document.querySelector(".password")).value;
  const passwordConfirm = (document.querySelector(".password-confirm")).value;

  const validation = validatePassword(password, passwordConfirm);

  if (!validation.valid) {
    errorMessage.textContent = validation.message;
    return;
  }

  const result = await registerUser(username, password, passwordConfirm);

  if (!result.success) {
    errorMessage.textContent = result.message;
    return;
  }

  errorMessage.textContent = "";
  e.target.reset();
});