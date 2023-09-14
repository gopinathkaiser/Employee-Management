

let page = 0;
let max = 0;

async function validateEmail() {
    let cookie = getCookie("UserCookie");
    let email = document.getElementById("email");
    let emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    let checkForAlert = 1;
    if (emailRegex.test(email.value)) {
        await fetch(`http://localhost:8080/user/verify/${email.value}`, {
            headers: new Headers({
                'Authorization': `${cookie}`
            }),
        })
            .then(response => response.json())
            .then(data => {
                if (data)
                    checkForAlert = 2;
                else
                    checkForAlert = 3;
            })
            .catch(error => {
                console.log(error);
            })
    }
    if (checkForAlert == 1) {
        // alert("Enter a valid email");
        // swal("Alert", "Enter a valid email", "error");
        swal("Alert", "Enter valid email", "warning");
        document.getElementById("warn-email").style.visibility = 'visible';
        email.focus();
        return false;
    }
    else {
        if (checkForAlert == 2) {
            // alert("Email already Exists");
            swal("Alert", "Email already Exists", "error");
            document.getElementById("warn-email").style.visibility = 'visible';
            email.focus();
            return false;
        } else {

            document.getElementById("warn-email").style.visibility = 'hidden';

            return true;
        }
    }

}

function validateFirstName() {

    let firstName = document.getElementById(("fname"));
    let fnameRegex = /^[A-Za-z\s]*$/;
    if (fnameRegex.test(firstName.value)) {
        document.getElementById("warn-fname").style.visibility = 'hidden';

        return true;
    }
    // swal.fire("enter valid num");
    swal("Alert", "First name should contain only alphabets", "warning");

    // alert("First name should contain only alphabets");
    document.getElementById("warn-fname").style.visibility = 'visible';

    firstName.focus();
    return false;
}

function validateLastName() {
    let lastName = document.getElementById(("lname"));
    let lnameRegex = /^[A-Za-z\s]*$/;
    if (lnameRegex.test(lastName.value)) {
        document.getElementById("warn-lname").style.visibility = 'hidden';

        return true;
    }
    swal("Alert", "Last name should contain only alphabets", "warning");

    // alert("Last name should contain only alphabets");
    document.getElementById("warn-lname").style.visibility = 'visible';

    lastName.focus();
    return false;
}


function validateDob() {
    let dob = document.getElementById("dob");
    let todayDate = new Date();

    if (new Date(dob.value).getTime() >= todayDate.getTime()) {
        swal("Alert", "The Date must be lesser or Equal to today date", "warning");

        // alert("The Date must be lesser or Equal to today date");
        document.getElementById("warn-dob").style.visibility = 'visible';

        dob.focus();
        return false;
    }
    return true;
}

function validateMobileNum() {
    let mobileNum = document.getElementById("mobile");
    let numRegex = /^[6-9]\d{9}$/;
    if (numRegex.test(mobileNum.value)) {
        document.getElementById("warn-mobile").style.visibility = 'hidden';

        return true;
    }
    swal("Alert", "Enter valid number", "warning");

    document.getElementById("warn-mobile").style.visibility = 'visible';

    mobileNum.focus();
    return false;

}

async function checkAllValidation() {
    event.preventDefault();
    const formData = {
        "users": {
            "email": document.getElementById("email").value,
            "fname": document.getElementById("fname").value,
            "lname": document.getElementById("lname").value,
            "mobile": document.getElementById("mobile").value,
            "dob": document.getElementById("dob").value,
            "address": document.getElementById("address").value,
            "role": {
                "roleName": document.querySelector('input[name="role"]:checked').value
            }
        },
    };
    // console.log(formData);
    let cookie = getCookie("UserCookie");
    if (document.getElementById("email").disabled == true) {

        if (validateFirstName() && validateLastName() && validateMobileNum && validateDob()) {
            fetch('http://localhost:8080/user', {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': cookie
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.text())
                .then(data => {
                    swal("Success", "Data modified successfully", "success");
                })
                .catch(error => {
                    console.log("error inserting after edit", error);
                })
        }
    }
    else {

        if (await validateEmail() && validateFirstName() && validateLastName() && validateMobileNum && validateDob()) {
            fetch('http://localhost:8080/user', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': cookie
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.text())
                .then(data => {
                    swal("Success", "Data inserted successfully", "success");
                    // swal("Data added successfully");
                    document.getElementById("form-reset").reset();
                })

                .catch(error => {
                    console.log("error", error);
                })

            return true;
        }
        return false;
    }
}

async function editData(email) {

    location = 'Register.html';
    console.log("replaced location");

    sessionStorage.setItem("email", email);

}

async function deleteData(email) {

    // if (!confirm("Are you sure")) {
    //     return false;
    // }
    let booleanType = false;
    await swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (!willDelete) {
                booleanType = true;
            }
        });

    let cookie = getCookie("UserCookie");

    if (booleanType) return false;

    await fetch(`http://localhost:8080/user/${email}`, {
        method: 'DELETE',
        headers: new Headers({
            'Authorization': `${cookie}`
        })

    })
        .then(response => response.json())
        .then(data => {
            console.log("deleted data", data);
        })
        .catch(error => {
            console.log(error + "in delete");
        })

    displayUserData();
}

function getEditData() {
    let cookie = getCookie("UserCookie");
    let sessionEmailData = sessionStorage.getItem("email");
    if (sessionEmailData) {

        fetch(`http://localhost:8080/user/${sessionEmailData}`, {
            headers: new Headers({
                'Authorization': `${cookie}`
            }),
        })
            .then(response => response.json())
            .then(data => {
                console.log("Data fro edit", data);
                displayOnForm(data);
            })
            .catch(error => {
                console.log("error", error);
            })

    }

    let today = new Date().toISOString().slice(0, 10);
    dob.setAttribute("max", today);

    sessionStorage.clear();
}

function displayOnForm(userData) {
    console.log("hello from display from", userData);

    document.getElementById("clear-button").style.display = 'none';
    document.getElementById("email").value = userData.email;
    document.getElementById("email").disabled = true;
    document.getElementById("fname").value = userData.fname;
    document.getElementById("lname").value = userData.lname;
    document.getElementById("mobile").value = userData.mobile;
    document.getElementById("dob").value = userData.dob;
    document.getElementById("address").value = userData.address;
    if (userData.role != null) {
        if (userData.role.roleName == "Developer") document.getElementById("Dev").checked = true;
        else if (userData.role.roleName == "Admin") document.getElementById("Admin").checked = true;

        else document.getElementById("Manager").checked = true;
    }
    else {
        document.getElementById("Manager").checked = true;
    }

    document.getElementById("register-header").innerHTML = "EDIT DATA";
    // console.log("dataaaaaaaaaa");

    const formData = {
        "email": document.getElementById("email").value,
        "fname": document.getElementById("fname").value,
        "lname": document.getElementById("lname").value,
        "mobile": document.getElementById("mobile").value,
        "dob": document.getElementById("dob").value,
        "address": document.getElementById("address").value
    };
}


(function () {
    'use strict';

    // Create a global variable and expose it to the world
    var $myapp = {};
    self.$myapp = $myapp;

    $myapp.isValidDate = function (dateString) {

        let todayDate = new Date();

        if (new Date(dateString).getTime() >= todayDate.getTime()) {

            return false;
        }
        return true;
    };


    $myapp.isValidMobile = function (mobileNum) {
        let numRegex = /^[6-9]\d{9}$/;
        return (numRegex.test(mobileNum))

    }

    $myapp.isValidFname = function (fname) {
        let fnameRegex = /^[A-Za-z\s]*$/;
        return fnameRegex.test(fname);
    }

    $myapp.isValidLname = function (lname) {
        let lnameRegex = /^[A-Za-z\s]*$/;
        return lnameRegex.test(lname);
    }

    $myapp.isValidEmail = function (email) {
        let emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return emailRegex.test(email);
    }
})();

function getCookie(name) {
    let cookies = document.cookie.slice(11);
    // alert(cookies);
    // for (let i = 0; i < cookies.length; i++) {
    //     let cookie = cookies[i].trim();
    //     if (cookie.startsWith(name + "=")) {
    //         return cookie.substring(name.length + 1, cookie.length);
    //     }
    // }
    ;

    return cookies;
}

async function displayUserData() {
    let cookie = getCookie("UserCookie");
    console.log("cokiee", typeof cookie);
    // alert(cookie);
    if (cookie == "") {
        // swal("Login to continue");
        await swal({
            icon: 'error',
            title: 'Oops...',
            text: 'Login to continue',
        })

        location.href = "login.html";
    }
    // console.log("1sr");
    // alert(cookie);
    await fetch(`http://localhost:8080/user/countData`, {

        headers: new Headers({
            'Authorization': `${cookie}`
        }),
    })
        .then(response => response.json())
        .then(responseCount => {
            // console.log(responseCount + "total data");
            max = Math.ceil(responseCount / 10);
            // console.log(max);

        })
        .catch(err => {
            console.log(err);
        })

    let tableMain = document.getElementById("table");
    tableMain.innerHTML = "";

    tableMain.innerHTML = "<tr class='table-head'>\n" +
        "                    <th>Email</th>\n" +
        "                    <th>First Name</th>\n" +
        "                    <th>Last Name</th>\n" +
        "                    <th>Mobile</th>\n" +
        "                    <th>Date of Birth</th>\n" +
        "                    <th>Address</th>\n" +
        "                    <th>Action</th>\n" +

        "                </tr>";
    await diplayData(page, cookie);


}

async function diplayData(page, cookie) {
    // console.log("2sr");

    // alert(cookie + "cookieeeeeeee");
    console.log(cookie + "cookiee");
    // await fetch(`http://localhost:8080/user/${page}/${10}`), {
    await fetch(`http://localhost:8080/user/${page}/${10}`, {
        headers: new Headers({
            'Authorization': `${cookie}`
        }),
    })

        .then((response) => response.json())
        .then(responseData => {
            // console.log("response data", responseData);
            let tableMain = document.getElementsByClassName("table");
            let tableBody = document.createElement("tbody");
            responseData.forEach(reponseDataItem => {
                let tableRow = document.createElement("tr");
                let parsedDate;
                let key = ["email", "fname", "lname", "mobile", "dob", "address"];
                for (let i = 0; i < 6; i++) {
                    let tableData = document.createElement("td");
                    if (key[i] == "dob") {
                        const parseArray = reponseDataItem[key[i]].split("-");
                        // console.log(parseArray);
                        parsedDate = parseArray[2] + "-" + parseArray[1] + "-" + parseArray[0];
                    } else {

                        parsedDate = reponseDataItem[key[i]];
                    }
                    let tableDataText = document.createTextNode(parsedDate);
                    tableData.appendChild(tableDataText);
                    tableRow.appendChild(tableData);
                }
                let tableData = document.createElement("td");
                let tableButtonEdit = document.createElement("button");
                tableButtonEdit.textContent = "EDIT";
                tableButtonEdit.onclick = function () {
                    editData(reponseDataItem["email"]);
                }
                tableData.appendChild(tableButtonEdit);
                let tableButtonDelete = document.createElement("button");
                tableButtonDelete.textContent = "DELETE";
                tableButtonDelete.onclick = function () {
                    deleteData(reponseDataItem["email"]);
                }
                tableData.appendChild(tableButtonDelete);
                let tableButtonIcon = document.createElement("button");
                tableButtonIcon.setAttribute("title", reponseDataItem.role.roleName);
                tableButtonIcon.textContent = "i";
                tableButtonIcon.onclick = function () {
                    displayIcon(reponseDataItem.role.roleName, reponseDataItem.createDate, reponseDataItem.modifyDate);
                }
                tableData.appendChild(tableButtonIcon);
                tableRow.appendChild(tableData);
                tableBody.appendChild(tableRow);

                document.getElementById("table").appendChild(tableBody);


            })

        })
        .catch(error => {
            console.log("error", error);
        })
}

function nextPage() {
    if ((page + 1) < max) {
        page++;

        document.getElementById("currentPage").textContent = page;
        displayUserData(page);
    }
}

function prevPage() {
    if (page > 0) {
        page--;
        document.getElementById("currentPage").textContent = page;
        displayUserData(page);

    }
}

function clearData() {
    document.getElementById("warn-dob").style.visibility = 'hidden';
    document.getElementById("warn-mobile").style.visibility = 'hidden';
    document.getElementById("warn-fname").style.visibility = 'hidden';
    document.getElementById("warn-lname").style.visibility = 'hidden';
    document.getElementById("warn-email").style.visibility = 'hidden';

}


function displayIcon(role, createdAt, modifiedAt) {
    swal("Role :  " + role + "\n" + "Created at :  " + new Date(createdAt).toString().slice(0, 25) + "\n" + "Modified at :   " + new Date(modifiedAt).toString().slice(0, 25));
}


async function checkLogout() {
    let booleanType = false;
    await swal({
        title: "Are you sure?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willLogout) => {
            if (!willLogout) {
                booleanType = true;
            }
        });

    console.log(booleanType);
    if (booleanType) return true;

    return false;
}


function setCookie(name, token) {
    let cookie = name + "=" + encodeURIComponent(token);

    let expiration = new Date();
    expiration.setTime(expiration.getTime() + 30 * 60 * 1000);

    cookie += "; expires=" + expiration.toUTCString();

    document.cookie = cookie;
}

 function Logout() {
    let booleanType = false;
     swal({
        title: "Are you sure?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willLogout) => {
            if (willLogout) {
                setCookie("UserCookie", "");
                location.href='login.html'
                booleanType = true;
            }
        });

    if (booleanType) {
        return booleanType;
    }

    //    let booleanType =   confirm("Are u sure");
    //    if(booleanType){
    //     setCookie("UserCookie","");
    //     return booleanType;
    //    }

    return false;

}