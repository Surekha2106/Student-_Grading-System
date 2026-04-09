document.addEventListener('DOMContentLoaded', () => {
    // Check if user is already logged in
    const currentUser = JSON.parse(localStorage.getItem('gradeCalc_currentUser'));
    if (currentUser) {
        window.location.href = 'app.html';
        return;
    }

    // Toggle Forms
    const loginFormContainer = document.getElementById('login-form-container');
    const signupFormContainer = document.getElementById('signup-form-container');
    
    document.getElementById('show-signup').addEventListener('click', (e) => {
        e.preventDefault();
        loginFormContainer.classList.remove('active');
        signupFormContainer.classList.add('active');
    });

    document.getElementById('show-login').addEventListener('click', (e) => {
        e.preventDefault();
        signupFormContainer.classList.remove('active');
        loginFormContainer.classList.add('active');
    });

    // Handle Signup
    document.getElementById('signup-form').addEventListener('submit', (e) => {
        e.preventDefault();
        
        const name = document.getElementById('signup-name').value.trim();
        const email = document.getElementById('signup-email').value.trim();
        const password = document.getElementById('signup-password').value.trim();
        const errorDiv = document.getElementById('signup-error');
        
        // Get existing users
        let users = JSON.parse(localStorage.getItem('gradeCalc_users')) || [];
        
        // Check if email exists
        if (users.some(u => u.email === email)) {
            errorDiv.textContent = "An account with this email already exists.";
            return;
        }
        
        // Create user
        const newUser = {
            id: Date.now().toString(),
            name,
            email,
            password, // Storing in plain text purely for frontend simulation
            history: []
        };
        
        users.push(newUser);
        localStorage.setItem('gradeCalc_users', JSON.stringify(users));
        
        // Auto log in
        localStorage.setItem('gradeCalc_currentUser', JSON.stringify(newUser));
        window.location.href = 'app.html';
    });

    // Handle Login
    document.getElementById('login-form').addEventListener('submit', (e) => {
        e.preventDefault();
        
        const email = document.getElementById('login-email').value.trim();
        const password = document.getElementById('login-password').value.trim();
        const errorDiv = document.getElementById('login-error');
        
        let users = JSON.parse(localStorage.getItem('gradeCalc_users')) || [];
        
        const user = users.find(u => u.email === email && u.password === password);
        
        if (user) {
            localStorage.setItem('gradeCalc_currentUser', JSON.stringify(user));
            window.location.href = 'app.html';
        } else {
            errorDiv.textContent = "Invalid email or password.";
        }
    });
});
