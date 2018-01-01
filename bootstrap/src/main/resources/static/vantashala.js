function loadNearByBrowserChefs() {

    var countryCode = "US";
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude;
            var long = position.coords.longitude;
            point = new google.maps.LatLng(lat, long);

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
                    var url = "rest/search/cooks/" + countryCode + "/nearBy/zip?zip=" + zip[1] + "&miles=10";

                    $.get(url, function (data, status) {
                        loadMap(point, data);
                        console.log("Data: " + data + "\nStatus: " + status);
                    });

                    //$("#loc").val(zip);
                }
            );
        });
    }
    else {
        alert("Sorry, Unable to find your location.");
    }
}

function addMarkers(chefs) {

    for (var i = 0; i < 2; i++) {
        console.log(i+"->"+chefs[i].loc[1], chefs[i].loc[0]);
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(chefs[i].loc[1], chefs[i].loc[0]),
            map: map
        });
        var infowindow = new google.maps.InfoWindow({
            content: chefs[i].kitchenName
        });
        infowindow.open(map, marker);
    }

}

function loadMap(center, chefs) {
    var mapOptions = {
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: center
    };

    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
    addMarkers(chefs);

    $('#chefsCount').text("Found " + chefs.length + " Chefs Nearby")
    $('#myModal').modal({
        backdrop: 'static',
        keyboard: false
    }).on('shown.bs.modal', function () {
        google.maps.event.trigger(map, 'resize');
        map.setCenter(center);

    });
}

$('.launch-map').on('click', function () {
    loadNearByBrowserChefs();
});