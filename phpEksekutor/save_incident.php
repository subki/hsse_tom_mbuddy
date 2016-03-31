<?php
    include("koneksiDB.php");

   // array for JSON response
$response = array();
 
    $batch= $_GET['batch'];
    $tgl = $_GET['tgl'];
    $problem= $_GET['problem'];
    $uraian= $_GET['uraian'];
    $tindakan= $_GET['tindakan'];
    $nik= $_GET['nik'];
 
// check for required fields
//if (isset($_post['batch']) && ($_post['nik']) ) {
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO incident(batch, tgl, problem, uraian, tindakan, nik, day) VALUES('$batch', '$tgl', '$problem', '$uraian', '$tindakan', '$nik','1')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
//} else {
//    // required field is missing
//    $response["success"] = 0;
//    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
//    echo json_encode($response);
//}
?>