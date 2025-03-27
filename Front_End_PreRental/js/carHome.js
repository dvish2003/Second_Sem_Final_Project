getUserData();
document.getElementById('memberLink').addEventListener('click', function () {
    getUserData1();
});
function CheckExistMember() {
    const token = localStorage.getItem('token');
    const decoded = jwt_decode(token);
    const userEmail = decoded.email;

    $.ajax({
        url: 'http://localhost:8080/api/v1/member/getMemberInfo',
        method: 'GET',
        contentType: 'application/json',
        async: true,
        data: {
            email: userEmail
        },
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            console.log(response);
            if (response.data === null) {
                PopUp();
            } else {
                if (response.data.verified === true) {
                    window.location.href = 'html/MemberPage/memberPage.html';
                } else {
                    handleDeactivatedAccount(userEmail, token);
                }
            }
        },
        error: function (xhr, status, error) {
            console.error("Error checking member:", error);
        }
    });
}

function handleDeactivatedAccount(email, token) {
    Swal.fire({
        title: 'Account Deactivated',
        text: 'Your account is deactivated at the moment. Please verify your account to activate it.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Activate',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            sendVerificationEmail(email, token);
        }
    });
}

function sendVerificationEmail(email, token) {
    Swal.fire({
        title: 'Enter your email',
        input: 'email',
        inputValue: email,
        inputPlaceholder: 'Enter your email address',
        showCancelButton: true,
        confirmButtonText: 'Send Verification',
        cancelButtonText: 'Cancel',
        preConfirm: (email) => {
            if (!email) {
                Swal.showValidationMessage('Please enter a valid email address');
                return false;
            }
            return $.ajax({
                url: 'http://localhost:8080/api/v1/member/reactive',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    email: email,
                    NIC: ""
                }),
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });
        }
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                'Verification Sent!',
                `A verification email has been sent to ${result.value}. Check your mail Box`,
                'success'
            ).then(() => {
                verifyActivationCode(email, token);
            });
        }
    });
}

function verifyActivationCode(email, token) {
    Swal.fire({
        title: 'Enter Verification Code',
        input: 'text',
        inputLabel: 'Verification Code',
        inputPlaceholder: 'Enter the verification code sent to your email',
        showCancelButton: true,
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        preConfirm: (verificationCode) => {
            if (!verificationCode) {
                Swal.showValidationMessage('Please enter a verification code');
                return false;
            }
            return $.ajax({
                url: 'http://localhost:8080/api/v1/verifyMember/ReActive',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    email: email,
                    code: verificationCode
                }),
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });
        }
    }).then((result) => {
        if (result.isConfirmed) {
            if (result.value.code === 200) {
                Swal.fire({
                    title: 'Account Activated!',
                    text: 'Your account has been activated successfully.',
                    icon: 'success',
                    showConfirmButton: false,
                    timer: 2000
                }).then(() => {
                    window.location.href = '../html/MemberPage/memberPage.html';
                });
            } else {
                Swal.fire({
                    title: 'Verification Failed',
                    text: 'Invalid verification code. Please try again.',
                    icon: 'error',
                    showConfirmButton: false,
                    timer: 2000
                }).then(() => {
                    verifyActivationCode(email, token);
                });
            }
        }
    });
}


function PopUp() {
    Swal.fire({
        title: 'Join Member Program',
        html: `
            <p style="font-size: 16px; color: #555; margin-bottom: 20px;">
                By joining our member program, you gain access to exclusive benefits, discounts, and personalized services.
            </p>
            <p style="font-size: 14px; color: #777;">
                Please review our <a href="#" target="_blank" style="color: #28a745; text-decoration: underline;">Terms and Conditions</a> before proceeding.
            </p>
        `,
        icon: 'info',
        iconColor: '#28a745',
        showCancelButton: true,
        confirmButtonText: 'Yes, Join Now',
        cancelButtonText: 'No, Thanks',
        confirmButtonColor: '#28a745',
        cancelButtonColor: '#6c757d',
        customClass: {
            popup: 'custom-swal-popup',
            title: 'custom-swal-title',
            htmlContainer: 'custom-swal-html',
            confirmButton: 'custom-swal-confirm',
            cancelButton: 'custom-swal-cancel',
        },
        showClass: {
            popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutUp',
        },
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'html/MemberPage/memberPage.html';
        }
    });
}


function previewImage(input, previewId) {
    const preview = document.getElementById(previewId);
    const file = input.files[0];
    const reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "/api/placeholder/100/100";
    }
}

function getUserData1() {
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
        headers: {
            Authorization: 'Bearer ' + token
        },
        error: function (jqXHR, textStatus, errorTh) {
            console.log("Error: ", errorTh);
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'You are not logged in. Please log in to continue.',
                customClass: {
                    popup: 'custom-swal-popup',
                    title: 'custom-swal-title',
                    htmlContainer: 'custom-swal-html',
                    confirmButton: 'custom-swal-confirm',
                },
                showClass: {
                    popup: 'animate__animated animate__fadeInDown',
                },
                hideClass: {
                    popup: 'animate__animated animate__fadeOutUp',
                },
            }).then(() => {
                window.location.href = '../index.html';
            });
        },

        success: function (response) {
            console.log("User details fetched successfully");
            const email = response.data.email;
            const name = response.data.name;
            const password1 = response.data.password;
            const nic = response.data.national_id;
            const address = response.data.address;
            const city = response.data.city;
            const postal_code = response.data.postal_code;
            const primary_phone_number = response.data.primary_phone_number;
            const secondary_phone_number = response.data.secondary_phone_number;
            //set text feild in data
            //show console in data
            console.log(password1, email, name, address, nic, city, secondary_phone_number, primary_phone_number, postal_code);


            //Check All Data Null
            if (address === null || nic === null || city === null || postal_code === null || primary_phone_number === null || secondary_phone_number === null) {
                $('#saveCustomerDetailsModal').modal('show');
                //set data in text field
                $('#customerEmail').val(email)
                $('#customerName').val(name)

            } else {
                CheckExistMember();
            }

        }
    })

}

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
        headers: {
            Authorization: 'Bearer ' + token
        },
        error: function (jqXHR, textStatus, errorTh) {
            console.log("Error: ", errorTh);
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'You are not logged in. Please log in to continue.',
                customClass: {
                    popup: 'custom-swal-popup',
                    title: 'custom-swal-title',
                    htmlContainer: 'custom-swal-html',
                    confirmButton: 'custom-swal-confirm',
                },
                showClass: {
                    popup: 'animate__animated animate__fadeInDown',
                },
                hideClass: {
                    popup: 'animate__animated animate__fadeOutUp',
                },
            }).then(() => {
                window.location.href = '../index.html';
            });
        },

        success: function (response) {
            console.log("User details fetched successfully");
            const email = response.data.email;
            const name = response.data.name;
            const password1 = response.data.password;
            const nic = response.data.national_id;
            const address = response.data.address;
            const city = response.data.city;
            const postal_code = response.data.postal_code;
            const primary_phone_number = response.data.primary_phone_number;
            const secondary_phone_number = response.data.secondary_phone_number;
            //set text feild in data
            //show console in data
            console.log(password1, email, name, address, nic, city, secondary_phone_number, primary_phone_number, postal_code);


            if (address === null || nic === null || city === null || postal_code === null || primary_phone_number === null || secondary_phone_number === null) {
                $('#saveCustomerDetailsModal').modal('show');
                $('#customerEmail').val(email)
                $('#customerName').val(name)

            } else {
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
    const profilePicture1 = $('#customerProfilePic')[0].files[0];

    /*==========================Validation========================*/
    if (nic.length !== 12 || !/^\d+$/.test(nic)) {
        Swal.fire({
            icon: 'error',
            title: 'Invalid NIC',
            text: 'NIC must be exactly 12 digits.',
            customClass: {
                popup: 'custom-swal-popup',
                title: 'custom-swal-title',
                htmlContainer: 'custom-swal-html',
                confirmButton: 'custom-swal-confirm',
            },
            showClass: {
                popup: 'animate__animated animate__fadeInDown',
            },
            hideClass: {
                popup: 'animate__animated animate__fadeOutUp',
            },
        });
        return;
    }

    if (primaryContact.length !== 10 || !/^\d+$/.test(primaryContact)) {
        Swal.fire({
            icon: 'error',
            title: 'Invalid Primary Contact',
            text: 'Primary phone number must be exactly 10 digits.',
            customClass: {
                popup: 'custom-swal-popup',
                title: 'custom-swal-title',
                htmlContainer: 'custom-swal-html',
                confirmButton: 'custom-swal-confirm',
            },
            showClass: {
                popup: 'animate__animated animate__fadeInDown',
            },
            hideClass: {
                popup: 'animate__animated animate__fadeOutUp',
            },
        });
        return;
    }

    if (secondaryContact && (secondaryContact.length !== 10 || !/^\d+$/.test(secondaryContact))) {
        Swal.fire({
            icon: 'error',
            title: 'Invalid Secondary Contact',
            text: 'Secondary phone number must be exactly 10 digits.',
            customClass: {
                popup: 'custom-swal-popup',
                title: 'custom-swal-title',
                htmlContainer: 'custom-swal-html',
                confirmButton: 'custom-swal-confirm',
            },
            showClass: {
                popup: 'animate__animated animate__fadeInDown',
            },
            hideClass: {
                popup: 'animate__animated animate__fadeOutUp',
            },
        });
        return;
    }

    /*=================================Update user====================================*/
    const formData = new FormData();
    formData.append('file', profilePicture1);
    formData.append('userDTO', new Blob([JSON.stringify({
            email: email,
            name: name,
            role: "user",
            national_id: nic,
            address: address,
            city: city,
            postal_code: postalCode,
            primary_phone_number: primaryContact,
            secondary_phone_number: secondaryContact
        })],
        {type: 'application/json'}));

    $.ajax({
        url: 'http://localhost:8080/api/v1/user/updateUser',
        method: 'PUT',
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token')
        },
        success: function (response) {
            console.log("User details saved successfully:", response);
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Customer details saved successfully!',
                customClass: {
                    popup: 'custom-swal-popup',
                    title: 'custom-swal-title',
                    htmlContainer: 'custom-swal-html',
                    confirmButton: 'custom-swal-confirm',
                },
                showClass: {
                    popup: 'animate__animated animate__fadeInDown',
                },
                hideClass: {
                    popup: 'animate__animated animate__fadeOutUp',
                },
            }).then(() => {
                window.location.href = "car.html";
            });
        },
        error: function (xhr, status, error) {
            console.error("Error saving user data:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Failed to save customer details. Please try again.',
                customClass: {
                    popup: 'custom-swal-popup',
                    title: 'custom-swal-title',
                    htmlContainer: 'custom-swal-html',
                    confirmButton: 'custom-swal-confirm',
                },
                showClass: {
                    popup: 'animate__animated animate__fadeInDown',
                },
                hideClass: {
                    popup: 'animate__animated animate__fadeOutUp',
                },
            });
        }
    });
}

