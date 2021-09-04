function initMap() {

	// $.get({
	// 	url: 'rest/restaurants/getCurrentRestaurant',
	// 	contentType: 'application/json',
	// 	success: function (rest) {
	// 		var location = rest.location.address.addressName + rest.location.address.city + rest.location.address.postalCode;
	// 		geocode(location);
	// 	}
	// });

	geocode("Jevrejska 2, Novi Sad, 21000");

}

function geocode(location) {
	axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
		params: {
			address: location,
			key: 'AIzaSyAPh6HCDbNWHnQaz0ah1KYa0P123mkCAEU'
		}
	})
		.then(function (response) {
			console.log(response);
			var lat = response.data.results[0].geometry.location.lat;
			var lng = response.data.results[0].geometry.location.lng;
			marker(lat, lng);
		})

		.catch(function (error) {
			console.log(error);
		})
}

function marker(lat, lng) {

	//initialize map
	var map = new google.maps.Map(document.getElementById("googleMap"), {
		center: new google.maps.LatLng(lat, lng),
		zoom: 15,
		maxZoom: 20,
		minZoom: 3,
		streetViewControl: true,
		fullscreenControl: true,
		zoomControl: true
	});

	//set marker
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat, lng),
		map: map
	});

	console.log(marker)

}