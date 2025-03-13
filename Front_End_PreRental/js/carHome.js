getUserData();
function getUserData() {
    //get token in local storage
    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get email use decorded token
    const userEmail = decodedToken.email;
    //get password use decorded token
    console.log(userEmail);

    //get all data use email
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/getUser',
        method: 'GET',
        contentType: 'application/json',
        async: true,
        data: {
            email: userEmail
        },
        error: function (jqXHR, textStatus, errorTh){
            console.log("Error: ", errorTh);
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Failed to fetch user data'
            }).then(()=>{
                window.location.href = 'index.html';
            });
        },
        success: function (response) {
            console.log("User details fetched successfully");
            const email = response.data.email;
            const  name = response.data.name;
            const password1 = response.data.password;
            const nic = response.data.national_id;
            const address = response.data.address;
            const city = response.data.city;
            const postal_code = response.data.postal_code;
            const primary_phone_number = response.data.primary_phone_number;
            const secondary_phone_number = response.data.secondary_phone_number;
            //set text feild in data
            //show console in data
            console.log(password1,email,name,address,nic,city,secondary_phone_number,primary_phone_number,postal_code);


            //Check All Data Null
            if(address === null || nic === null || city === null || postal_code === null || primary_phone_number === null || secondary_phone_number === null){
                $('#saveCustomerDetailsModal').modal('show');
                //set data in text field
                $('#customerEmail').val(email)
                $('#customerName').val(name)

            }else{
                console.log("All data are filled");
            }

        }
    })
}
function saveCustomerDetails() {
    const name = $('#customerName').val();
    const email = $('#customerEmail').val();
    const nic = $('#customerNic').val();
    const address = $('#customerAddress').val();
    const city = $('#customerCity').val();
    const postalCode = $('#customerPostalCode').val();
    const primaryContact = $('#customerPrimaryNumber').val();
    const secondaryContact = $('#customerSecondNumber').val();

    /*==========================Validation========================*/
    if (nic.length !== 10 || !/^\d+$/.test(nic)) {
        Swal.fire('Error', 'NIC must be exactly 10 digits.', 'error');
        return;
    }

    if (primaryContact.length !== 10 || !/^\d+$/.test(primaryContact)) {
        Swal.fire('Error', 'Primary phone number must be exactly 10 digits.', 'error');
        return;
    }

    if (secondaryContact && (secondaryContact.length !== 10 || !/^\d+$/.test(secondaryContact))) {
        Swal.fire('Error', 'Secondary phone number must be exactly 10 digits.', 'error');
        return;
    }

    /*=================================Update user====================================*/
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/updateUser',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            email: email,
            name: name,
            role: "user",
            national_id: nic,
            address: address,
            city: city,
            postal_code: postalCode,
            primary_phone_number: primaryContact,
            secondary_phone_number: secondaryContact
        }),
        success: function (response) {
            console.log("User details saved successfully:", response);
            Swal.fire('Success', 'Customer details saved successfully!', 'success').then(() => {
                window.location.href = "car.html"; // Redirect to the home page
            });
        },
        error: function (xhr, status, error) {
            console.error("Error saving user data:", error);
            Swal.fire('Error', 'Failed to save customer details.', 'error');
        }
    });
}
