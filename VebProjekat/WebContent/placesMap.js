window.parts = 'x';

function initAutocomplete() {
  const map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 45.25, lng: 19.84 },
    zoom: 14,
    mapTypeId: "roadmap",
  });
  // Create the search box and link it to the UI element.
  const input = document.getElementById("pac-input");
  const searchBox = new google.maps.places.SearchBox(input);
  // map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener("bounds_changed", () => {
    searchBox.setBounds(map.getBounds());
  });
  let markers = [];
  // Listen for the event fired when the user selects a prediction and retrieve
  // more details for that place.
  searchBox.addListener("places_changed", () => {
    const places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }
    // Clear out the old markers.
    markers.forEach((marker) => {
      marker.setMap(null);
    });
    markers = [];
    // For each place, get the icon, name and location.
    const bounds = new google.maps.LatLngBounds();
    places.forEach((place) => {
      if (!place.geometry || !place.geometry.location) {
        console.log("Returned place contains no geometry");
        return;
      }
      const icon = {
        url: place.icon,
        size: new google.maps.Size(71, 71),
        origin: new google.maps.Point(0, 0),
        anchor: new google.maps.Point(17, 34),
        scaledSize: new google.maps.Size(25, 25),
      };
      // Create a marker for each place.
      markers.push(
        new google.maps.Marker({
          map,
          // draggable : true,
          icon,
          title: place.name,
          position: place.geometry.location,
        })
      );

      console.log('place.geometry' + place.geometry)

      if (place.geometry.viewport) {
        // Only geocodes have viewport.
        bounds.union(place.geometry.viewport);
      } else {
        bounds.extend(place.geometry.location);
      }

      const lat = place.geometry.location.lat()
      const lng = place.geometry.location.lng()
      let url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyAPh6HCDbNWHnQaz0ah1KYa0P123mkCAEU`
      fetch(url)
        .then(response => response.json())
        .then(data => {
          console.log(data);
          parts = data.results[0].address_components;

          var streetName = "";
          var streetNum = "";
          var city = "";
          var postal = "";

          parts.forEach(part => {
            
            if (part.types.includes("route")) {
              streetName = part.long_name;
            }
            if (part.types.includes("street_number")) {
              streetNum = part.long_name;
            }
            if (part.types.includes("locality")) {
             
              city = part.long_name;
            }
            if (part.types.includes("postal_code")) {
              postal = part.long_name;
            }
            
          }) 

          parts = streetName + " " + streetNum + ", " + city + ", " + postal;

        })

    });
    google.maps.event.trigger(map, 'resize')
    map.fitBounds(bounds);




  });
}
