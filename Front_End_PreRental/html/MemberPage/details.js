let vehicleDTO;
function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

document.addEventListener('DOMContentLoaded', function() {
    const plateNumber = getQueryParam('plate');
    if (plateNumber) {
        fetchVehicleDetails(plateNumber);
    } else {
        console.error('No plate number provided in URL');
    }
});

function fetchVehicleDetails(plateNumber) {
    localStorage.setItem('plateNumber', plateNumber);

    $.ajax({
        method: "POST",
        url: 'http://localhost:8080/api/v1/vehicle/getVehicle',
        contentType: "application/json",
        async: true,
        data: JSON.stringify({
            plate_number: plateNumber
        }),
        success: function(response) {
            if (response.data) {
                console.log(response.data);
                populateVehicleDetails(response.data);
                populateVehicleDetailsProfile(response.data);
                vehicleDTO = response.data;
                document.getElementById('total2').value ="$" + vehicleDTO.depositAmount;
                document.getElementById('pickupLocation').value =vehicleDTO.city;
            } else {
                console.error('No vehicle data received');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching vehicle details:', error);
        }
    });
}

function populateVehicleDetailsProfile(vehicle){
    const email = vehicle.owner.email;
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/getUser',
        method: 'GET',
        contentType: 'application/json',
        async: true,
        headers: {
            'Authorization': 'Bearer '+ localStorage.getItem('token')
        },
        data: {
            email: email,
        },
        success: function(response) {
            const fileName = response.data.fileName;
            const email = vehicle.owner.email;
            const phoneNumber = vehicle.owner.contact;
            const ownerContainer = document.getElementById('ownerContainer');
            ownerContainer.innerHTML = `
    <img src="/uploads/${fileName}" class="rounded-circle me-3" width="50" height="50" alt="Owner">
    <div>
        <h6 class="mb-0">Rented by ${vehicle.owner?.name || 'Owner'}</h6>
        <small class="text-muted">Member since ${vehicle.owner?.joinDate || '2020'}</small>
    </div>
    <div class="ms-auto">
        <button class="btn btn-outline-primary btn-sm" id="contactOwnerBtn">
            <i class="bi bi-chat"></i> Contact
        </button>
    </div>
`;
            document.getElementById('contactOwnerBtn').addEventListener('click', function() {
                Swal.fire({
                    title: `${vehicle.owner?.name || 'Owner'}`,
                    html: `
            <div class="text-start">
                <div class="d-flex align-items-center mb-3">
                    <i class="bi bi-envelope-fill text-primary me-2 fs-4"></i>
                    <a href="mailto:${email}" class="text-decoration-none">${email}</a>
                </div>
                <div class="d-flex align-items-center">
                    <i class="bi bi-telephone-fill text-success me-2 fs-4"></i>
                    <a href="tel:${phoneNumber}" class="text-decoration-none">${phoneNumber}</a>
                </div>
            </div>
        `,
                    showCloseButton: true,
                    showConfirmButton: false,
                    width: '450px',
                    padding: '2rem',
                    customClass: {
                        popup: 'border-radius-15',
                        htmlContainer: 'text-start'
                    },
                    background: '#f8f9fa',
                    backdrop: `
            rgba(0,0,0,0.4)
            url("/images/nyan-cat.gif")
            left top
            no-repeat
        `
                });
            });
        }
    })
}

function populateVehicleDetails(vehicle) {
    const mainImageContainer = document.getElementById('mainImageContainer');
    mainImageContainer.innerHTML = `
            <img id="mainImage" src="/uploads/${vehicle.fileName}"
                 onerror="this.onerror=null;this.src='https://via.placeholder.com/800x600?text=Vehicle+Image'"
                 class="vehicle-image" alt="${vehicle.brand} ${vehicle.model}">
        `;
    const titleContainer = document.getElementById('titleContainer');
    titleContainer.innerHTML = `
            <h1 class="mb-0">${vehicle.brand} ${vehicle.model} ${vehicle.year}</h1>
            <span class="badge bg-success fs-6">
                <i class="bi bi-star-fill"></i> 4.8 (24 reviews)
            </span>
        `;

    const basicInfoContainer = document.getElementById('basicInfoContainer');
    basicInfoContainer.innerHTML = `
            <i class="bi bi-geo-alt me-2"></i>
            <span class="me-3">${vehicle.owner?.address || 'Location not specified'}</span>
            <i class="bi bi-car-front me-2"></i>
            <span>${vehicle.vehicleType} • ${vehicle.color} • ${vehicle.transmission}</span>
        `;


    const featuresContainer = document.getElementById('featuresContainer');
    let featuresHTML = '';
    if (vehicle.airCondition) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-snow"></i> Air Conditioning</span>`;
    if (vehicle.bluetooth) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-bluetooth"></i> Bluetooth</span>`;
    if (vehicle.Navigation) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-signpost"></i> Navigation</span>`;
    if (vehicle.sunroof) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-sun"></i> Sunroof</span>`;
    if (vehicle.cruiseControl) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-speedometer2"></i> Cruise Control</span>`;
    if (vehicle.backCamera) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-camera-video"></i> Backup Camera</span>`;
    if (vehicle.heatedSeat) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-thermometer-sun"></i> Heated Seats</span>`;
    if (vehicle.childSeat) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-emoji-angry"></i> Child Seat</span>`;
    if (vehicle.tollPass) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-pass"></i> Toll Pass</span>`;
    if (vehicle.gpsTracker) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-geo-alt"></i> GPS Tracker</span>`;
    if (vehicle.wifiHotspot) featuresHTML += `<span class="badge bg-light text-dark feature-badge"><i class="bi bi-wifi"></i> WiFi Hotspot</span>`;
    featuresContainer.innerHTML = featuresHTML;



    document.getElementById('descriptionContainer').innerHTML = vehicle.description || 'No description provided.';

    const specsLeftContainer = document.getElementById('specsLeftContainer');
    specsLeftContainer.innerHTML = `
            <li class="mb-2"><strong>Brand:</strong> ${vehicle.brand}</li>
            <li class="mb-2"><strong>Model:</strong> ${vehicle.model}</li>
            <li class="mb-2"><strong>Year:</strong> ${vehicle.year}</li>
            <li class="mb-2"><strong>Color:</strong> ${vehicle.color}</li>
            <li class="mb-2"><strong>Plate Number:</strong> ${vehicle.plateNumber}</li>
        `;

    const specsRightContainer = document.getElementById('specsRightContainer');
    specsRightContainer.innerHTML = `
            <li class="mb-2"><strong>Fuel Type:</strong> ${vehicle.fuelType}</li>
            <li class="mb-2"><strong>Engine:</strong> ${vehicle.engineCapacity}</li>
            <li class="mb-2"><strong>Transmission:</strong> ${vehicle.transmission}</li>
            <li class="mb-2"><strong>Seats:</strong> ${vehicle.seatingCapacity}</li>
            <li class="mb-2"><strong>Mileage:</strong> ${vehicle.mileage} km</li>
        `;

    const rentalTermsContainer = document.getElementById('rentalTermsContainer');
    rentalTermsContainer.innerHTML = `
            <li>Minimum rental period: ${vehicle.minRentalDays} day(s)</li>
            <li>Security deposit: $${vehicle.depositAmount} (refundable)</li>
            <li>Free cancellation up to 24 hours before pickup</li>
            <li>Mileage limit: 200 km/day (additional $0.30/km after)</li>
            <li>No smoking in the vehicle</li>
            <li>Pets allowed with prior approval</li>
            <li>Must be returned with same fuel level</li>
        `;

    const reviewsContainer = document.getElementById('reviewsContainer');
    reviewsContainer.innerHTML = `
            <div class="mb-4">
                <div class="d-flex mb-2">
                    <img src="https://source.unsplash.com/random/40x40?person" class="rounded-circle me-3" width="40" height="40" alt="Reviewer">
                    <div>
                        <div class="d-flex justify-content-between">
                            <h6 class="mb-0">Sarah M.</h6>
                            <div>
                                <i class="bi bi-star-fill text-warning"></i>
                                <i class="bi bi-star-fill text-warning"></i>
                                <i class="bi bi-star-fill text-warning"></i>
                                <i class="bi bi-star-fill text-warning"></i>
                                <i class="bi bi-star-fill text-warning"></i>
                            </div>
                        </div>
                        <small class="text-muted">March 15, 2023</small>
                        <p class="mt-2 mb-0">Great car! Very clean and comfortable. Owner was easy to communicate with and flexible with pickup/dropoff times.</p>
                    </div>
                </div>
            </div>
        `;

    document.getElementById('priceContainer').innerHTML = `
            <span class="price-highlight">$${vehicle.dailyRate}/day</span>
            <span class="badge bg-success">
                <i class="bi bi-lightning-charge"></i> Instant Book
            </span>
        `;

    document.getElementById('weeklyRateContainer').innerHTML = `
            <span>Weekly rate:</span>
            <span><strong>$${vehicle.weeklyRate}/week</strong> (save $${Math.round(vehicle.dailyRate * 7 - vehicle.weeklyRate)})</span>
        `;

    document.getElementById('monthlyRateContainer').innerHTML = `
            <span>Monthly rate:</span>
            <span><strong>$${vehicle.monthlyRate}/month</strong> (save $${Math.round(vehicle.dailyRate * 30 - vehicle.monthlyRate)})</span>
        `;


}


// Set minimum dates for booking
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

const dd = String(tomorrow.getDate()).padStart(2, '0');
const mm = String(tomorrow.getMonth() + 1).padStart(2, '0');
const yyyy = tomorrow.getFullYear();

document.getElementById('pickupDate').min = `${yyyy}-${mm}-${dd}`;

document.getElementById('pickupDate').addEventListener('change', function() {
    document.getElementById('returnDate').min = this.value;
});

/*  date = return date - pickup date
    date < 7 = day * date
    date > 7 = week / 7  = new value (new value * date)
    date > 30 = month / 30 = new value 2 (new value 2 * date)

    service fee = day rental * 10%

    total = dat rental + service fee

 */
function BookingForm(vehicle){

}
function prepareBooking() {
    const pickupDate = new Date(document.getElementById('pickupDate').value);
    const returnDate = new Date(document.getElementById('returnDate').value);
    const days = Math.ceil((returnDate - pickupDate) / (1000 * 60 * 60 * 24));

    const dayRental = vehicleDTO.dailyRate;
    const weeklyRental = vehicleDTO.weeklyRate;
    const monthlyRental = vehicleDTO.monthlyRate;

    console.log(dayRental, weeklyRental, monthlyRental);
    console.log("Rental days: ", days);

    let daysPerFee = 0;
    let total = 0;
    if (days < 7) {
        daysPerFee = days * dayRental;
        total = daysPerFee;
    }
    else if (days >= 7 && days < 30) {
        const newValue = weeklyRental / 7;
        daysPerFee = days * newValue;
        total = daysPerFee;
    }
    else if (days >= 30) {
        const newValue2 = monthlyRental / 30;
        daysPerFee = days * newValue2;
        total = daysPerFee;
    }
    // service fee 10%
    const serviceFee = daysPerFee * 0.1;
    total += serviceFee;
    total += vehicleDTO.depositAmount



    console.log("Rental Cost:", daysPerFee);
    console.log("Service fee", serviceFee);
    console.log( "Total:", total);

    document.getElementById('rentalDays').value ="$" + daysPerFee.toFixed(1);
    document.getElementById('serviceFee').value = "$" + serviceFee.toFixed(1);
    document.getElementById('total').value = "$" + total.toFixed(1);
}

function bookingBtn(){

    const token = localStorage.getItem('token');
    //decoding token
    const decodedToken = jwt_decode(token);
    //get name use decorded token
    const UserEmail = decodedToken.email;
    const totalAmount =  document.getElementById('total').value;
    const serviceFee =  document.getElementById('serviceFee').value;
    const depositCharge =  document.getElementById('total2').value;
    const startDate = document.getElementById('pickupDate').value;
    const endDate = document.getElementById('returnDate').value;

}