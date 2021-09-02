function myMap() {

	//var location =  rest.address.addressName + rest.address.city + rest.address.postalCode;
	var location = "Jevrejska 8 Novi Sad 21000";
			geocode(location);
		
}

function geocode(location){
	axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
		params:{
			address:location,
			key: 'AIzaSyAN9OBd2ZhIWfh489y3NRD7NMt33oXoxY0'
		}
	})
	.then(function(response){
		console.log(response);
		var lat = response.data.results[0].geometry.location.lat;
		var lng = response.data.results[0].geometry.location.lng;
		marker(lat, lng);
		})
	
	.catch(function(error){
        console.log(error);
	})
}

function marker(lat, lng){

	var mapProps = {
		center: new google.maps.LatLng(lat, lng),
		zoom: 14,
	};
	var map = new google.maps.Map(document.getElementById("googleMap"), mapProps);
	
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat, lng)
	});

	marker.setMap(map);

}