async function updateSignup() {
    event.preventDefault();
    const formData = {
        email: document.getElementById("signup-email").value,
        name: document.getElementById("signup-name").value,
        password: document.getElementById("signup-password").value,

    }

    if (await checkEmailExists()) {

        await fetch('http://localhost:8080/Auth/insert', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Datas are", data);
                alert("data added successfully");

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

