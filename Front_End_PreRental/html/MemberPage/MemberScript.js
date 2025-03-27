getName();
getMemberData();
getProfilePicture();
//create object
let User;
// DOM Elements

function getProfilePicture() {
    //get token in local storage
    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get email use decorded token
    const userEmail = decodedToken.email;
    //get password use decorded token
    console.log(userEmail);
    fetchAndSetProfilePicture(userEmail);

}


const sideMenu = document.querySelector('aside');
const menuBtn = document.getElementById('menu-btn');
const closeBtn = document.getElementById('close-btn');
const darkMode = document.querySelector('.dark-mode');

menuBtn.addEventListener('click', () => {
    sideMenu.style.display = 'block';
});

closeBtn.addEventListener('click', () => {
    sideMenu.style.display = 'none';
});

darkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode-variables');
    darkMode.querySelector('span:nth-child(1)').classList.toggle('active');
    darkMode.querySelector('span:nth-child(2)').classList.toggle('active');
});

/*===================================save Member=========================================================*/
function getMemberData() {
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
        url: 'http://localhost:8080/api/v1/member/getMemberInfo',
        method: 'GET',
        contentType: 'application/json',
        async: true,
        data: {
            email: userEmail
        },
        headers: {
            'Authorization': 'Bearer '+ token
        },
        success: function(response) {
            console.log(response)
            if (response.data === null) {
                showModal();
                userGetData();
            }
        },
    })
};

function userGetData(){
    //get token in local storage
    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get email use decorded token
    const userEmail = decodedToken.email;
    //get all data use email
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/getUser',
        method: 'GET',
        contentType: 'application/json',
        async: true,
        headers: {
            'Authorization': 'Bearer '+ token
        },
        data: {
            email: userEmail
        },

        success: function (response) {
            console.log(response)
            if (response.data!== null) {
                User = response.data;
                //set all field to all data
                document.getElementById('customerNic').value = response.data.national_id;
                document.getElementById('customerName').value = response.data.name;
                document.getElementById('customerAddress').value = response.data.address;
                document.getElementById('customerCity').value = response.data.city;
                document.getElementById('customerPostalCode').value = response.data.postal_code;
                document.getElementById('customerPrimaryNumber').value = response.data.primary_phone_number;
                document.getElementById('customerEmail').value = response.data.email;
                console.log("dddddddddddddddddddd",response.data.uid)
                /*
                                    document.getElementById('customerProfilePreview').src = '/api/v1/user/getProfilePic/' + response.data.id;
                */
            }
        }
    })
}


function saveMemberDetails(){
    //get all field value
    const nic = document.getElementById('customerNic').value;
    const name = document.getElementById('customerName').value;
    const address = document.getElementById('customerAddress').value;
    const city = document.getElementById('customerCity').value;
    const postalCode = document.getElementById('customerPostalCode').value;
    const primaryNumber = document.getElementById('customerPrimaryNumber').value;
    const email = document.getElementById('customerEmail').value;

    //validate Mobile Number
    if (!/^\d{10}$/.test(primaryNumber)) {
        Swal.fire({
            title: 'Error!',
            text: 'Primary contact must be exactly 10 digits',
            icon: 'error',
            confirmButtonText: 'Okay'
        });
        return;
    }
    $.ajax({
        url: 'http://localhost:8080/api/v1/member/saveMemberInfo',
        method: 'POST',
        contentType: 'application/json',
        async: true,
        data: JSON.stringify({
            email: email,
            name: name,
            address: address,
            contact: primaryNumber,
            NicNumber: nic,
            userDTO:User

        }),
        headers: {
            'Authorization': 'Bearer '+ localStorage.getItem('token')
        },
        success: function (response) {
            console.log(response)
            Swal.fire({
                title: 'Success!',
                text: 'Customer details saved successfully',
                icon:'success',
                confirmButtonText: 'Okay'
            });
            closeModal();
        },
    })
}
function showModal() {
    const modal = document.getElementById('saveCustomerDetailsModal');
    modal.style.display = 'flex';
}
function closeModal() {
    const modal = document.getElementById('saveCustomerDetailsModal');
    modal.style.display = 'none';
}

/*===============================================Set Name=====================================================================*/
function getName() {
    //get token in local storage
    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get name use decorded token
    const UserEmail = decodedToken.email;
    //set name in header
   $.ajax({
       url: 'http://localhost:8080/api/v1/member/getMemberInfo',
       method: 'GET',
       contentType: 'application/json',
       async: true,
       headers: {
           'Authorization': 'Bearer '+ token
       },
       data: {
           email: UserEmail
       },
       success: function(response) {
           const userName = response.data.name;
           const profileInfo = document.querySelector('.profile .info');
           const profileInfo2 = document.querySelector('.profile-header');
           if (profileInfo && profileInfo2) {
               profileInfo.querySelector('p').innerHTML = `Hey, <b>${userName}</b>`;
               profileInfo2.querySelector('h2').innerHTML = `profile-name${userName}`;
           } else {
               console.error("Profile info element not found.");
           }

       }
   })
}


/*=====================================================Set Profile picture=========================================================================*/
function fetchAndSetProfilePicture(userEmail) {
    const token = localStorage.getItem('token');
    const profilePhotoElement = document.getElementById('profilePhoto');
    const profilePhotoElement2 = document.getElementById('profilePhoto2');

    fetch(`http://localhost:8080/api/v1/user/getProfilePic/${userEmail}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer '+ token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch profile picture');
            }
            return response.blob();
        })
        .then(blob => {
            const imageUrl = URL.createObjectURL(blob);
            profilePhotoElement.src = imageUrl;
            profilePhotoElement2.src = imageUrl;
        })
        .catch(error => {
            console.error('Error fetching profile picture:', error);
            profilePhotoElement.src = 'images/profile-1.jpg';
        });
}

