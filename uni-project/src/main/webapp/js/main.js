function bindEvents(){
    $('#register').on('click', registerClick);
}

function registerClick(){
	$('#register-modal').modal('show');
}

function updateClick(categoryName){
	$('#update-modal').modal('show');
	$('#update-name').val(categoryName);
}

function updateClickProduct(productName,productPrice,productQuantity){
	$('#update-modal-product').modal('show');
	$('#update-product-name').val(productName);
	$('#update-product-price').val(productPrice);
	$('#update-product-quantity').val(productQuantity);
}
