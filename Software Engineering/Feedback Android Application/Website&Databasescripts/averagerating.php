<!DOCTYPE html>  
<html lang="en">  
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="15">
    <title>Feedback Average Rating</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="assets/css/tablecloth.css" rel="stylesheet">
    <link href="assets/css/prettify.css" rel="stylesheet"> 
    <style>
      body {
        background-image:url("assets/img/back.jpg");
        opacity:5.0;
      }
    </style>
  </head>
  <body >
    <div class="container">
      <div class="row">
        <div class="span12" style="padding:20px 0;">
<?php
require ("db_config.php");
$resultArray=array();
$con=mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$query = "SELECT  actual_rating FROM user_rating";
$result = mysqli_query($con,$query);
$type=array();
while ($row=mysqli_fetch_row($result))
{
	$type[] = $row[0];
	
}

// select distinct(comments) from comments c, user_rating urwhere c.user_id = ur.user_id
$queryComments = "select comments from comments c";
$resultComments = mysqli_query($con,$queryComments);
$typeComments=array();
while ($row=mysqli_fetch_row($resultComments))
{
  $typeComments[] = $row[0];
  
}

$len = count($type);
$cnt=0;
$i=0;
echo"<br>";
echo"<br>";
echo "<h1 align=center>FEEDBACK AVERAGE RATING</h1>";
echo "<a href='averagerating.php'><img align=right src='assets/img/refresh.png' height=50 width=50; ></a>";
//echo "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
echo"<br>";
echo"<br>";


echo "<table align=center border='1' class='tablehead' style='background:#CCC;'>";
echo "<tr class='colhead'>";
echo"<td align='center'><b>Music</b></td>";
echo"<td align='center'><b>Food</b></td>";
echo"<td align='center'><b>Service</b></td>";
echo"<td align='center'><b>Ambience</b></td>";
echo"<td align='center'><b>Comments</b></td>";
echo"<td align='center'></td>";
echo "</tr>"; 
while($i<$len) {
	echo "<tr>";
        echo"<td align='center' width=140>".$type[$i]."</td>";
        echo"<td align='center' width=128>".$type[$i+1]."</td>";
        echo"<td align='center' width=185>".$type[$i+2]."</td>";
        echo"<td align='center' width=235>".$type[$i+3]."</td>";
        if(strlen($typeComments[$cnt]) == 0)
          echo"<td align='center' width=300>No comments</td>";
        else  
          echo"<td align='center' width=300>".$typeComments[$cnt]."</td>";
        echo"<td align='center'></td>";
	echo "</tr>"; 
	$i=$i+4;
  $cnt= $cnt+1;
}
echo "</table>";

$groupQuery ="select r.rating_parameter,avg(actual_rating) as summation from rating r,user_rating ur where r.rating_id = ur.rating_id group by rating_parameter";
$resultGroup = mysqli_query($con,$groupQuery);
$tyG=array();
while ($row=mysqli_fetch_row($resultGroup))
{
  $tyG[] = $row[1];
  
}
$j=0;
echo "<table align=center border='1' class='tablehead' style='background:#CCC;'>";


while($j<1){

echo "<tr>";
        echo"<td align='center' width=140><b>".sprintf('%0.2f',$tyG[$j+2])."</b></td>";
        echo"<td align='center'width=128><b>".sprintf('%0.2f',$tyG[$j+1])."</b></td>";
        echo"<td align='center' width=185><b>".sprintf('%0.2f',$tyG[$j+3])."</b></td>";
        echo"<td align='center' width=235><b>".sprintf('%0.2f',$tyG[$j])."</b></td>";
        echo"<td align='center' width=300><b> </b></td>";
        echo"<td align='center'><b>Avg Rating</b></td>";
  echo "</tr>"; 
$j++;
}
echo "</table>";
mysqli_close($con); 
?>
        </div>
      </div>
    </div>
   <script src="assets/js/jquery-1.7.2.min.js"></script>
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/jquery.metadata.js"></script>
    <script src="assets/js/jquery.tablesorter.min.js"></script>
    <script src="assets/js/jquery.tablecloth.js"></script>
    
    <script type="text/javascript" charset="utf-8">
      $(document).ready(function() {
        $("table").tablecloth({
          theme: "stats",
          striped: true,
          sortable: true,
          condensed: true
        });
      });
    </script>
