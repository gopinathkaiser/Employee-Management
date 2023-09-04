function checkLoginDetails(){
    const email = document.getElementById("email").value;
    const pass = document.getElementById("password").value;

    fetch("http://localhost:8080/checkLogin",{
        method : 'GET'
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.log(error);
    })
}