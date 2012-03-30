<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Map<String, String> result = (Map<String, String>)request.getAttribute("result");
	String address = result.get("address");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Map</title>
<script type="text/javascript" src="http://www.google.com/jsapi?key=AIzaSyARWMljS1UBvccj6E3siKU41_sktBm9VHc">
</script>
<script type="text/javascript">
    google.load("maps", "2.x", { "language": "en" });
</script>


<script type="text/javascript">

    var map = null;
    var geocoder = null;



    function initialize() {
      if (GBrowserIsCompatible()) {
    	
        map = new GMap2(document.getElementById("map_canvas"));
      
 //       map.setCenter(new GLatLng(37.4419, -122.1419), 13);
        geocoder = new GClientGeocoder();
      }
    }

    function showAddress(address) {
      if (geocoder) {
        geocoder.getLatLng(
          address,
          function(point) {
            if (!point) {
              alert(address + " not found");
            } else {
              map.setCenter(point, 13);
              var marker = new GMarker(point);
              map.addOverlay(marker);

              // As this is user-generated content, we display it as
              // text rather than HTML to reduce XSS vulnerabilities.
              marker.openInfoWindow(document.createTextNode(address));
            }
          }
        );
      }
    }
    </script>
</head>
<body onload="initialize();showAddress('<%=address %>');" onunload="GUnload()">
<div id="map_canvas" style="height:700px"></div>
</body>
</body>
</html>