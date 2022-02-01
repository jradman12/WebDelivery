function initMap() {

	$.get({
		url: 'rest/restaurants/getCurrentRestaurant',
		contentType: 'application/json',
		success: function (rest) {
			//var loco = rest.location.address.addressName + rest.location.address.city + rest.location.address.postalCode;
			var loco = "Radnicka 44 , Novi Sad , 21000";
			geocode(loco);
		}
	});	
}

function geocode(location) {
	axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
		params: {
			address: location,
			key: 'AIzaSyAdZcoh61MaikwAmRmhKEo44OaMmDtVKK8'
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
	var map = new google.maps.Map(document.getElementById("map"), {
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