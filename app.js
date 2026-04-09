document.addEventListener('DOMContentLoaded', () => {
    // Authentication Check
    let currentUser = JSON.parse(localStorage.getItem('gradeCalc_currentUser'));
    if (!currentUser) {
        window.location.href = 'index.html';
        return;
    }

    // Update UI with User Info
    document.getElementById('nav-user-name').textContent = currentUser.name.split(' ')[0];
    document.getElementById('profile-name').textContent = currentUser.name;
    document.getElementById('profile-email').textContent = currentUser.email;
    document.getElementById('profile-initial').textContent = currentUser.name.charAt(0).toUpperCase();

    // Navigation Logic
    const navBtns = document.querySelectorAll('.nav-btn');
    const sections = document.querySelectorAll('.app-section');

    navBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            
            // Remove active classes
            navBtns.forEach(b => b.classList.remove('active'));
            sections.forEach(s => s.classList.remove('active'));
            
            // Add active class to clicked
            btn.classList.add('active');
            const targetId = btn.getAttribute('data-target');
            document.getElementById(targetId).classList.add('active');

            if (targetId === 'history-section' || targetId === 'profile-section') {
                refreshUserData();
            }
        });
    });

    // Logout
    document.getElementById('logout-btn').addEventListener('click', () => {
        localStorage.removeItem('gradeCalc_currentUser');
        window.location.href = 'index.html';
    });

    // Calculator Logic
    const calcForm = document.getElementById('calc-form');
    const resultsArea = document.getElementById('results-area');
    const resTotal = document.getElementById('res-total');
    const resAvg = document.getElementById('res-avg');
    const resGrade = document.getElementById('res-grade');
    
    // Dynamic Inputs Generation
    const numSubjectsInput = document.getElementById('num-subjects');
    const generateBtn = document.getElementById('generate-btn');
    const dynamicInputsContainer = document.getElementById('dynamic-inputs');
    let subjectCount = 0;

    generateBtn.addEventListener('click', () => {
        const num = parseInt(numSubjectsInput.value);
        if (isNaN(num) || num < 1 || num > 20) {
            alert('Please enter a valid number of subjects (1-20).');
            return;
        }
        
        subjectCount = num;
        dynamicInputsContainer.innerHTML = ''; // Clear existing
        
        for (let i = 1; i <= num; i++) {
            const group = document.createElement('div');
            group.className = 'input-group';
            group.innerHTML = `
                <label for="subject-${i}">Subject ${i} <span class="range">(0-100)</span></label>
                <input type="number" id="subject-${i}" class="subject-input" min="0" max="100" required placeholder="e.g. 85">
            `;
            dynamicInputsContainer.appendChild(group);
        }
        
        calcForm.classList.remove('hidden');
        resultsArea.classList.add('hidden');
    });

    calcForm.addEventListener('submit', (e) => {
        e.preventDefault();
        
        const inputs = document.querySelectorAll('.subject-input');
        let total = 0;
        
        inputs.forEach(input => {
            total += parseFloat(input.value) || 0;
        });
        
        const average = total / subjectCount;
        const grade = calculateGrade(average);
        
        // Update UI
        resTotal.textContent = `${total} / ${subjectCount * 100}`;
        resAvg.textContent = `${average.toFixed(2)}%`;
        resGrade.textContent = grade;
        
        // Update Grade Color
        resGrade.className = `grade-value grade-${grade}`;
        
        // Show Results
        resultsArea.classList.remove('hidden');

        // Save to History
        saveToHistory({
            date: new Date().toLocaleDateString() + ' ' + new Date().toLocaleTimeString(),
            subjectsCount: subjectCount,
            total,
            average: average.toFixed(2),
            grade
        });
    });

    document.getElementById('reset-calc').addEventListener('click', () => {
        calcForm.reset();
        resultsArea.classList.add('hidden');
        calcForm.classList.add('hidden');
        numSubjectsInput.value = '';
    });

    function calculateGrade(average) {
        if (average >= 90) return 'A';
        if (average >= 75) return 'B';
        if (average >= 60) return 'C';
        if (average >= 50) return 'D';
        return 'F';
    }

    // History & User Data Sync
    function saveToHistory(entry) {
        // Sync with latest DB
        let users = JSON.parse(localStorage.getItem('gradeCalc_users')) || [];
        const userIndex = users.findIndex(u => u.id === currentUser.id);
        
        if (userIndex !== -1) {
            users[userIndex].history.unshift(entry); // add to top
            localStorage.setItem('gradeCalc_users', JSON.stringify(users));
            
            // Update current session obj
            currentUser.history = users[userIndex].history;
            localStorage.setItem('gradeCalc_currentUser', JSON.stringify(currentUser));
            
            renderHistory();
        }
    }

    function refreshUserData() {
        let users = JSON.parse(localStorage.getItem('gradeCalc_users')) || [];
        const updatedUser = users.find(u => u.id === currentUser.id);
        if(updatedUser) {
            currentUser = updatedUser;
            localStorage.setItem('gradeCalc_currentUser', JSON.stringify(currentUser));
        }
        renderHistory();
        
        // Update Profile Stats
        const historyCount = currentUser.history ? currentUser.history.length : 0;
        document.getElementById('profile-stats').textContent = historyCount;
    }

    function renderHistory() {
        const tbody = document.getElementById('history-tbody');
        const emptyState = document.getElementById('no-history-msg');
        
        tbody.innerHTML = '';
        const history = currentUser.history || [];
        
        if (history.length === 0) {
            emptyState.classList.remove('hidden');
            return;
        }
        
        emptyState.classList.add('hidden');
        
        history.forEach(entry => {
            const tr = document.createElement('tr');
            // Support legacy records (m1, m2, m3) or new dynamic format (subjectsCount)
            const count = entry.subjectsCount || 3; 
            tr.innerHTML = `
                <td>${entry.date}</td>
                <td>${count}</td>
                <td><strong>${entry.total} / ${count * 100}</strong></td>
                <td><strong>${entry.average}%</strong></td>
                <td><span class="grade-${entry.grade}" style="font-weight:bold; font-size:1.1rem">${entry.grade}</span></td>
            `;
            tbody.appendChild(tr);
        });
    }

    // Initial load
    refreshUserData();
});
