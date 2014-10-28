<?php session_start(); 
if (!isset($_SESSION['items'])) {
  		$_SESSION['items'] = array();
	}
?>
<html>
<head><title>Buy Products</title></head>
<body>
<p>Shopping Basket:</p>
<?php
	$buy=isset($_GET['buy']);
	echo $_SESSION['items'][$buy]['name'];
?>
<table border=1>

</table>
<p/>
Total: 0$<p/>
<form action="buy.php" method="GET">
<input type="hidden" name="clear" value="1"/>
<input type="submit" value="Empty Basket"/>
</form>
<p/>
<form action="buy.php" method="GET">
<fieldset><legend>Find products:</legend>
<label>Search for items: <input type="text" name="search"/><label>
<input type="submit" name="Submit" value="Search"/>
</fieldset>
</form>
<p/>

<?php
error_reporting(E_ALL);
ini_set('display_errors','On');

if (isset($_GET['Submit'])) {
	$url = "http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/GeneralSearch?apiKey=78b0db8a-0ee1-4939-a2f9-d3cd95ec0fcc&trackingId=7000610&keyword=".$_GET['search'];
	$xmlstr = file_get_contents($url);
	//$xmlstr =stripslashes($xmlstr);
	$xml = new SimpleXMLElement($xmlstr);
	header('Content-Type: text/html');
	$itemID =$xml->categories[0]->category[0]->items[0]->product[0]->attributes();
	$name = $xml->categories[0]->category[0]->items[0]->product[0]->name;
	//echo $itemID;
	
	echo "<table border='1'>";
	for($i = 0; $i < 1; $i++) {
		echo "<tr>";
		echo "<td><a href=buy.php?buy=".$xml->categories[0]->category[0]->items[0]->product[0]->attributes()."><img src=\"" .$xml->categories[0]->category[0]->items[0]->product[0]->images[0]->image[0]->sourceURL. "\" /></a></td>";
		echo "<td>". $xml->categories[0]->category[0]->items[0]->product[0]->name."</td>";
		echo "<td>". $xml->categories[0]->category[0]->items[0]->product[0]->minPrice."</td>";
		echo "</tr>";
	}
	echo "</table>";
	//$_SESSION['items'][$itemID] = array('image' => $xml->categories[0]->category[0]->items[0]->product[0]->images[0]->image[0]->sourceURL, 'Url' => $xml->categories[0]->category[0]->items[0]->product[0]->productOffersURL,'name'=>xml->categories[0]->category[0]->items[0]->product[0]->name,'price'=>$xml->categories[0]->category[0]->items[0]->product[0]->minPrice;
	
	$_SESSION['items'][$itemID] = array("name"=>$name);
	
	}
?>
</body>
</html>
