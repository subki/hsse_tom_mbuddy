<?php
include("koneksiDB.php");

$batch = $_GET['batch'];

// array for JSON response
$response = array();
 
 
$sql = "SELECT nik, nama, nohp, phone, profile_images from karyawan where batch='$batch' order by nik ASC";
$result = mysql_query ($sql) or die(mysql_error()); //run the query
 
 // check for empty result
 if (mysql_num_rows($result) > 0) {

  $response["data"] = array();
 
  while ($row = mysql_fetch_array($result)) {
   // temp user array
   $phone= array();
   $phone["nik"] = $row["nik"];
   $phone["nama"] = $row["nama"];
   $phone["nohp"] = $row["nohp"];
   $phone["telepon"] = $row["phone"];
   $phone["foto"]="http://employees.esy.es/profile_images/".$row["profile_images"];
 
   // push single puasa into final response array
   array_push($response["data"], $phone);
  }
  // success
  $response["success"] = 1;
 
  // echoing JSON response
  echo json_encode($response);
 } else {
  $response["success"] = 0;
  $response["message"] = "Tidak ada data mahasiswa";
 
  // echo no users JSON
  echo json_encode($response);
 }
?>

