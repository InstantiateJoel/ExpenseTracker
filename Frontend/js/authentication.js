/**
 * Checks if the password and password confirmation for registering match
 * 
 * @param { string } password - The user's password 
 * @param { string } passwordConfirm - The user's password confirmation 
 * @returns { object } - Returns an object indicating whether the passwrods match. If unsuccessful, an error description is included.
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