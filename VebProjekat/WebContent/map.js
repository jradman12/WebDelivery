var map = new Vue({ 
    el: '#googleMap',
    data: {
        map: null,
        zoom : 15,
        mapCenter : { lat : 45.267, lng : 19.84 },
        restaurant : { lat : 45.244, lng : 19.84 }
        
    },
    mounted () {
        this.initMap()
        this.setMarker(this.mapCenter,"A")
        this.setMarker(this.restaurant,"B")
    },

    methods : {
        initMap(){
            this.map = new google.maps.Map(document.getElementById("googleMap"), {
                center : this.mapCenter,
                zoom : true,
                maxZoom: 20, 
                minZoom : 3,
                streetViewControl : true,
                fullscreenControl : true,
                zoomControl : true
            });
        },

        setMarker(points, label){
            const markers = new google.maps.Marker({
                position : points, 
                map : this.map,
                label : {
                    text : label,
                    color : "#FFF"
                }
            })
        }

    }
});