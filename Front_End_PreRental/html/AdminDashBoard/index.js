getName();
getProfilePicture();


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
})
/*
document.addEventListener("DOMContentLoaded", function () {
    const sidebarLinks = document.querySelectorAll(".sidebar a");

    function setActiveLink(clickedLink) {
        sidebarLinks.forEach(link => link.classList.remove("active"));
        clickedLink.classList.add("active");
    }


    sidebarLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            setActiveLink(link);
        });
    });

    const defaultLink = document.querySelector(".sidebar a.active");
    setActiveLink(defaultLink);
});*/
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
function getName() {
    //get token in local storage
    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get name use decorded token
    const UserEmail = decodedToken.email;
    //set name in header
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/getUser',
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
            if (profileInfo) {
                profileInfo.querySelector('p').innerHTML = `Hey, <b>${userName}</b>`;
            } else {
                console.error("Profile info element not found.");
            }

        }
    })
}
function fetchAndSetProfilePicture(userEmail) {
    const token = localStorage.getItem('token');
    const profilePhotoElement = document.getElementById('profilePhoto');
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
        })
        .catch(error => {
            console.error('Error fetching profile picture:', error);
            profilePhotoElement.src = 'images/profile-1.jpg';
        });
}
