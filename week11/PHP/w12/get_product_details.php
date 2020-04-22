<?php

// array for JSON response
$response = array();

$koneksi = mysqli_connect("localhost", "root", "", "android_w12");

// check for POST data
if(isset($_POST['pid'])){
	$pid = $_POST['pid'];
	$sql = "SELECT * FROM products WHERE id = '$pid'";
	$result = mysqli_query($koneksi, $sql);

	if(mysqli_num_rows($result) > 0){
		$row = mysqli_fetch_array($result);
		$product = array();
		$product['pid'] = $row['id'];
		$product['name'] = $row['name'];
		$product['price'] = $row['price'];
		$product['description'] = $row['description'];

		// success
		$response['success'] = 1;

		// user node
		$response['product'] = array();
		array_push($response['product'], $product);

		// echoing JSON response
		echo json_encode($response);
	}else{
		// no product found
		$response['success'] = 0;
		$response['message'] = "No product found";
		// echoing JSON response
		echo json_encode($response);
	}
}else{
	// required field is missing
	$response['success'] = 0;
	$response['message'] = "Required field(s) is missing!";
	// echoing JSON response
	echo json_encode($response);
}