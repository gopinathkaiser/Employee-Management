
async function checkLoginDetails() {
    event.preventDefault();
    const email = document.getElementById("login-email").value;
    const pass = document.getElementById("password").value;

    const formData = {
        "email": document.getElementById("login-email").value,
        "password": document.getElementById("password").value
    };


    if (await checkEmailExists()) {
        await fetch(`http://localhost:8080/Auth/validate`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',

            },
            
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {

                console.log(data.data.accessToken);
                if (data.data != 'Password wrong') {
                    if (data.data != 'Verify the email') {
                        // alert("password wrong");
                        setCookie("UserCookie", data.data.accessToken);
                        location.href = "../templates/index.html";
                    } else {
                        swal("Alert", "Verify the Email", "error");
                        
                    }

                } else if(data.data != 'Verify the email') {
                    swal("Alert", "Password is wrong", "error");
                }else{
                    swal("Alert", "Verify the Email", "error");
                }

            })
            .catch(error => {
                console.log(error);
            })

    } else {
        //  alert("Email not registered kindly signup");
        swal("Alert", "Email not registered kindly signup", "error");
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

function setCookie(name, token) {
    let cookie = name + "=" + encodeURIComponent(token);

    let expiration = new Date();
    expiration.setTime(expiration.getTime() + 30 * 60 * 1000);

    cookie += "; expires=" + expiration.toUTCString();

    document.cookie = cookie;
}