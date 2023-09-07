async function updateSignup() {
    event.preventDefault();
    const formData = {
        email: document.getElementById("signup-email").value,
        name: document.getElementById("signup-name").value,
        password: document.getElementById("signup-password").value,

    }
    if (checkReEnteredPassword() && validatePassword() && await validateEmail() ) {

       

            await fetch('http://localhost:8080/Auth/', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log("Datas are", data);
                    // alert("data added successfully");

                })

                .catch(error => {
                    console.log("error", error);
                })

            location = "index.html";
        
    }

}

async function checkEmailExists() {
    let email = document.getElementById("signup-email");
    let bool = true;
    await fetch(`http://localhost:8080/Auth/userExists/${email.value}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data) {
                alert("Email already exists");
                bool = false;
            }
        })
        .catch(err => {
            console.log(err);
        })

    return bool;
}

function checkReEnteredPassword() {
    let password = document.getElementById("signup-password").value;
    let reEnterPassword = document.getElementById("signup-ReEnter-password");

    if (password == reEnterPassword.value) {
        return true;
    } else {
        alert("Password did not match");
        reEnterPassword.value = '';
        reEnterPassword.focus();
        return false;
    }
}


function validatePassword(){
    let password = document.getElementById("signup-password");
    let passwordRegex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,16}$/;

    if(passwordRegex.test(password.value)){
        return true;
    }

    alert("Password should have 8 to 16 characters including a number,a special character,a lower and upper case letters");
    password.focus();
    return false;
}

async function validateEmail(){
    let emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    let email = document.getElementById("signup-email");

    if(emailRegex.test(email.value)){
        return await checkEmailExists();;
    }

    alert("Enter valid email");
    email.focus();
    return false;

}