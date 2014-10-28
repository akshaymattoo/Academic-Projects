var map;
var myCenter;
var geocoder;

var latSw;
var longSw;

var latNe;
var longNe;


var query;
var textInput;
var markers = [];

function initialize()
{
  geocoder = new google.maps.Geocoder();
  myCenter=new google.maps.LatLng(32.75,-97.13);
  var mapProp = {
  center:myCenter,
  zoom:16,
  mapTypeId:google.maps.MapTypeId.ROADMAP
  };

 map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

 google.maps.event.addListener(map, 'bounds_changed', function() {
         
         var bounds = map.getBounds();
         //console.log(bounds);
         latNe = bounds.getNorthEast().lat();
         longNe = bounds.getNorthEast().lng();

         latSw = bounds.getSouthWest().lat();
         longSw = bounds.getSouthWest().lng();
      });
}



function sendRequest () {
      
   var parentDiv = document.getElementById("output");
   
   parentDiv.innerHTML="";
   parentDiv.style.border="";

   textInput = document.getElementById("search").value;

   if(!textInput)
   {
    if(markers.length>0){
          for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
          }
        }
    return;
   }
    
   var xhr = new XMLHttpRequest();
   var bound = latSw  + "," + longSw + "|" + latNe  + "," + longNe;
   xhr.open("GET", "proxy.php?term="+ textInput + "&" + "bounds=" + bound + "&limit=10");
  
   xhr.setRequestHeader("Accept","application/json");
   xhr.onreadystatechange = function () {
       if (this.readyState == 4) {
          var json = JSON.parse(this.responseText);


          var orderedList = document.createElement("ol");
          var addressString="";
          var address = new Array();
          
          if(markers.length>0){
            for (var i = 0; i < markers.length; i++) {
              markers[i].setMap(null);
            }
          }
          
          for(var i=0;i<json.businesses.length;i++)
          {
              
              for(var j=0;j<json.businesses[i].location.display_address.length;j++)
              {
                addressString+=json.businesses[i].location.display_address[j]+" ";

              }
            
              populateMarkers(addressString,i);
              addressString="";

              parentDiv.style.border="thin solid black";
              var lis = document.createElement("li");
              var restImg =document.createElement("img");
              restImg.src = json.businesses[i].image_url;

              var restLink = document.createElement("a");
              restLink.href = json.businesses[i].url;
              restLink.innerHTML = json.businesses[i].name;
              restLink.style.font="bold 20px Lucida Sans Unicode, Lucida Grande, sans-serif";

              var ratingImg =document.createElement("img");
              ratingImg.src = json.businesses[i].rating_img_url_large;

              
              var overview =document.createElement("div");
              overview.innerHTML = json.businesses[i].snippet_text;
              restLink.style.font="Lucida Sans Unicode, Lucida Grande, sans-serif";

              lis.appendChild(restLink);
              lis.appendChild(document.createElement("br"));
              lis.appendChild(ratingImg);
              lis.appendChild(document.createElement("br"));
              lis.appendChild(restImg);
              lis.appendChild(overview);
             
              orderedList.appendChild(lis);
              orderedList.appendChild(document.createElement("br"));
              parentDiv.appendChild(orderedList);


          }
          
 
       }
   };
   xhr.send(null);
}

function populateMarkers(addressString,i)
{

    i=i+1;
    var iconImageUrl = "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="+i+"|DC381F|000000";
    geocoder.geocode( { 'address': addressString}, function(results, status) {

    if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);

        var marker = new google.maps.Marker({
        map: map,
        position: results[0].geometry.location,
        icon:iconImageUrl
        });
        markers.push(marker);
    } else {
        alert('Geocode was not successful for the following reason: ' + status);
    }

    });
}