async function checkLoginDetails() {
    event.preventDefault();
    const email = document.getElementById("login-email").value;
    const pass = document.getElementById("password").value;

    const formData = {
        "email" : document.getElementById("login-email").value,
        "password" : document.getElementById("password").value
    };


    if (await checkEmailExists()) {
        await fetch(`http://localhost:8080/Auth/validate`,{
            method : 'POST',
            headers : {
                'Content-type' : 'application/json'
            },
            body : JSON.stringify(formData)
        })
            .then(response => response.text())
            .then(data => {
                // if (data.email == email) {
                //     if (data.password == pass) {
                //         location.href = "../templates/index.html";
                //     } else {
                //         alert("password wrong");
                //     }
                // }

                console.log(data);
                if(data == "success"){
                    
                    location.href = "../templates/index.html";
                }else{
                    alert("password wrong");
                }

            })
            .catch(error => {
                console.log(error);
            })

    } else {
        alert("Email not registered kindly signup");
    }
}



async function checkEmailExists() {
    let email = document.getElementById("login-email");
    let bool = false;
    await fetch(`http://localhost:8080/Auth/userExists/${email.value}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                bool = true;

            }
        })
        .catch(err => {
            console.log(err);
        })

    return bool;
}