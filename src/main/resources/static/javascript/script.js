

function validateEmail(){
    let email = document.getElementById("email");
    let emailRegex =  /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(emailRegex.test(email.value)){
        return true;
    }
    alert("Enter valid Email");
    email.focus();
    return false;
}

function validateFirstName(){
    let firstName = document.getElementById(("fname"));
    let fnameRegex = /^[A-Za-z\s]*$/;
    if(fnameRegex.test(firstName.value)){
        return true;
    }
    alert("First name should contain only alphabets");
    firstName.focus();
    return false;
}

function validateLastName(){
    let lastName = document.getElementById(("lname"));
    let lnameRegex = /^[A-Za-z\s]*$/;
    if(lnameRegex.test(lastName.value)){
        return true;
    }
    alert("Last name should contain only alphabets");
    lastName.focus();
    return false;
}

function validateDob(){
    let dob = document.getElementById("dob");
    let todayDate = new Date();

    if (new Date(dob.value).getTime() >= todayDate.getTime()) {
        alert("The Date must be lesser or Equal to today date");
        dob.focus();
        return false;
    }
    return true;
}

function validateMobileNum(){
    let mobileNum = document.getElementById("mobile");
    let numRegex = /^[6789]\d{9}$/;
    if(numRegex.test(mobileNum.value)){
        return true;
    }
    alert("Enter valid number");
    mobileNum.focus();
    return false;

}

function checkAllValidation(){
    const formData = {
        "email" : document.getElementById("email").value,
        "fname" : document.getElementById("fname").value,
        "lname" : document.getElementById("lname").value,
        "mobile" : document.getElementById("mobile").value,
        "dob" : document.getElementById("dob").value,
        "address" : document.getElementById("address").value
    };

    if(validateEmail() && validateFirstName() && validateLastName() && validateMobileNum && validateDob()){
        fetch('/insert',{
            method : 'POST',
            headers : {
                'Content-type' : 'application/json'
            },
            body : JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Datas are" , data);
            })

            .catch(error => {
                console.log("error" , error);
            })

        return true;
    }
    return false;
}


async function display(){

    let tableMain = document.getElementById("table");
    tableMain.innerHTML = "";

    tableMain.innerHTML = "<tr>\n" +
        "                    <th>Email</th>\n" +
        "                    <th>First Name</th>\n" +
        "                    <th>Last Name</th>\n" +
        "                    <th>Mobile</th>\n" +
        "                    <th>Date of Birth</th>\n" +
        "                    <th>Address</th>\n" +
        "                    <th>Action</th>\n" +
        "                </tr>";
    await fetch('/displayData')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let tableMain = document.getElementsByClassName("table");
            data.forEach(item => {
                let tableRow = document.createElement("tr");

                let keyValues = ["email","fname","lname","mobile",'dob',"address"];
                for(let i=0;i<6;i++){
                    let tableData = document.createElement("td");
                    let value = item[keyValues[i]];
                    let child = document.createTextNode(value);
                    tableData.appendChild(child);
                    tableRow.appendChild(tableData);
                }
                let tableData = document.createElement("td");
                let tableButtonEdit = document.createElement("button");
                tableButtonEdit.textContent = "EDIT";
                tableButtonEdit.onclick = function(){
                    editData(item["email"]);
                }
                tableData.appendChild(tableButtonEdit);
                let tableButtonDelete = document.createElement("button");
                tableButtonDelete.textContent = "DELETE";
                tableButtonDelete.onclick = function(){
                    deleteData(item["email"]);
                }
                tableData.appendChild(tableButtonDelete);


                tableRow.appendChild(tableData);

                document.getElementById("table").appendChild(tableRow);
                console.log("items" ,item);
            })

        })
        .catch(error => {
            console.log("error" , error);
        })
}

function editData(email){
    alert(email);
    fetch(`/updateData/${email}`,{
        method : 'PUT'
    })
        .then(response => response.json())
        .then(data => {
            console.log("data from edit", data);
        })
        .catch(error => {
            console.log("error" , error);
        })

}

async function deleteData(email){
    // alert("Delete data alert" + val);

    await fetch(`/delete/${email}`,{
        method : 'DELETE'
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



