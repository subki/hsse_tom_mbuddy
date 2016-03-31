<?php
include("koneksiDB.php");

$batch = $_GET['batch'];

// array for JSON response
$response = array();
 
 
$sql = "SELECT id, nama, phone, hp, keterangan from telp_team where batch='$batch'";
$result = mysql_query ($sql) or die(mysql_error()); //run the query
 
 // check for empty result
 if (mysql_num_rows($result) > 0) {

  $response["data"] = array();
 
  while ($row = mysql_fetch_array($result)) {
   // temp user array
   $phone= array();
   $phone["id"] = $row["id"];
   $phone["ket"] = $row["keterangan"];
   $phone["nama"] = $row["nama"];
   $phone["nohp"] = $row["hp"];
   $phone["telepon"] = $row["phone"];
 
   // push single puasa into final response array
   array_push($response["data"], $phone);
  }
  // success
  $response["success"] = 1;
 
  // echoing JSON response
  echo json_encode($response);
 } else {
  $response["success"] = 0;
  $response["message"] = "Tidak ada data phone";
 
  // echo no users JSON
  echo json_encode($response);
 }
?>

