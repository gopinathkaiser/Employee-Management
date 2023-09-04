async function checkLoginDetails() {
    event.preventDefault();
    const email = document.getElementById("login-email").value;
    const pass = document.getElementById("password").value;
    console.log("calles");


    if (await checkEmailExists()) {
        await fetch(`http://localhost:8080/Auth/checkLogin/${email}`)
            .then(response => response.json())
            .then(data => {
                // console.log(data[0]["email"], data[0]["password"], email, pass);
                if(data.email == email){
                    if(data.password == pass){
                        location = "../../templates/index.html";
                    }else{
                        alert("password wrong");
                    }
                    
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
            console.log(data + "check email exists");
            if (data) {
                bool = true;
                // return true;
            }
        })
        .catch(err => {
            console.log(err);
        })

    return bool;
}