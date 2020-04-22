<?php
// array for JSON response
$response = array();

$koneksi = mysqli_connect("localhost", "root", "", "android_w12");
$sql = "SELECT * FROM products";
$result = mysqli_query($koneksi, $sql);

if(mysqli_num_rows($result) > 0){
	// looping through all results
	// products node
	$response['products'] = array();
	while($row = mysqli_fetch_array($result)){
		// temp user array
		$product = array();
		$product['pid'] = $row['id'];
		$product['name'] = $row['name'];
		$product['price'] = $row['price'];
		$product['description'] = $row['description'];
		// push single product into final response array
		array_push($response['products'], $product);
	}
	// success
	$response['success'] = 1;
	// echoing JSON response
	echo json_encode($response);
}else{
	// no product found
	$response['success'] = 0;
	$response['message'] = "No products found.";
	// echoing JSON response
	echo json_encode($response);
}