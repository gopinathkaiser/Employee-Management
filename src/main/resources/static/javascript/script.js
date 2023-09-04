
let arr = [
    {}
];

async function validateEmail() {
    let email = document.getElementById("email");
    let emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    let checkForAlert = 1;
    console.log("email validatinggg");
    if (emailRegex.test(email.value)) {
        await fetch(`http://localhost:8080/user/verify/${email.value}`)
            .then(response => response.json())
            .then(data => {
                //console.log(data);
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
        email.focus();
        return false;
    }
    else {
        if (checkForAlert == 2) {
            alert("Email already Exists");
            email.focus();
            return false;
        } else {
            return true;
        }
    }

}

function validateFirstName() {
    let firstName = document.getElementById(("fname"));
    let fnameRegex = /^[A-Za-z\s]*$/;
    if (fnameRegex.test(firstName.value)) {
        return true;
    }
    alert("First name should contain only alphabets");
    firstName.focus();
    return false;
}

function validateLastName() {
    let lastName = document.getElementById(("lname"));
    let lnameRegex = /^[A-Za-z\s]*$/;
    if (lnameRegex.test(lastName.value)) {
        return true;
    }
    alert("Last name should contain only alphabets");
    lastName.focus();
    return false;
}

function validateDob() {
    let dob = document.getElementById("dob");
    let todayDate = new Date();

    if (new Date(dob.value).getTime() >= todayDate.getTime()) {
        alert("The Date must be lesser or Equal to today date");
        dob.focus();
        return false;
    }
    return true;
}

function validateMobileNum() {
    let mobileNum = document.getElementById("mobile");
    let numRegex = /^[6789]\d{9}$/;
    if (numRegex.test(mobileNum.value)) {
        return true;
    }
    alert("Enter valid number");
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

    // console.log(await validateEmail());
    if (document.getElementById("email").disabled == true) {
        
        if (validateFirstName() && validateLastName() && validateMobileNum && validateDob()) {
            console.log("data editing");
            fetch('http://localhost:8080/user', {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log("data inserted after editing", data);
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
                    console.log("Datas are", data);
                })

                .catch(error => {
                    console.log("error", error);
                })

            return true;
        }
        return false;
    }
}


async function display() {
    //alert("deru");
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
    await fetch('http://localhost:8080/user',{
        method : 'GET'
    })

        .then(response => response.json())
        .then(data => {
            //console.log(data);
            //alert("deru");
            console.log(arr + "arraytyy");
            let tableMain = document.getElementsByClassName("table");
            data.forEach(item => {
                let tableRow = document.createElement("tr");
                let value ;
                let keyValues = ["email", "fname", "lname", "mobile", "dob", "address"];
                for (let i = 0; i < 6; i++) {
                    let tableData = document.createElement("td");
                    if(keyValues[i] == "dob"){
                        const parseArray = item[keyValues[i]].split("-");
                        value = parseArray[2] + "-" + parseArray[1] + "-" + parseArray[0];
                        // console.log("Date value" , value);
                    }else{
                        value = item[keyValues[i]];
                    // console.log("heloooooooo");
                    }
                    // console.log("heloooooooo");
                    let child = document.createTextNode(value);
                    tableData.appendChild(child);
                    tableRow.appendChild(tableData);
                }
                let tableData = document.createElement("td");
                let tableButtonEdit = document.createElement("button");
                tableButtonEdit.textContent = "EDIT";
                tableButtonEdit.onclick = function () {
                    editData(item["email"]);
                }
                tableData.appendChild(tableButtonEdit);


                let tableButtonDelete = document.createElement("button");
                tableButtonDelete.textContent = "DELETE";
                tableButtonDelete.onclick = function () {
                    deleteData(item["email"]);
                }
                tableData.appendChild(tableButtonDelete);


                tableRow.appendChild(tableData);

                document.getElementById("table").appendChild(tableRow);
                // console.log("items" ,item);
                addToLocalArr(item);

            })

        })
        .catch(error => {
            console.log("error", error);
        })
}


//  function editData(email){
//     let ans = arr.find(item => item.email == email);
//      location = "Register.html";
//     console.log(ans);
// }


async function editData(email) {
    // alert(email);
    console.log("edit button clicked");
    // window.location.href = "/Edit";



    // await fetch(`http://localhost:8080/updateData/${email}`, {
    //     method: 'PUT'
    // })
    //     .then(response => response.json())
    //     .then(data => {
    //         //alert("check session");
    //         //  window.location.href = "http://localhost:8080/Edit";
    //         console.log("before reload");
    //         location = 'Register.html';
    //         console.log("replaced location");
    //         // localStorage.set Item("email",data.email);
    //         sessionStorage.setItem("email", data.email);
    //
    //
    //     })
    //     .catch(error => {
    //         console.log("error", error);
    //     })

            console.log("before reload");
            location = 'Register.html';
            console.log("replaced location");
            // localStorage.set Item("email",data.email);
            sessionStorage.setItem("email", email);

}

async function deleteData(email) {

    if (!confirm("Are you sure")) {
        return false;
    }

    await fetch(`http://localhost:8080/user/${email}`, {
        method: 'DELETE'
    })
        // .then(response => response.json())
        .then(data => {
            console.log("deleted data", data);
        })
        .catch(error => {
            console.log(error + "in delete");
        })

    display();
}

function getEditData() {
    let sessionData = sessionStorage.getItem("email");
    console.log(sessionData);
    if (sessionData) {

        fetch(`http://localhost:8080/user/${sessionData}`)
            .then(response => response.json())
            .then(data => {
                console.log("Data fro edit", data);
                displayOnForm(data);
            })
            .catch(error => {
                console.log("error", error);
            })

    }

    sessionStorage.clear();
}

function displayOnForm(data) {
    console.log("hello from display from", data);
    document.getElementById("email").value = data.email;
    document.getElementById("email").disabled = true;
    document.getElementById("fname").value = data.fname;
    document.getElementById("lname").value = data.lname;
    document.getElementById("mobile").value = data.mobile;
    document.getElementById("dob").value = data.dob;
    document.getElementById("address").value = data.address;

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

    // fetch('http://localhost:8080/insertEditedData', {
    //     method: 'POST',
    //     headers: {
    //         'Content-type': 'application/json'
    //     },
    //     body: JSON.stringify(formData)
    // })
    //     .then(response => response.json())
    //     .then(data => {
    //         console.log("data inserted after editing", data);
    //     })
    //     .catch(error => {
    //         console.log("error inserting after edit", error);
    //     })


}

// function checkValid(){
//     event.preventDefault();
// }

function navigateToAdd() {
    location.replace("Register.html");
}

function navigateToIndex() {
    location.replace("index.html");
}

function addToLocalArr(data) {
    arr.push(data);
    //alert("calling");
    // console.log("data " ,data);
    // console.log(arr + "added to local arrat");

    // arr.forEach(function(item){
    //     console.log(item.email + " " + item.fname);
    // })
}




