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

$('#myMapModal').on('show.bs.modal', function() {
    alert('TEST');
    loadNearByBrowserChefs();
})
