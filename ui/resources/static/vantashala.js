function loadNearByBrowserChefs() {

    var countryCode = "US";
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude;
            var long = position.coords.longitude;
            var point = new google.maps.LatLng(lat, long);
            new google.maps.Geocoder().geocode(
                {'latLng': point},
                function (results, status) {
                    for (var i = 0; i < results[0].address_components.length; i++) {
                        for (var b = 0; b < results[0].address_components[i].types.length; b++) {
                            if (results[0].address_components[i].types[0] == "country") {
                                countryCode = results[0].address_components[i].short_name;
                            }
                        }
                    }

                    var zip = results[0].formatted_address.match(/,\s\w{2}\s(\d{5})/);

                    var url = "rest/search/cooks/"+countryCode+"/nearBy/zip?zip="+zip[1]+"&miles=10";

                    $.get(url, function (data, status) {
                        alert("Data: " + data + "\nStatus: " + status);
                    });


                    //$("#loc").val(zip);
                }
            );
        });
    }

}
var map;
var myCenter=new google.maps.LatLng(53, -1.33);
var marker=new google.maps.Marker({
    position:myCenter
});

function initialize() {
    var mapProp = {
        center:myCenter,
        zoom: 14,
        draggable: false,
        scrollwheel: false,
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };

    map=new google.maps.Map(document.getElementById("map-canvas"),mapProp);
    marker.setMap(map);

    google.maps.event.addListener(marker, 'click', function() {

        infowindow.setContent(contentString);
        infowindow.open(map, marker);

    });
};
google.maps.event.addDomListener(window, 'load', initialize);

google.maps.event.addDomListener(window, "resize", resizingMap());

$('#myMapModal').on('show.bs.modal', function() {
    loadNearByBrowserChefs();
    resizeMap();
})

function resizeMap() {
    if(typeof map =="undefined") return;
    setTimeout( function(){resizingMap();} , 400);
}

function resizingMap() {
    if(typeof map =="undefined") return;
    var center = map.getCenter();
    google.maps.event.trigger(map, "resize");
    map.setCenter(center);
}