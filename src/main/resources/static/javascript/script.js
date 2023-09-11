let page = 0;
let max = 0;
async function validateEmail() {
    let email = document.getElementById("email");
    console.log("validate email called");
    let emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    let checkForAlert = 1;
    if (emailRegex.test(email.value)) {
        await fetch(`http://localhost:8080/user/verify/${email.value}`)
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
        alert("Enter a valid email");
        document.getElementById("warn-email").style.visibility = 'visible';
        email.focus();
        return false;
    }
    else {
        if (checkForAlert == 2) {
            alert("Email already Exists");
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
    alert("First name should contain only alphabets");
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
    alert("Last name should contain only alphabets");
    document.getElementById("warn-lname").style.visibility = 'visible';

    lastName.focus();
    return false;
}


function validateDob() {
    let dob = document.getElementById("dob");
    let todayDate = new Date();

    if (new Date(dob.value).getTime() >= todayDate.getTime()) {
        alert("The Date must be lesser or Equal to today date");
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
    alert("Enter valid number");
    document.getElementById("warn-mobile").style.visibility = 'visible';

    mobileNum.focus();
    return false;

}

async function checkAllValidation() {
    event.preventDefault();
    const formData = {
        "email": document.getElementById("email").value,
        "fname": document.getElementById("fname").value,
        "lname": document.getElementById("lname").value,
        "mobile": document.getElementById("mobile").value,
        "dob": document.getElementById("dob").value,
        "address": document.getElementById("address").value
    };

    if (document.getElementById("email").disabled == true) {

        if (validateFirstName() && validateLastName() && validateMobileNum && validateDob()) {
            fetch('http://localhost:8080/user', {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    alert("Data modified successfully");
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
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    alert("data added successfully");
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


// async function displayUserData() {

//     let tableMain = document.getElementById("table");
//     tableMain.innerHTML = "";

//     tableMain.innerHTML = "<tr class='table-head'>\n" +

//         "                    <th>Email</th>\n" +
//         "                    <th>First Name</th>\n" +
//         "                    <th>Last Name</th>\n" +
//         "                    <th>Mobile</th>\n" +
//         "                    <th>Date of Birth</th>\n" +
//         "                    <th>Address</th>\n" +
//         "                    <th>Action</th>\n" +

//         "                </tr>";
//     await fetch('http://localhost:8080/user/', {
//         method: 'GET'
//     })

//         .then(response => response.json())
//         .then(responseData => {

//             let tableMain = document.getElementsByClassName("table");
//             responseData.forEach(reponseDataItem => {
//                 let tableRow = document.createElement("tr");
//                 let parsedDate;
//                 let key = ["email", "fname", "lname", "mobile", "dob", "address"];
//                 for (let i = 0; i < 6; i++) {
//                     let tableData = document.createElement("td");
//                     if (key[i] == "dob") {
//                         const parseArray = reponseDataItem[key[i]].split("-");
//                         parsedDate = parseArray[2] + "-" + parseArray[1] + "-" + parseArray[0];
//                     } else {
//                         parsedDate = reponseDataItem[key[i]];
//                     }
//                     let tableDataText = document.createTextNode(parsedDate);
//                     tableData.appendChild(tableDataText);
//                     tableRow.appendChild(tableData);
//                 }
//                 let tableData = document.createElement("td");
//                 let tableButtonEdit = document.createElement("button");
//                 tableButtonEdit.textContent = "EDIT";
//                 tableButtonEdit.onclick = function () {
//                     editData(reponseDataItem["email"]);
//                 }
//                 tableData.appendChild(tableButtonEdit);
//                 let tableButtonDelete = document.createElement("button");
//                 tableButtonDelete.textContent = "DELETE";
//                 tableButtonDelete.onclick = function () {
//                     deleteData(reponseDataItem["email"]);
//                 }
//                 tableData.appendChild(tableButtonDelete);


//                 tableRow.appendChild(tableData);

//                 document.getElementById("table").appendChild(tableRow);

//             })

//         })
//         .catch(error => {
//             console.log("error", error);
//         })
// }


async function editData(email) {
    console.log("edit button clicked");
    console.log("before reload");
    location = 'Register.html';
    console.log("replaced location");

    sessionStorage.setItem("email", email);

}

async function deleteData(email) {

    if (!confirm("Are you sure")) {
        return false;
    }

    await fetch(`http://localhost:8080/user/${email}`, {
        method: 'DELETE'
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
    let sessionEmailData = sessionStorage.getItem("email");
    if (sessionEmailData) {

        fetch(`http://localhost:8080/user/${sessionEmailData}`)
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

    document.getElementById("register-header").innerHTML = "EDIT DATA";
    console.log("dataaaaaaaaaa");

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


async function displayUserData() {

    await fetch(`http://localhost:8080/user/countData`)
        .then(response => response.json())
        .then(responseCount => {
            console.log(responseCount + "total data");
            max = Math.ceil(responseCount / 10);
            console.log(max);
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

    await diplayData(page);

}

async function diplayData(page) {
    await fetch(`http://localhost:8080/user/${page}/${10}`)

        .then((response) => response.json())
        .then(responseData => {

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

function clearData(){
    document.getElementById("warn-dob").style.visibility = 'hidden';
    document.getElementById("warn-mobile").style.visibility = 'hidden';
    document.getElementById("warn-fname").style.visibility = 'hidden';
    document.getElementById("warn-lname").style.visibility = 'hidden';
    document.getElementById("warn-email").style.visibility = 'hidden';

}


