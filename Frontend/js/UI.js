/**
 * This is used for handling the events on the buttons, as well as adding error messages or changing the UI based on user interaction.
 */
const errorMessage = document.querySelector(".error-message");
const isRegisterPage = !!document.querySelector(".password-confirm");

document.querySelector("form").addEventListener("submit", async function (e) {
  e.preventDefault();

  const username = (document.querySelector(".username")).value;
  const password = (document.querySelector(".password")).value;

  if (isRegisterPage) {
  const passwordConfirm = document.querySelector(".password-confirm").value;
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
  } else {
    const result = await loginUser(username, password);

    if (!result.success) {
      errorMessage.textContent = result.message;
      return;
    }

    localStorage.setItem("username", username);

    errorMessage.textContent = "";
    window.location.href = "expensetracker-dashboard-tmp.html"  
  } 
});