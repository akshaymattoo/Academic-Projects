<?php
 $phone_number="";
$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$query = "SELECT phone_number FROM admin";


$result = mysqli_query($con,$query);
while($row = mysqli_fetch_array($result))
  {
  
    $phone_number.= $row['phone_number'].";";
  
  }
echo $phone_number;
mysqli_close($con); 
?>