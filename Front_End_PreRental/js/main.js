 AOS.init({
 	duration: 800,
 	easing: 'slide'
 });

(function($) {

	"use strict";

	var isMobile = {
		Android: function() {
			return navigator.userAgent.match(/Android/i);
		},
			BlackBerry: function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
			iOS: function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
			Opera: function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
			Windows: function() {
			return navigator.userAgent.match(/IEMobile/i);
		},
			any: function() {
			return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};


	$(window).stellar({
    responsive: true,
    parallaxBackgrounds: true,
    parallaxElements: true,
    horizontalScrolling: false,
    hideDistantElements: false,
    scrollProperty: 'scroll'
  });


	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	// loader
	var loader = function() {
		setTimeout(function() { 
			if($('#ftco-loader').length > 0) {
				$('#ftco-loader').removeClass('show');
			}
		}, 1);
	};
	loader();

	// Scrollax
   $.Scrollax();

	var carousel = function() {
		$('.carousel-car').owlCarousel({
			center: true,
			loop: true,
			autoplay: true,
			items:1,
			margin: 30,
			stagePadding: 0,
			nav: false,
			navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
			responsive:{
				0:{
					items: 1
				},
				600:{
					items: 2
				},
				1000:{
					items: 3
				}
			}
		});
		$('.carousel-testimony').owlCarousel({
			center: true,
			loop: true,
			items:1,
			margin: 30,
			stagePadding: 0,
			nav: false,
			navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
			responsive:{
				0:{
					items: 1
				},
				600:{
					items: 2
				},
				1000:{
					items: 3
				}
			}
		});

	};
	carousel();

	$('nav .dropdown').hover(function(){
		var $this = $(this);
		// 	 timer;
		// clearTimeout(timer);
		$this.addClass('show');
		$this.find('> a').attr('aria-expanded', true);
		// $this.find('.dropdown-menu').addClass('animated-fast fadeInUp show');
		$this.find('.dropdown-menu').addClass('show');
	}, function(){
		var $this = $(this);
			// timer;
		// timer = setTimeout(function(){
			$this.removeClass('show');
			$this.find('> a').attr('aria-expanded', false);
			// $this.find('.dropdown-menu').removeClass('animated-fast fadeInUp show');
			$this.find('.dropdown-menu').removeClass('show');
		// }, 100);
	});


	$('#dropdown04').on('show.bs.dropdown', function () {
	  console.log('show');
	});

	// scroll
	var scrollWindow = function() {
		$(window).scroll(function(){
			var $w = $(this),
					st = $w.scrollTop(),
					navbar = $('.ftco_navbar'),
					sd = $('.js-scroll-wrap');

			if (st > 150) {
				if ( !navbar.hasClass('scrolled') ) {
					navbar.addClass('scrolled');	
				}
			} 
			if (st < 150) {
				if ( navbar.hasClass('scrolled') ) {
					navbar.removeClass('scrolled sleep');
				}
			} 
			if ( st > 350 ) {
				if ( !navbar.hasClass('awake') ) {
					navbar.addClass('awake');	
				}
				
				if(sd.length > 0) {
					sd.addClass('sleep');
				}
			}
			if ( st < 350 ) {
				if ( navbar.hasClass('awake') ) {
					navbar.removeClass('awake');
					navbar.addClass('sleep');
				}
				if(sd.length > 0) {
					sd.removeClass('sleep');
				}
			}
		});
	};
	scrollWindow();

	var isMobile = {
		Android: function() {
			return navigator.userAgent.match(/Android/i);
		},
			BlackBerry: function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
			iOS: function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
			Opera: function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
			Windows: function() {
			return navigator.userAgent.match(/IEMobile/i);
		},
			any: function() {
			return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};

	var counter = function() {
		
		$('#section-counter, .hero-wrap, .ftco-counter').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('ftco-animated') ) {

				var comma_separator_number_step = $.animateNumber.numberStepFactories.separator(',')
				$('.number').each(function(){
					var $this = $(this),
						num = $this.data('number');
						console.log(num);
					$this.animateNumber(
					  {
					    number: num,
					    numberStep: comma_separator_number_step
					  }, 7000
					);
				});
				
			}

		} , { offset: '95%' } );

	}
	counter();


	var contentWayPoint = function() {
		var i = 0;
		$('.ftco-animate').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('ftco-animated') ) {
				
				i++;

				$(this.element).addClass('item-animate');
				setTimeout(function(){

					$('body .ftco-animate.item-animate').each(function(k){
						var el = $(this);
						setTimeout( function () {
							var effect = el.data('animate-effect');
							if ( effect === 'fadeIn') {
								el.addClass('fadeIn ftco-animated');
							} else if ( effect === 'fadeInLeft') {
								el.addClass('fadeInLeft ftco-animated');
							} else if ( effect === 'fadeInRight') {
								el.addClass('fadeInRight ftco-animated');
							} else {
								el.addClass('fadeInUp ftco-animated');
							}
							el.removeClass('item-animate');
						},  k * 50, 'easeInOutExpo' );
					});
					
				}, 100);
				
			}

		} , { offset: '95%' } );
	};
	contentWayPoint();


	// navigation
	var OnePageNav = function() {
		$(".smoothscroll[href^='#'], #ftco-nav ul li a[href^='#']").on('click', function(e) {
		 	e.preventDefault();

		 	var hash = this.hash,
		 			navToggler = $('.navbar-toggler');
		 	$('html, body').animate({
		    scrollTop: $(hash).offset().top
		  }, 700, 'easeInOutExpo', function(){
		    window.location.hash = hash;
		  });


		  if ( navToggler.is(':visible') ) {
		  	navToggler.click();
		  }
		});
		$('body').on('activate.bs.scrollspy', function () {
		  console.log('nice');
		})
	};
	OnePageNav();


	// magnific popup
	$('.image-popup').magnificPopup({
    type: 'image',
    closeOnContentClick: true,
    closeBtnInside: false,
    fixedContentPos: true,
    mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
     gallery: {
      enabled: true,
      navigateByImgClick: true,
      preload: [0,1] // Will preload 0 - before current, and 1 after the current image
    },
    image: {
      verticalFit: true
    },
    zoom: {
      enabled: true,
      duration: 300 // don't foget to change the duration also in CSS
    }
  });

  $('.popup-youtube, .popup-vimeo, .popup-gmaps').magnificPopup({
    disableOn: 700,
    type: 'iframe',
    mainClass: 'mfp-fade',
    removalDelay: 160,
    preloader: false,

    fixedContentPos: false
  });


	$('#book_pick_date,#book_off_date').datepicker({
	  'format': 'm/d/yyyy',
	  'autoclose': true
	});
	$('#time_pick').timepicker();



})(jQuery);











/*=================================login And Register================================================================================================================*/
 function validateEmail(email) {
	 const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	 return emailRegex.test(email);
 }
 function validatePassword(password) {
	 return password.length >= 6;
 }
 function validateEmail(email) {
	 const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	 return emailRegex.test(email);
 }
 function validatePassword(password) {
	 return password.length >= 6;
 }

 function login() {
	 let emailUser = document.getElementById("loginEmail").value.toLowerCase();
	 let password = document.getElementById("loginPassword").value;
	 if (!validateEmail(emailUser)) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Invalid Email',
			 text: 'Please enter a valid email address.',
			 showConfirmButton: true
		 });
		 return;
	 }
	 if (!validatePassword(password)) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Invalid Password',
			 text: 'Password must be at least 6 characters long.',
			 showConfirmButton: true
		 });
		 return;
	 }

	 $.ajax({
		 url: 'http://localhost:8080/api/v1/auth/authenticate',
		 method: 'POST',
		 contentType: 'application/json',
		 data: JSON.stringify({
			 email: emailUser,
			 password: password
		 }),
		 success: function(response) {
			 console.log("Login successful");
			 localStorage.setItem("token", response.data.token);
				 getUserDetails(emailUser);
		 },

		 error: function(xhr, status, error) {
			 console.error("Login failed:", error);
			 Swal.fire({
				 icon: 'error',
				 title: 'Login Failed',
				 text: 'Invalid email or password. Please try again.',
				 showConfirmButton: true
			 });
		 }
	 });
 }

 function getUserDetails(email) {
	 $.ajax({
		 url: 'http://localhost:8080/api/v1/user/getUser',
		 method: 'GET',
		 contentType: 'application/json',
		 async: true,
		 headers: {
			 Authorization: 'Bearer '+ localStorage.getItem("token")
		 },
		 data: {
			 email: email
		 },
		 success: function(response) {
			 console.log("User details fetched successfully");
			 const userRole = response.data.role;
			 if(response.data.verified === true) {
				 Swal.fire({
				 icon: 'success',
				 title: 'Login Successful!',
				 text: 'You have successfully logged in.',
				 showConfirmButton: false,
				 timer: 2000
			 }).then(() => {
					 if (userRole === "admin") {
						 window.location.href = "../../../Second_Sem_Final_Project/Front_End_PreRental/car1.html";
					 } else if(userRole === "user"){
						 window.location.href = "../../../Second_Sem_Final_Project/Front_End_PreRental/car.html";
						 getUserData(email);
					 }
				 });
			 }else{
				 Swal.fire({
                     icon: 'error',
                     title: 'Email not verified',
                     text: 'Please verify your email address to proceed.',
                     showConfirmButton: false,
					 timer: 2000
                 }).then(() => {
					 $('#loginModal').modal('hide');

					 $('#verificationModal').modal('show');
				 });
			 }

		 },
		 error: function(xhr, status, error) {
			 console.error("Failed to fetch user details:", error);
			 Swal.fire({
				 icon: 'error',
				 title: 'Error',
				 text: 'Failed to fetch user details. Please try again.',
				 showConfirmButton: true
			 });
		 }
	 });
 }


 function register() {
	 let Firstname = document.getElementById("registerFirstName").value;
	 let Secondname = document.getElementById("registerLastName").value;
	 let name = Firstname + " " + Secondname;
	 let email = document.getElementById("registerEmail").value.toLowerCase();
	 let password = document.getElementById("registerPassword").value;
	 let confirmPassword = document.getElementById("confirmPassword").value;
	 if (!validateEmail(email)) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Invalid Email',
			 text: 'Please enter a valid email address.',
			 showConfirmButton: true
		 });
		 return false;
	 }
	 if (!validatePassword(password)) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Invalid Password',
			 text: 'Password must be at least 6 characters long.',
			 showConfirmButton: true
		 });
		 return false;
	 }

	 if (password !== confirmPassword) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Password Mismatch',
			 text: 'Passwords do not match. Please try again.',
			 showConfirmButton: true
		 });
		 return false;
	 } else {
		 $.ajax({
			 method: 'POST',
			 contentType: 'application/json',
			 url: 'http://localhost:8080/api/v1/user/register',
			 async: true,
			 data: JSON.stringify({
				 "email": email,
				 "name": name,
				 "password": password,
				 "role": "user"
			 }),
			 success: function(response) {
				 console.log("Registration successful");
				 localStorage.setItem("token", response.data.token);

				 Swal.fire({
					 icon: 'success',
					 title: 'Registration Successful!',
					 text: 'Your account has been created successfully.A verification email has been sent to your email. Check your mail box ',
					 showConfirmButton: true,
					 confirmButtonText: 'Verify Email',
				 }).then((result) => {
					 if (result.isConfirmed) {
						 // Hide the register modal
						 $('#registerModal').modal('hide');

						 // Show the verification modal
						 $('#verificationModal').modal('show');
					 }
				 });
			 },
			 error: function(error) {
				 console.error("Registration failed:", error);
				 let errorMessage = "Registration failed. Please try again.";
				 if (error.responseJSON && error.responseJSON.message) {
					 errorMessage = error.responseJSON.message.join("<br>");
				 }
				 Swal.fire({
					 icon: 'error',
					 title: 'Registration Failed',
					 html: errorMessage,
					 showConfirmButton: true
				 });
			 }
		 });
	 }
 }

/*============================================Verify the code===========================================================================================*/
 function verifyTheAccount() {
	 // Get token from local storage
	 const token = localStorage.getItem('token');
	 if (!token) {
		 Swal.fire({
			 icon: 'error',
			 title: 'Error',
			 text: 'No token found. Please log in again.',
			 showConfirmButton: true
		 });
		 return;
	 }
	 const decodedToken = jwt_decode(token);
	 const email = decodedToken.email;

	 console.log("email ",email);

	 // Get the OTP code from the input field
	 const code = document.getElementById("otp").value;
	 // Send verification request to the server
	 $.ajax({
		 url: 'http://localhost:8080/api/v1/verifyUser/verify',
		 method: 'POST',
		 contentType: 'application/json',
		 headers: {
			 Authorization: 'Bearer ' + token
		 },
		 data: JSON.stringify({
			 email: email,
			 code: code
		 }),
		 success: function(response) {
			 if (response.code === 200) {
				 Swal.fire({
					 icon: 'success',
					 title: 'Email Verification Successful!',
					 text: 'Your email has been verified successfully. You can now log in.',
					 showConfirmButton: true
				 }).then(() => {
					 $('#verificationModal').modal('hide');
					 $('#loginModal').modal('show');
				 });
			 } else if (response.code === 404) {
				 Swal.fire({
					 icon: 'error',
					 title: 'User Not Found',
					 text: 'The user associated with this email does not exist.',
					 showConfirmButton: true
				 });
			 } else if (response.code === 406) {
				 Swal.fire({
					 icon: 'error',
					 title: 'Invalid Verification Code',
					 text: 'The verification code you entered is invalid. Please try again.',
					 showConfirmButton: true
				 });
			 }
		 },
		 error: function(xhr, status, error) {
			 console.error("Verification failed:", error);
			 Swal.fire({
				 icon: 'error',
				 title: 'Verification Failed',
				 text: 'An error occurred while verifying your email. Please try again.',
				 showConfirmButton: true
			 });
		 }
	 });
 }

 /*===================================Car Rental Page==================================================================================================*/
  /*Fetch data use by User Email*/
/*
 function getUserDetailsInCarPage(email) {
	 $.ajax({
		 url: 'http://localhost:8080/api/v1/user/getUser',
		 method: 'GET',
		 contentType: 'application/json',
		 async: true,
		 data: {
			 email: email
		 },
		 success: function(response) {
			 console.log("User details fetched successfully");
			 const userRole = response.data.role;
			 if (userRole === "admin") {
				 window.location.href = "html/AdminDashBoard/AdminDashBoard.html";
			 } else {
				 window.location.href = "../html/car.html";
			 }
		 },
		 error: function(xhr, status, error) {
			 console.error("Failed to fetch user details:", error);
			 Swal.fire({
				 icon: 'error',
				 title: 'Error',
				 text: 'Failed to fetch user details. Please try again.',
				 showConfirmButton: true
			 });
		 }
	 });
 }*/
