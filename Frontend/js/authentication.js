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